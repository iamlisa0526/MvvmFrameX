package com.lisa.mvvmframex.base.utils;


import com.lisa.mvvmframex.base.cache.BasePreferences;

/**
 * @Description: java类作用描述
 * @Author: lisa
 * @CreateDate: 2020/5/6 13:48
 */
public class BasicDataPreferenceUtil extends BasePreferences {
    private static final String BASIC_DATA_PREFERENCE_FILE_NAME = "network_api_module_basic_data_preference";
    private static BasicDataPreferenceUtil mInstance;

    public static BasicDataPreferenceUtil getInstance() {
        if (mInstance == null) {
            synchronized (BasicDataPreferenceUtil.class) {
                if (mInstance == null) {
                    mInstance = new BasicDataPreferenceUtil();
                }
            }
        }
        return mInstance;
    }

    @Override
    protected String getSPFileName() {
        return BASIC_DATA_PREFERENCE_FILE_NAME;
    }
}
