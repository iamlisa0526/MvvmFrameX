package com.lisa.mvvmframex.base.utils;

import android.annotation.TargetApi;
import android.content.SharedPreferences;
import android.os.Build;

/**
 * @Description: Editor工具类
 * @Author: lisa
 * @CreateDate: 2020/5/6 16:44
 */
public class EditorUtil {
    public static void fastCommit(final SharedPreferences.Editor editor) {
        //editor.apply could not commit your preferences changes in time on Android 4.3
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            GingerbreadCompatLayer.fastCommit(editor);
        } else {
            //there's no fast commit below GINGERBREAD
            editor.commit();
        }
    }

    @TargetApi(9)
    private static class GingerbreadCompatLayer {
        public static void fastCommit(final SharedPreferences.Editor editor) {
            editor.apply();
        }
    }
}
