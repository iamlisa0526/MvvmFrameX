package com.lisa.mvvmframex.base.dto

/**
 * @Description: 分页对象
 * @Author: lisa
 * @CreateDate: 2020/5/18 17:17
 */
class BasePageDto<T> {
    /**
     * 总条数
     */
    var totalElements = 0

    /**
     * 数据集合
     */
    var content: ArrayList<T> = arrayListOf()

}