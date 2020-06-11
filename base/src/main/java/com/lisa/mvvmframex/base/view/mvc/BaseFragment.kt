package com.lisa.mvvmframex.base.view.mvc

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

/**
 * @Description: Fragment基类
 * @Author: lisa
 * @CreateDate: 2020/6/8 09:10
 */
abstract class BaseFragment : Fragment() {
    protected lateinit var mContext: FragmentActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this.requireActivity()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(getLayout(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    /**
     * 初始化
     */
    abstract fun init()

    /**
     * xml布局
     */
    @LayoutRes
    abstract fun getLayout(): Int

}