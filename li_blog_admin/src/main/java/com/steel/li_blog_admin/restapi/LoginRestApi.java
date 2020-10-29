package com.steel.li_blog_admin.restapi;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.steel.li_blog_common.config.jwt.Audience;
import com.steel.li_blog_common.config.jwt.JwtHelper;
import com.steel.li_blog_common.entity.Admin;
import com.steel.li_blog_common.entity.Role;
import com.steel.li_blog_common.feign.PictureFeignClient;
import com.steel.li_blog_utils.*;
import com.steel.li_blog_xo.global.MessageConf;
import com.steel.li_blog_xo.global.RedisConf;
import com.steel.li_blog_xo.global.SQLConf;
import com.steel.li_blog_xo.global.SysConf;
import com.steel.li_blog_xo.service.AdminService;
import com.steel.li_blog_xo.service.CategoryMenuService;
import com.steel.li_blog_xo.service.RoleService;
import com.steel.li_blog_xo.utils.WebUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @Auther: steel
 * @Date: 2020/10/29 11:39
 * @Description: 登录管理RestApi(为了更好地使用security放行把登录管理放在AuthRestApi中)
 */
@RestController
@RequestMapping("/auth")
@Api(value = "登录管理相关接口", tags = {"登录管理相关接口"})
@Slf4j
public class LoginRestApi {

    @Autowired
    WebUtil webUtil;

    @Autowired
    private AdminService adminService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private JwtHelper jwtHelper;

    @Autowired
    private CategoryMenuService categoryMenuService;

    @Autowired
    private Audience audience;

    @Value(value = "${tokenHead}")
    private String tokenHead;

    @Value(value = "${isRememberMeExpiresSecond}")
    private int longExpiresSecond;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private PictureFeignClient pictureFeignClient;

    public String login(HttpServletRequest request,
                        @ApiParam(name = "username", value = "用户名或邮箱或手机号", required = false)@RequestParam(name = "username", required = false) String username,
                        @ApiParam(name = "password", value = "密码", required = false) @RequestParam(name = "password", required = false) String password,
                        @ApiParam(name = "isRememberMe", value = "是否记住账号密码", required = false) @RequestParam(name = "isRememberMe", required = false, defaultValue = "0") int isRememberMe) {
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            return ResultUtil.result(SysConf.ERROR, "账号或密码不能为空");
        }

        String ip = IpUtils.getIpAddr(request);
        String limitCount = redisUtil.get(RedisConf.LOGIN_LIMIT + RedisConf.SEGMENTATION + ip);
        if (StringUtils.isNotEmpty(limitCount)) {
            Integer tempLimitCount = Integer.valueOf(limitCount);
            if (tempLimitCount >= 5) {
                return ResultUtil.result(SysConf.ERROR, "密码输错次数过多,已被锁定30分钟");
            }
        }
        Boolean isEmail = CheckUtils.checkEmail(username);
        Boolean isMobile = CheckUtils.checkMobileNumber(username);
        QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
        if (isEmail) {
            queryWrapper.eq(SQLConf.EMAIL, username);
        } else if (isMobile) {
            queryWrapper.eq(SQLConf.MOBILE, username);
        } else {
            queryWrapper.eq(SQLConf.USER_NAME, username);
        }
        Admin admin = adminService.getOne(queryWrapper);
        if (admin == null) {
            // 设置错误登录次数
            return ResultUtil.result(SysConf.ERROR, String.format(MessageConf.LOGIN_ERROR, setLoginCommit(request)));
        }
        // 验证密码
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        boolean isPassword =  encoder.matches(password, admin.getPassWord());
        if (!isPassword) {
            //密码错误，返回提示
            return ResultUtil.result(SysConf.ERROR, String.format(MessageConf.LOGIN_ERROR, setLoginCommit(request)));
        }

        List<String> roleUids = new ArrayList<>();
        roleUids.add(admin.getRoleUid());
        List<Role> roles = (List<Role>) roleService.listByIds(roleUids);

        if (roles.size() <= 0) {
            return ResultUtil.result(SysConf.ERROR, MessageConf.NO_ROLE);
        }
        String roleNames = null;
        for (Role role : roles) {
            roleNames += (role.getRoleName() + ",");
        }
        String roleName = roleNames.substring(0, roleNames.length() - 2);
        long expiration = isRememberMe == 1 ? longExpiresSecond : audience.getExpiresSecond();
        String jwtToken = jwtHelper.createJWT(admin.getUserName(),
                admin.getUid(),
                roleName.toString(),
                audience.getClientId(),
                audience.getName(),
                expiration * 1000,
                audience.getBase64Secret());
        String token = tokenHead + jwtToken;
        Map<String, Object> result = new HashMap<>();
        result.put(SysConf.TOKEN, token);

        //进行登录相关操作
        Integer count = admin.getLoginCount() + 1;
        admin.setLoginCount(count);
        admin.setLastLoginIp(IpUtils.getIpAddr(request));
        admin.setLastLoginTime(new Date());
        admin.updateById();

        // 设置token到validCode，用于记录登录用户
        admin.setValidCode(token);
        admin.setRole(roles.get(0));

        // 添加在线用户到Redis中
        adminService.addOnlineAdmin(admin);

        return ResultUtil.result(SysConf.SUCCESS, result);
    }

    private Integer setLoginCommit(HttpServletRequest request) {
        String ip = IpUtils.getIpAddr(request);
        String count = redisUtil.get(RedisConf.LOGIN_LIMIT + RedisConf.SEGMENTATION + ip);
        Integer surplusCount = 5;
        if (StringUtils.isNotEmpty(count)) {
            Integer countTemp = Integer.parseInt(count) + 1;
            surplusCount = surplusCount - countTemp;
            redisUtil.setEx(RedisConf.LOGIN_LIMIT + RedisConf.SEGMENTATION + ip, String.valueOf(countTemp), 10, TimeUnit.MINUTES);
        } else {
            surplusCount = surplusCount - 1;
            redisUtil.setEx(RedisConf.LOGIN_LIMIT + RedisConf.SEGMENTATION + ip, "1", 30, TimeUnit.MINUTES);
        }

        return surplusCount;
    }
}
