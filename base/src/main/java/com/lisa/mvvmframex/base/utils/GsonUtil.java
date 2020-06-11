//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.lisa.mvvmframex.base.utils;


import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.lang.reflect.Type;

/**
 * @Description: gson工具类
 * @Author: lisa
 * @CreateDate: 2020/4/30 15:01
 */
public class GsonUtil {
    private static Gson mGson = new Gson();

    public static <T> T fromJson(String json, Class<T> classOfT) {
        try {
            return mGson.fromJson(json, classOfT);
        } catch (JsonSyntaxException var3) {
            var3.printStackTrace();
            return null;
        }
    }

    public static <T> T fromJson(String json, Type typeOfT) {
        try {
            return mGson.fromJson(json, typeOfT);
        } catch (JsonSyntaxException var3) {
            var3.printStackTrace();
            return null;
        }
    }

    public static String toJson(Object src) {
        try {
            return mGson.toJson(src);
        } catch (Exception var2) {
            var2.printStackTrace();
            return null;
        }
    }
}
