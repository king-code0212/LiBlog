package com.steel.li_blog_xo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.steel.li_blog_base.service.SuperService;
import com.steel.li_blog_common.entity.SysDictData;
import com.steel.li_blog_xo.vo.SysDictDataVO;


import java.util.List;
import java.util.Map;

/**
 * <p>
 * 字典数据 服务类
 * </p>
 *
 * @author steel
 * @since 2020年2月15日21:06:45
 */
public interface SysDictDataService extends SuperService<SysDictData> {
    /**
     * 获取数据字典列表
     *
     * @param sysDictDataVO
     * @return
     */
    public IPage<SysDictData> getPageList(SysDictDataVO sysDictDataVO);

    /**
     * 新增数据字典
     *
     * @param sysDictDataVO
     */
    public String addSysDictData(SysDictDataVO sysDictDataVO);

    /**
     * 编辑数据字典
     *
     * @param sysDictDataVO
     */
    public String editSysDictData(SysDictDataVO sysDictDataVO);

    /**
     * 批量删除数据字典
     *
     * @param sysDictDataVOList
     */
    public String deleteBatchSysDictData(List<SysDictDataVO> sysDictDataVOList);

    /**
     * 根据字典类型获取字典数据
     *
     * @param dictType
     * @return
     */
    public Map<String, Object> getListByDictType(String dictType);

    /**
     * 根据字典类型数组获取字典数据
     *
     * @param dictTypeList
     * @return
     */
    public Map<String, Map<String, Object>> getListByDictTypeList(List<String> dictTypeList);

}
