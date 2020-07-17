package com.lisa.mvvmframex

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.lisa.mvvmframex.base.recyclerview.BaseAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_text.view.*

class MainActivity : AppCompatActivity() {

    private val mlist1 = arrayListOf<Any>()
    private val mlist2 = arrayListOf<Any>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        for (i in 1..10) {
            mlist1.add(Any())
        }

        for (i in 1..40) {
            mlist2.add(Any())
        }

        rv1.layoutManager = LinearLayoutManager(this)
        rv1.adapter = object : BaseAdapter<Any>(mlist1, R.layout.item_text) {
            override fun onBindViewHolder(itemView: View, model: Any, position: Int) {
                itemView.tv.text="11111111111111"
            }
        }

        rv2.layoutManager = LinearLayoutManager(this)
        rv2.adapter = object : BaseAdapter<Any>(mlist2, R.layout.item_text) {
            override fun onBindViewHolder(itemView: View, model: Any, position: Int) {
                itemView.tv.text="222222222222222"
            }
        }
    }
}
