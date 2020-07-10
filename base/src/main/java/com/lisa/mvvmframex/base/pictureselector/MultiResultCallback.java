package com.lisa.mvvmframex.base.pictureselector;

import android.util.Log;

import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.listener.OnResultCallbackListener;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * 多选回调
 */
public class MultiResultCallback implements OnResultCallbackListener<LocalMedia> {
    private final static String TAG = MultiResultCallback.class.getSimpleName();

    private WeakReference<GridImageAdapter> mAdapterWeakReference;

    public MultiResultCallback(GridImageAdapter adapter) {
        super();
        this.mAdapterWeakReference = new WeakReference<>(adapter);
    }

    @Override
    public void onResult(List<LocalMedia> result) {
        for (LocalMedia media : result) {
            Log.i(TAG, "是否压缩:" + media.isCompressed());
            Log.i(TAG, "压缩:" + media.getCompressPath());
            Log.i(TAG, "原图:" + media.getPath());
            Log.i(TAG, "是否裁剪:" + media.isCut());
            Log.i(TAG, "裁剪:" + media.getCutPath());
            Log.i(TAG, "是否开启原图:" + media.isOriginal());
            Log.i(TAG, "原图路径:" + media.getOriginalPath());
            Log.i(TAG, "Android Q 特有Path:" + media.getAndroidQToPath());
            Log.i(TAG, "宽高: " + media.getWidth() + "x" + media.getHeight());
            Log.i(TAG, "Size: " + media.getSize());
            // 可以通过PictureSelectorExternalUtils.getExifInterface();方法获取一些额外的资源信息，如旋转角度、经纬度等信息
        }

        //多选
        if (mAdapterWeakReference != null) {
            if (mAdapterWeakReference.get() != null) {
                mAdapterWeakReference.get().setList(result);
                mAdapterWeakReference.get().notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onCancel() {
        Log.i(TAG, "PictureSelector Cancel");
    }

    public GridImageAdapter getAdapter() {
        return mAdapterWeakReference.get();
    }
}