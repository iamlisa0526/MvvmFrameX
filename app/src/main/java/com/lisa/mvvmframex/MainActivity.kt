package com.lisa.mvvmframex

import android.util.Log
import com.lisa.mvvmframex.base.view.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*
import rxhttp.wrapper.param.RxHttp
import rxhttp.wrapper.utils.GsonUtil


class MainActivity : BaseActivity() {
    override fun getLayout(): Int {
        return R.layout.activity_main
    }

    override fun init() {
        btn1.setOnClickListener { rxhttpRequest() }
    }

    private fun rxhttpRequest() {
        RxHttp.get("/community/web/moment/hot/rule/findOne") //第一步, 通过get、postXxx、putXxx等方法，确定请求类型
            .addHeader(
                "Authorization",
                "bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJyb2xlIjpbeyJhdXRob3JpdHkiOiJST0xFX1NFTklPUiJ9LHsiYXV0aG9yaXR5IjoiUk9MRV9QTEFUSU5VTV9BR0VOVCJ9XSwidXNlcl9uYW1lIjoiMTU4Njg4OTI2NDciLCJzY29wZSI6WyJyZWFkIiwid3JpdGUiXSwiZXhwIjoxNjAwODI2NzM5LCJhdXRob3JpdGllcyI6WyJST0xFX1NFTklPUiIsIlJPTEVfUExBVElOVU1fQUdFTlQiXSwianRpIjoiOGQ1ZWFlOWQtZmQwNS00OGRiLWI3YzEtMDNiNzE4YWQ3NDJhIiwiY2xpZW50X2lkIjoiZ2F0ZXdheSIsInVzZXJuYW1lIjoiMTU4Njg4OTI2NDcifQ.HRHQ_LIdtcbocsgWhe19l1NzlK4qwhiX8rnoCBRDhhE76uN2s-3Ms7t2QQEIhe94pkb3LonL9qaE6ySYj1plXTzYueUv_SPZDLQ3-6prjF-00Jy-TIYg3LoWzna6d95TZxWSfe0D8uO4PvNzViELQqveJa2r5GAvYzPLhW9pDrlgxedL1uGD5v94irPz4isJ_ZllS3RLopVQf0lDkR_wYMjonRZQfaPY73QGa9FiWF3ivJ3xuq5_NYk_iR_4T8myjzlVF5OuRRD2SVgH8jJjV4PrpGVGLK2mUDCBlkIouBkv1ToNwChglKIleV2va633JAaiweTo7gPcY21reewRtw"
            )
            .asResponse(HotRuleDto::class.java) //第二步, 通过asXxx系列方法，确定返回数据类型
            .subscribe({ ruleDto: HotRuleDto? -> Log.e("请求成功", GsonUtil.toJson(ruleDto)) })
            { throwable: Throwable? -> Log.e("请求失败", GsonUtil.toJson(throwable)) }

    }


}
