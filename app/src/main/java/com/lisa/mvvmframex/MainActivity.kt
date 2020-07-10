package com.lisa.mvvmframex

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.lisa.mvvmframex.base.pictureselector.FullyGridLayoutManager
import com.lisa.mvvmframex.base.pictureselector.GridImageAdapter
import com.lisa.mvvmframex.base.pictureselector.MediaManager
import com.lisa.mvvmframex.base.pictureselector.MultiResultCallback
import com.luck.picture.lib.decoration.GridSpacingItemDecoration
import com.luck.picture.lib.tools.ScreenUtils
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: GridImageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recycler_view.layoutManager = FullyGridLayoutManager(
            this,
            4, GridLayoutManager.VERTICAL, false
        )
        adapter = GridImageAdapter(this, GridImageAdapter.OnAddClickListener {
            MediaManager.addActivityMultiImage(this,
                MultiResultCallback(
                    adapter
                )
            )
        })
        recycler_view.adapter = adapter
        recycler_view.addItemDecoration(
            GridSpacingItemDecoration(
                4,
                ScreenUtils.dip2px(this, 8f),
                false
            )
        )

        iv_image.setOnClickListener {
            MediaManager.addActivitySingleImage(this,
                MultiResultCallback(
                    iv_image
                )
            )
        }
    }
}
