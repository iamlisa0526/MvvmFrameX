package com.lisa.mvvmframex.base.dto;

import java.util.List;

/**
 * @Description: 分页对象
 * @Author: lisa
 * @CreateDate: 2020/5/18 17:17
 */
public class BasePageDto<T> {
    /**
     * 总条数
     */
    private int totalElements;

    /**
     * 数据集合
     */
    private List<T> content;

    public int getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(int totalElements) {
        this.totalElements = totalElements;
    }

    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }
}
