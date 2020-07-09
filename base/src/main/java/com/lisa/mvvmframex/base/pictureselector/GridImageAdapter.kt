package com.lisa.mvvmframex.base.pictureselector

import android.content.Context
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lisa.mvvmframex.base.R
import com.lisa.mvvmframex.base.utils.PicassoUtil.setImage
import com.luck.picture.lib.config.PictureMimeType
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.listener.OnItemClickListener
import com.luck.picture.lib.tools.DateUtils
import java.io.File
import java.util.*

/**
 * @author：luck
 * @date：2016-7-27 23:02
 * @describe：GridImageAdapter
 */
class GridImageAdapter(
    context: Context?,
    mOnAddClickListener: OnAddClickListener
) : RecyclerView.Adapter<GridImageAdapter.ViewHolder>() {
    private val mInflater: LayoutInflater
    private var list: List<LocalMedia>? = ArrayList()
    private var selectMax = 9

    /**
     * 点击添加图片跳转
     */
    private val mOnAddClickListener: OnAddClickListener

    interface OnAddClickListener {
        fun onAddClick()
    }

    /**
     * 删除
     */
    fun delete(position: Int) {
        try {
            if (position != RecyclerView.NO_POSITION && list!!.size > position) {
                list.removeAt(position)
                notifyItemRemoved(position)
                notifyItemRangeChanged(position, list!!.size)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun setSelectMax(selectMax: Int) {
        this.selectMax = selectMax
    }

    fun setList(list: List<LocalMedia>?) {
        this.list = list
    }

    val data: List<LocalMedia>
        get() = (if (list == null) ArrayList() else list)!!

    fun remove(position: Int) {
        if (list != null && position < list!!.size) {
            list.removeAt(position)
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var mImg: ImageView
        var mIvDel: ImageView
        var tvDuration: TextView

        init {
            mImg = view.findViewById(R.id.fiv)
            mIvDel = view.findViewById(R.id.iv_del)
            tvDuration = view.findViewById(R.id.tv_duration)
        }
    }

    override fun getItemCount(): Int {
        return if (list!!.size < selectMax) {
            list!!.size + 1
        } else {
            list!!.size
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (isShowAddItem(position)) {
            TYPE_CAMERA
        } else {
            TYPE_PICTURE
        }
    }

    /**
     * 创建ViewHolder
     */
    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        i: Int
    ): ViewHolder {
        val view = mInflater.inflate(
            R.layout.gv_filter_image,
            viewGroup, false
        )
        return ViewHolder(view)
    }

    private fun isShowAddItem(position: Int): Boolean {
        val size = if (list!!.size == 0) 0 else list!!.size
        return position == size
    }

    /**
     * 设置值
     */
    override fun onBindViewHolder(
        viewHolder: ViewHolder,
        position: Int
    ) {
        //少于8张，显示继续添加的图标
        if (getItemViewType(position) == TYPE_CAMERA) {
            viewHolder.mImg.setImageResource(R.drawable.ic_add_image)
            viewHolder.mImg.setOnClickListener { v: View? -> mOnAddClickListener.onAddClick() }
            viewHolder.mIvDel.visibility = View.INVISIBLE
        } else {
            viewHolder.mIvDel.visibility = View.VISIBLE
            viewHolder.mIvDel.setOnClickListener { view: View? ->
                val index = viewHolder.adapterPosition
                // 这里有时会返回-1造成数据下标越界,具体可参考getAdapterPosition()源码，
                // 通过源码分析应该是bindViewHolder()暂未绘制完成导致，知道原因的也可联系我~感谢
                if (index != RecyclerView.NO_POSITION && list!!.size > index) {
                    list.removeAt(index)
                    notifyItemRemoved(index)
                    notifyItemRangeChanged(index, list!!.size)
                }
            }
            val media = list!![position]
            if (media == null
                || TextUtils.isEmpty(media.path)
            ) {
                return
            }
            val chooseModel = media.chooseModel
            val path: String
            path = if (media.isCut && !media.isCompressed) {
                // 裁剪过
                media.cutPath
            } else if (media.isCompressed || media.isCut && media.isCompressed) {
                // 压缩过,或者裁剪同时压缩过,以最终压缩过图片为准
                media.compressPath
            } else {
                // 原图
                media.path
            }
            Log.i(TAG, "原图地址::" + media.path)
            if (media.isCut) {
                Log.i(TAG, "裁剪地址::" + media.cutPath)
            }
            if (media.isCompressed) {
                Log.i(
                    TAG,
                    "压缩地址::" + media.compressPath
                )
                Log.i(
                    TAG,
                    "压缩后文件大小::" + File(media.compressPath).length() / 1024 + "k"
                )
            }
            if (!TextUtils.isEmpty(media.androidQToPath)) {
                Log.i(
                    TAG,
                    "Android Q特有地址::" + media.androidQToPath
                )
            }
            if (media.isOriginal) {
                Log.i(TAG, "是否开启原图功能::" + true)
                Log.i(
                    TAG,
                    "开启原图功能后地址::" + media.originalPath
                )
            }
            val duration = media.duration
            viewHolder.tvDuration.visibility =
                if (PictureMimeType.isHasVideo(media.mimeType)) View.VISIBLE else View.GONE
            if (chooseModel == PictureMimeType.ofAudio()) {
                viewHolder.tvDuration.visibility = View.VISIBLE
                viewHolder.tvDuration.setCompoundDrawablesRelativeWithIntrinsicBounds(
                    R.drawable.picture_icon_audio,
                    0,
                    0,
                    0
                )
            } else {
                viewHolder.tvDuration.setCompoundDrawablesRelativeWithIntrinsicBounds(
                    R.drawable.picture_icon_video,
                    0,
                    0,
                    0
                )
            }
            viewHolder.tvDuration.text = DateUtils.formatDurationTime(
                duration
            )
            if (chooseModel == PictureMimeType.ofAudio()) {
                viewHolder.mImg.setImageResource(R.drawable.picture_audio_placeholder)
            } else {
                setImage(path, viewHolder.mImg, 0)
            }
            //itemView 的点击事件
            if (mItemClickListener != null) {
                viewHolder.itemView.setOnClickListener { v: View? ->
                    val adapterPosition = viewHolder.adapterPosition
                    mItemClickListener!!.onItemClick(v, adapterPosition)
                }
            }
            //            else {
//                String mimeType = media.getMimeType();
//                int mediaType = PictureMimeType.getMimeType(mimeType);
//                switch (mediaType) {
//                    case PictureConfig.TYPE_VIDEO:
//                        // 预览视频
//                        PictureSelector.create(viewHolder.itemView.getContext())
//                                .themeStyle(R.style.picture_default_style)
////                                        .setPictureStyle(mPictureParameterStyle)// 动态自定义相册主题
//                                .externalPictureVideo(TextUtils.isEmpty(media.getAndroidQToPath()) ? media.getPath() : media.getAndroidQToPath());
//                        break;
//                    case PictureConfig.TYPE_AUDIO:
//                        // 预览音频
//                        PictureSelector.create(MainActivity.this)
//                                .externalPictureAudio(PictureMimeType.isContent(media.getPath()) ? media.getAndroidQToPath() : media.getPath());
//                        break;
//                    default:
//                        // 预览图片 可自定长按保存路径
////                        PictureWindowAnimationStyle animationStyle = new PictureWindowAnimationStyle();
////                        animationStyle.activityPreviewEnterAnimation = R.anim.picture_anim_up_in;
////                        animationStyle.activityPreviewExitAnimation = R.anim.picture_anim_down_out;
//                        PictureSelector.create(MainActivity.this)
//                                .themeStyle(R.style.picture_default_style) // xml设置主题
////                                .setPictureStyle(mPictureParameterStyle)// 动态自定义相册主题
//                                //.setPictureWindowAnimationStyle(animationStyle)// 自定义页面启动动画
//                                .setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)// 设置相册Activity方向，不设置默认使用系统
//                                .isNotPreviewDownload(true)// 预览图片长按是否可以下载
//                                //.bindCustomPlayVideoCallback(new MyVideoSelectedPlayCallback(getContext()))// 自定义播放回调控制，用户可以使用自己的视频播放界面
//                                .imageEngine(PicassoEngine.createPicassoEngine())// 外部传入图片加载引擎，必传项
//                                .openExternalPreview(position, getData());
//                        break;
//                }
//            }
            if (mItemLongClickListener != null) {
                viewHolder.itemView.setOnLongClickListener { v: View? ->
                    val adapterPosition = viewHolder.adapterPosition
                    mItemLongClickListener!!.onLongClick(viewHolder, adapterPosition, v)
                    true
                }
            }
        }
    }

    private var mItemClickListener: OnItemClickListener? = null
    fun setOnItemClickListener(l: OnItemClickListener?) {
        mItemClickListener = l
    }

    interface OnItemLongClickListener {
        fun onLongClick(
            holder: RecyclerView.ViewHolder?,
            position: Int,
            v: View?
        )
    }

    private var mItemLongClickListener: OnItemLongClickListener? =
        null

    fun setItemLongClickListener(l: OnItemLongClickListener?) {
        mItemLongClickListener = l
    }

    companion object {
        const val TAG = "PictureSelector"
        const val TYPE_CAMERA = 1
        const val TYPE_PICTURE = 2
    }

    init {
        mInflater = LayoutInflater.from(context)
        this.mOnAddClickListener = mOnAddClickListener
    }
}