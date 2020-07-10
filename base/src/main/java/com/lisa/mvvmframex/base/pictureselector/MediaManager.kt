package com.lisa.mvvmframex.base.pictureselector

import android.app.Activity
import androidx.fragment.app.Fragment
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import com.luck.picture.lib.config.PictureMimeType
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.listener.OnResultCallbackListener

/**
 * @Description:    媒体文件管理类（图片、视频、音频）
 * @Author:         lisa
 * @CreateDate:     2020/7/9 09:18
 */
object MediaManager {
    /**
     * Fragment添加多张图片（最多9张）
     */
    @JvmStatic
    fun addFragmentMultiImage(
        context: Fragment,
        multiResultCallback: MultiResultCallback
    ) {
        addFragmentMedia(context, 9, PictureMimeType.ofImage(), multiResultCallback)

    }

    /**
     * Fragment添加单张图片
     */
    @JvmStatic
    fun addFragmentSingleImage(
        context: Fragment,
        onResultCallbackListener: OnResultCallbackListener<LocalMedia>
    ) {
        addFragmentMedia(context, 1, PictureMimeType.ofImage(), onResultCallbackListener)
    }

    /**
     * Fragment添加多个视频（最多9个）
     */
    @JvmStatic
    fun addFragmentMultiVideo(
        context: Fragment,
        multiResultCallback: MultiResultCallback
    ) {
        addFragmentMedia(context, 9, PictureMimeType.ofVideo(), multiResultCallback)

    }

    /**
     * Fragment添加单个视频
     */
    @JvmStatic
    fun addFragmentSingleVideo(
        context: Fragment,
        onResultCallbackListener: OnResultCallbackListener<LocalMedia>
    ) {
        addFragmentMedia(context, 1, PictureMimeType.ofVideo(), onResultCallbackListener)
    }

    /**
     * Fragment添加media(图片，视频，音频)
     */
    @JvmStatic
    private fun addFragmentMedia(
        context: Fragment,
        maxNum: Int,
        mimeType: Int,
        onResultCallbackListener: OnResultCallbackListener<LocalMedia>
    ) {
        setSelectConfig(PictureSelector.create(context), maxNum, mimeType, onResultCallbackListener)
    }

    /**
     * Activity添加多张图片（最多9张）
     */
    @JvmStatic
    fun addActivityMultiImage(
        context: Activity,
        multiResultCallback: MultiResultCallback
    ) {
        addActivityImage(context, 9, PictureMimeType.ofImage(), multiResultCallback)

    }

    /**
     * Activity添加单张图片
     */
    @JvmStatic
    fun addActivitySingleImage(
        context: Activity,
        onResultCallbackListener: OnResultCallbackListener<LocalMedia>
    ) {
        addActivityImage(context, 1, PictureMimeType.ofImage(), onResultCallbackListener)
    }

    /**
     * Activity添加多个视频（最多9个）
     */
    @JvmStatic
    fun addActivityMultiVideo(
        context: Activity,
        multiResultCallback: MultiResultCallback
    ) {
        addActivityImage(context, 9, PictureMimeType.ofVideo(), multiResultCallback)

    }

    /**
     * Activity添加单个视频
     */
    @JvmStatic
    fun addActivitySingleVideo(
        context: Activity,
        onResultCallbackListener: OnResultCallbackListener<LocalMedia>
    ) {
        addActivityImage(context, 1, PictureMimeType.ofVideo(), onResultCallbackListener)
    }

    /**
     * Activity添加图片
     */
    @JvmStatic
    private fun addActivityImage(
        context: Activity,
        maxNum: Int,
        mimeType: Int,
        onResultCallbackListener: OnResultCallbackListener<LocalMedia>
    ) {
        setSelectConfig(PictureSelector.create(context), maxNum, mimeType, onResultCallbackListener)
    }

    /**
     * 图片选择器参数配置
     */
    @JvmStatic
    private fun setSelectConfig(
        selector: PictureSelector,
        maxNum: Int,
        mimeType: Int,
        onResultCallbackListener: OnResultCallbackListener<LocalMedia>
    ) {
        selector.openGallery(mimeType) // 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
            .imageEngine(PicassoEngine.createPicassoEngine())// 外部传入图片加载引擎，必传项
            .maxSelectNum(maxNum) // 最大图片选择数量
            .selectionMode(if (maxNum > 1) PictureConfig.MULTIPLE else PictureConfig.SINGLE) // 多选 or 单选
            .isWeChatStyle(true) //微信style
            .isPreviewImage(true) // 是否可预览图片
            .isPreviewVideo(true)// 是否可预览视频
            .isEnablePreviewAudio(true) // 是否可播放音频
//            .isOriginalImageControl(true)// 是否显示原图控制按钮，如果设置为true则用户可以自由选择是否使用原图，压缩、裁剪功能将会失效
            .isCamera(true) // 是否显示拍照按钮
            .isGif(true) // 是否显示gif图片
            .isEnableCrop(true) //是否可裁剪
            .isMultipleSkipCrop(true)// 多图裁剪时是否支持跳过，默认支持
            .freeStyleCropEnabled(true) // 裁剪框是否可拖拽
            .isCompress(true) //是否压缩
            .imageSpanCount(4) // 每行显示个数
            .minimumCompressSize(100) // 小于100kb的图片不压缩
            .isMaxSelectEnabledMask(true)// 选择数到了最大阀值列表是否启用蒙层效果
            .selectionData(if (maxNum > 1) (onResultCallbackListener as MultiResultCallback).adapter.data else null)// 是否传入已选图片
            .forResult(onResultCallbackListener)
    }

}