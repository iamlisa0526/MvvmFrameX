package com.lisa.mvvmframex

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.lisa.mvvmframex.base.customview.dialog.EditDialog
import com.lisa.mvvmframex.base.recyclerview.BaseAdapter
import com.lisa.mvvmframex.base.view.BaseActivity
import com.lisa.mvvmframex.base.view.BaseTabActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_text.view.*
import java.util.ArrayList

class MainActivity : BaseActivity() {
    override fun getLayout(): Int {
        return R.layout.activity_main
    }

    override fun init() {
        val dialog = EditDialog(mContext).title("修改昵称")
            .hint("请输入昵称")
        btn1.setOnClickListener { dialog.show() }
    }


}
