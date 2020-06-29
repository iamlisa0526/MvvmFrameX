package com.lisa.mvvmframex.base.cache;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Base64;

import com.lisa.mvvmframex.base.BaseApplication;
import com.lisa.mvvmframex.base.utils.EditorUtil;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.Map;

/**
 * @Description: Preferences基类
 * @Author: lisa
 * @CreateDate: 2020/5/6 15:54
 */
public abstract class BasePreferences {
    protected SharedPreferences mPreferences;

    public BasePreferences() {
        if (TextUtils.isEmpty(getSPFileName())) {
            mPreferences = PreferenceManager.getDefaultSharedPreferences(BaseApplication.Companion.getApplication());
        } else {
            mPreferences = BaseApplication.Companion.getApplication().getSharedPreferences(getSPFileName(), Context.MODE_PRIVATE);
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

    /**
     * 使用SharedPreference保存序列化对象
     * 用Base64.encode将字节文件转换成Base64编码保存在String中
     *
     * @param key    储存对象的key
     * @param object object对象  对象必须实现Serializable序列化，否则会出问题，
     *               out.writeObject 无法写入 Parcelable 序列化的对象
     */
    public void setObject(String key, Object object) {
        SharedPreferences.Editor editor = mPreferences.edit();
        //创建字节输出流
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        //创建字节对象输出流
        ObjectOutputStream out = null;
        try {
            //然后通过将字对象进行64转码，写入sp中
            out = new ObjectOutputStream(baos);
            out.writeObject(object);
            String objectValue = new String(Base64.encode(baos.toByteArray(), Base64.DEFAULT));
            editor.putString(key, objectValue);
            EditorUtil.fastCommit(editor);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.close();
                }

                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取SharedPreference保存的对象
     * 使用Base64解密String，返回Object对象
     *
     * @param key     储存对象的key
     * @param <T>     泛型
     * @return 返回保存的对象
     */
    public <T> T getObject(String key) {
        if (mPreferences.contains(key)) {
            String objectValue = mPreferences.getString(key, null);
            byte[] buffer = Base64.decode(objectValue, Base64.DEFAULT);
            //一样通过读取字节流，创建字节流输入流，写入对象并作强制转换
            ByteArrayInputStream bais = new ByteArrayInputStream(buffer);
            ObjectInputStream ois = null;
            try {
                ois = new ObjectInputStream(bais);
                T t = (T) ois.readObject();
                return t;
            } catch (StreamCorruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (bais != null) {
                        bais.close();
                    }

                    if (ois != null) {
                        ois.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
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
