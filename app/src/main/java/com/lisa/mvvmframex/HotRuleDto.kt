package com.lisa.mvvmframex

/**
 * @Description:    上热门规则Dto
 * @Author:         lisa
 * @CreateDate:     2020/6/17 11:28
 */
class HotRuleDto {
    /**
     * 基础价格（分）
     */
    var basePrice = 0

    /**
     * 最高竞价（分）
     */
    var maximumBid = 0

    /**
     * 期限(ms,后台管理系统以"月(30天)"为单位)
     */
    var term = 0L
}
