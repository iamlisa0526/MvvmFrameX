package com.lisa.mvvmframex

/**
 * @Description:    代理信息Dto
 * @Author:         lisa
 * @CreateDate:     2020/7/29 09:28
 */
data class AgentInfoDto(
        //------------------业务需要客户端自己添加的字段start---------------------
        /**
         * 代理类型
         */
        var agentType: String = "",

        /**
         * 编号**代理的代理份数上限
         */
        var agentLimit: Int = 0,

        /**
         * 编号**代理的代理单价（分）
         */
        var agentUnitPrice: Int = 0,

        /**
         * 剩余份数
         */
        var agentRemainNumber: Int = 0,

        //------------------业务需要客户端自己添加的字段end-----------------------
        /**
         * id
         */
        var motorhomeProxyId: String = "",

        /**
         * 当前用户已购买数量
         */
        var buyNum: Int = 0,

        /**
         * 目前代理人数(金牌代理)
         */
        var goldAgentNum: Int = 0,

        /**
         * 目前代理份数(金牌代理)
         */
        var goldServings: Int = 0,

        /**
         * 目前代理人数(白金代理)
         */
        var platinumAgentNum: Int = 0,

        /**
         * 目前代理份数(白金代理)
         */
        var platinumServings: Int = 0,

        /**
         * 代理编号
         */
        var proxyNo: String = "",

        /**
         * 用户名
         */
        var username: String = ""
)