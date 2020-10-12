package com.lisa.mvvmframex

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.lisa.mvvmframex.base.recyclerview.BaseAdapter
import com.lisa.mvvmframex.base.rxhttp.PageList
import com.lisa.mvvmframex.base.view.BasePageListFragment
import io.reactivex.rxjava3.core.Observable
import kotlinx.android.synthetic.main.item_text.view.*
import rxhttp.wrapper.param.RxHttp

/**
 * @Description:    java类作用描述
 * @Author:         lisa
 * @CreateDate:     2020/8/4 11:02
 */
class MyFragment : BasePageListFragment<AgentInfoDto>() {
    override fun getObservablePageList(): Observable<PageList<AgentInfoDto>> {
        return RxHttp.get("/community/web/motorhome/proxy/find?page=$pageNo&size=$pageSize")
            .addHeader(
                "Authorization",
                "bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJyb2xlIjpbeyJhdXRob3JpdHkiOiJST0xFX1JFR1VMQVIifSx7ImF1dGhvcml0eSI6IlJPTEVfT1BFUkFUT1JTIn0seyJhdXRob3JpdHkiOiJST0xFX1BMQVRJTlVNX0FHRU5UIn1dLCJ1c2VyX25hbWUiOiIxNTc1NzgzMTg4NiIsInNjb3BlIjpbInJlYWQiLCJ3cml0ZSJdLCJleHAiOjE2MDI5MDI1MTksImF1dGhvcml0aWVzIjpbIlJPTEVfT1BFUkFUT1JTIiwiUk9MRV9SRUdVTEFSIiwiUk9MRV9QTEFUSU5VTV9BR0VOVCJdLCJqdGkiOiI3ZGNkMDVjYy0wN2FhLTRmNmQtOWI2OC00OWUyYjJjZmZlNmYiLCJjbGllbnRfaWQiOiJnYXRld2F5IiwidXNlcm5hbWUiOiIxNTc1NzgzMTg4NiJ9.XhtVkjsd1_AeU0cp0PpD7AtPA9m8q8XRsBZ45Fy9RCuBc_3zjvAlWZ1Jd7LVG3g_hyBUpDFiDor_LbQEBkKUm7e6TXa3ik67yfVr7h99SaR5cWk0Y8hIWUkhnPcs7OylNeBxWZzqsZ8VCJWBZ9hHReieKYVCLzFpFGn4IirOuffd8Fom07FpHKEm0SuDmgSVEyZwyuks1N0EMPvi4aeVqaDt7vtCSFhz5Rw_TqSISkBs9tB0N3S2B7g2R82sdBKbKs48etCXHy12dQooaq7CB7f0XaaExAxBfOxw2NpEd8n73bLxIPoOWkAi5d78lnX7QErRkwvkNc6UV_KF4IjkZg"
            )
            .asResponsePageList(AgentInfoDto::class.java)

    }

    override fun getAdapter(): RecyclerView.Adapter<*> {
        return object : BaseAdapter<AgentInfoDto>(mList, R.layout.item_text) {
            override fun onBindViewHolder(itemView: View, model: AgentInfoDto, position: Int) {
                with(itemView) {
                    //编号
                    tv.text = "编号：${model.proxyNo}"
                }
            }
        }
    }

    override fun go2Login() {

    }

}