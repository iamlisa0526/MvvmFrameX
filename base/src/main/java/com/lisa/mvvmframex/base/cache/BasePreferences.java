package com.lisa.mvvmframex.base.cache;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.lisa.mvvmframex.base.utils.EditorUtil;

import java.util.Map;

/**
 * @Description: Preferences基类
 * @Author: lisa
 * @CreateDate: 2020/5/6 15:54
 */
public abstract class BasePreferences {
    protected static Application mApplication;
    protected SharedPreferences mPreferences;

    public BasePreferences() {
        if (TextUtils.isEmpty(getSPFileName())) {
            mPreferences = PreferenceManager.getDefaultSharedPreferences(mApplication);
        } else {
            mPreferences = mApplication.getSharedPreferences(getSPFileName(), Context.MODE_PRIVATE);
        }
    }

    protected abstract String getSPFileName();

    public String getString(String key, String defValue) {
        return mPreferences.getString(key, defValue);
    }

    public Boolean getBoolean(String key, boolean defBool) {
        return mPreferences.getBoolean(key, defBool);
    }

    public int getInt(String key, int defValue) {
        return mPreferences.getInt(key, defValue);
    }

    public long getLong(String key, long defValue) {
        return mPreferences.getLong(key, defValue);
    }

    public void setBoolean(String key, boolean bool) {
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putBoolean(key, bool);
        EditorUtil.fastCommit(editor);
    }

    public void setLong(String key, long value) {
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putLong(key, value);
        EditorUtil.fastCommit(editor);
    }

    public void setString(String key, String value) {
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putString(key, value);
        EditorUtil.fastCommit(editor);
    }

    public void setInt(String key, int value) {
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putInt(key, value);
        EditorUtil.fastCommit(editor);
    }

    public String getString(String key) {
        return getString(key, "");
    }

    public void remove(String key) {
        if (!TextUtils.isEmpty(key) && contains(key)) {
            SharedPreferences.Editor editor = mPreferences.edit();
            editor.remove(key);
            EditorUtil.fastCommit(editor);
        }
    }

    public boolean contains(String key) {
        return mPreferences.contains(key);
    }

    public void clearAll() {
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.clear();
        EditorUtil.fastCommit(editor);

    }

    public Map<String, ?> getAll() {
        return mPreferences.getAll();
    }

}
