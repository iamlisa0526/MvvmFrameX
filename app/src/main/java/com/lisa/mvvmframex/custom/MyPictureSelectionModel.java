package com.lisa.mvvmframex.custom;

import com.luck.picture.lib.PictureSelectionModel;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.listener.OnResultCallbackListener;

/**
 * @Description: java类作用描述
 * @Author: lisa
 * @CreateDate: 2020/7/10 09:25
 */
public class MyPictureSelectionModel extends PictureSelectionModel {
    public MyPictureSelectionModel(PictureSelector selector, int chooseMode) {
        super(selector, chooseMode);
    }

    public MyPictureSelectionModel(PictureSelector selector, int chooseMode, boolean camera) {
        super(selector, chooseMode, camera);
    }


    @Override
    public void forResult(OnResultCallbackListener listener) {
        super.forResult(listener);
    }
}
