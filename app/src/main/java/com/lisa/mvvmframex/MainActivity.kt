package com.lisa.mvvmframex

import android.util.Log
import android.view.View
import com.google.gson.Gson
import com.lisa.mvvmframex.base.rxhttp.ErrorInfo
import com.lisa.mvvmframex.base.rxhttp.OnError
import com.lisa.mvvmframex.base.rxhttp.PageList
import com.lisa.mvvmframex.base.utils.GsonUtil
import com.lisa.mvvmframex.base.view.BaseActivity
import com.rxjava.rxlife.RxLife
import io.reactivex.rxjava3.functions.Consumer
import kotlinx.android.synthetic.main.activity_main.*
import rxhttp.wrapper.param.RxHttp


class MainActivity : BaseActivity() {
    override fun getLayout(): Int {
        return R.layout.activity_main
    }

    override fun init() {
        btn1.setOnClickListener {
//            rxhttpRequest()
            sendGet()
        }
    }

    private fun rxhttpRequest() {

        RxHttp.get("/community/web/moment/hot/rule/findOne") //第一步, 通过get、postXxx、putXxx等方法，确定请求类型
            .addHeader(
                "Authorization",
                " bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJyb2xlIjpbeyJhdXRob3JpdHkiOiJST0xFX1NFTklPUiJ9LHsiYXV0aG9yaXR5IjoiUk9MRV9QTEFUSU5VTV9BR0VOVCJ9LHsiYXV0aG9yaXR5IjoiUk9MRV9DT01QQU5ZX1ZFUklGWSJ9XSwidXNlcl9uYW1lIjoiMTU4Njg4OTI2NDciLCJzY29wZSI6WyJyZWFkIiwid3JpdGUiXSwiZXhwIjoxNjAxNDQ1MzU1LCJhdXRob3JpdGllcyI6WyJST0xFX0NPTVBBTllfVkVSSUZZIiwiUk9MRV9TRU5JT1IiLCJST0xFX1BMQVRJTlVNX0FHRU5UIl0sImp0aSI6IjIwNGU2YmY1LWVhMjQtNGFmYS04NzljLTA5NWRkMjZjYTk1NyIsImNsaWVudF9pZCI6ImdhdGV3YXkiLCJ1c2VybmFtZSI6IjE1ODY4ODkyNjQ3In0.hcZPxr80wrt0RAmTXPvO6swqy9gLdRkX_kQy7xiKxEtc1OClTVvhl6LKmP1qIqJKqvPcJcm6-KC2jPgIrdE-CWOqOMMt29rBieJZs-tWDTlSlIYQLJMs6Qiiru7ks-Ge61HsN1fJ_HKaiQXxkWuqN5SpZiI4ubmh56BGSuAZvbKbdb2azwSNv7WSoKdeIlY8Nexgj6ez7gjUAPHvojo8QIp3mx_6YlIaY5eXsQgW046SJwv998htc5LdY8n9Ukzc5BBRdtE90-aIOxc9PVtZkkGD39-7Velgl1inOgzfH3M8B4K-THK3SbZ9ihjaTg63Mzya27hbGfDfGxf0wjr17A"
            )
            .asResponse(HotRuleDto::class.java) //第二步, 通过asXxx系列方法，确定返回数据类型
            .subscribe({ ruleDto -> Log.e("请求成功", GsonUtil.toJson(ruleDto)) })
            { throwable -> Log.e("请求失败", GsonUtil.toJson(throwable)) }

    }

    //发送Get请求，获取文章列表
    fun sendGet() {
        RxHttp.putJson("/user/user/write-off")
            .addHeader(
                "Authorization",
                " bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJyb2xlIjpbeyJhdXRob3JpdHkiOiJST0xFX1NFTklPUiJ9LHsiYXV0aG9yaXR5IjoiUk9MRV9QTEFUSU5VTV9BR0VOVCJ9LHsiYXV0aG9yaXR5IjoiUk9MRV9DT01QQU5ZX1ZFUklGWSJ9XSwidXNlcl9uYW1lIjoiMTU4Njg4OTI2NDciLCJzY29wZSI6WyJyZWFkIiwid3JpdGUiXSwiZXhwIjoxNjAxNDQ1MzU1LCJhdXRob3JpdGllcyI6WyJST0xFX0NPTVBBTllfVkVSSUZZIiwiUk9MRV9TRU5JT1IiLCJST0xFX1BMQVRJTlVNX0FHRU5UIl0sImp0aSI6IjIwNGU2YmY1LWVhMjQtNGFmYS04NzljLTA5NWRkMjZjYTk1NyIsImNsaWVudF9pZCI6ImdhdGV3YXkiLCJ1c2VybmFtZSI6IjE1ODY4ODkyNjQ3In0.hcZPxr80wrt0RAmTXPvO6swqy9gLdRkX_kQy7xiKxEtc1OClTVvhl6LKmP1qIqJKqvPcJcm6-KC2jPgIrdE-CWOqOMMt29rBieJZs-tWDTlSlIYQLJMs6Qiiru7ks-Ge61HsN1fJ_HKaiQXxkWuqN5SpZiI4ubmh56BGSuAZvbKbdb2azwSNv7WSoKdeIlY8Nexgj6ez7gjUAPHvojo8QIp3mx_6YlIaY5eXsQgW046SJwv998htc5LdY8n9Ukzc5BBRdtE90-aIOxc9PVtZkkGD39-7Velgl1inOgzfH3M8B4K-THK3SbZ9ihjaTg63Mzya27hbGfDfGxf0wjr17A"
            )
            .asResponse(Boolean::class.java)
            .to(RxLife.toMain(this)) //感知生命周期，并在主线程回调
            .subscribe(
                { b: Boolean ->
                    Log.e("注销成功", b.toString())
                },
                { error ->
                    Log.e("注销失败", ErrorInfo(error).errorMsg)
                }
            )
    }


}
