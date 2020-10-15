package com.steel.li_blog_utils.ServerInfo;

import lombok.Data;

/**
 * 内存相关信息
 *
 * @author steel
 */
@Data
public class Mem {

    /**
     * 内存总量
     */
    private double total;

    /**
     * 已用内存
     */
    private double used;

    /**
     * 剩余内存
     */
    private double free;

    /**
     * 使用率
     */
    private double usage;
}
