package com.lisa.mvvmframex.base.rxhttp;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import rxhttp.wrapper.annotation.Parser;
import rxhttp.wrapper.entity.ParameterizedTypeImpl;
import rxhttp.wrapper.exception.ParseException;
import rxhttp.wrapper.parse.AbstractParser;
import rxhttp.wrapper.utils.GsonUtil;

@Parser(name = "Response", wrappers = {List.class, PageList.class})
public class ResponseParser<T> extends AbstractParser<T> {

    //注意，以下两个构造方法是必须的
    protected ResponseParser() {
        super();
    }

    public ResponseParser(Type type) {
        super(type);
    }

    @Override
    public T onParse(okhttp3.Response response) throws IOException {
        //第一步，解析code、msg字段，把data当成String对象
        final Type type = ParameterizedTypeImpl.get(Response.class, String.class);
        Response<String> data = convert(response, type);
        T t = null;
        if (data.getCode() == 0) {  //0代表正常情况
            //第二步，取出data字段，转换成我们想要的对象
            t = GsonUtil.getObject(data.getData(), mType);
        }
        if (t == null && mType == String.class) {
            //判断我们传入的泛型是String对象，就给t赋值""字符串，确保t不为null
            t = (T) "";
        }
        if (data.getCode() != 0 || t == null) {
            throw new ParseException(String.valueOf(data.getCode()), data.getErr(), response);
        }
        return t;
    }
}
