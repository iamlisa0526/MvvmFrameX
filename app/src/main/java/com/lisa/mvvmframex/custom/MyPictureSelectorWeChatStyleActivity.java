package com.lisa.mvvmframex.custom;

import com.luck.picture.lib.PictureSelectorWeChatStyleActivity;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.config.PictureSelectionConfig;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.List;

/**
 * @Description: java类作用描述
 * @Author: lisa
 * @CreateDate: 2020/7/10 09:27
 */
public class MyPictureSelectorWeChatStyleActivity extends PictureSelectorWeChatStyleActivity {
    @Override
    protected void onChangeData(List<LocalMedia> list) {
        super.onChangeData(list);
        if (list.size() == 1) {
            for (LocalMedia media : list) {
                if (PictureMimeType.getMimeType(media.getMimeType()) == PictureConfig.TYPE_VIDEO) {
                    PictureSelectionConfig.getCleanInstance().maxSelectNum = 1;
                    return;
                }
            }
        }
    }
}
