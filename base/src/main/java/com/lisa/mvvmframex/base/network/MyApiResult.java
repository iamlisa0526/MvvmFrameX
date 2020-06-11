/*
 * Copyright (C) 2017 zhouyou(478319399@qq.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.lisa.mvvmframex.base.network;

import com.zhouyou.http.model.ApiResult;

/**
 * <p>描述：自定义ApiResult，使用情景举列2</p>
 * 主要思想就是：默认标准的是code、msg、data三个字段，如果你的结构字段和默认的不一样，<br>
 * 只需要覆写不一样的就可以了，一样的字段不用覆写，添加反而会出问题。<br>
 * 作者： zhouyou<br>
 * 日期： 2017/6/27 16:57 <br>
 * 版本： v1.0<br>
 * 例如：网络请求<br>
 * http://japi.juhe.cn/joke/content/list.from?key=f5236a9fb8fc75fac0a4d9b8c27a4e90&page=1&pagesize=10&sort=asc&time=1418745237
 * 返回json：
 * {
 * error_code: 0,
 * reason: "Success",
 * result: - {
 * data: - [
 * - {
 * content: "妻：想当年我的身材和体形正面山清水秀，侧面悬崖峭壁，背面柳暗花明，是吧？夫：是呀，可惜水土保持做的太差了。",
 * hashId: "49288A771A24A65AF795FC64E3B783CE",
 * unixtime: 1418745238,
 * updatetime: "2014-12-16 23:53:58"
 * }
 * ]
 * }
 * }
 */
public class MyApiResult<T> extends ApiResult<T> {
    /**
     * 业务层返回成功code
     */
    public static final int SUCCESS_CODE = 0;

    /**
     * 业务层返回错误code
     */
    public static final int ERROR_CODE = 1;

    String err;//对应默认标准ApiResult的err

    @Override
    public String getMsg() {//因为库里使用msg字段，而你的结构是err,所以覆写getMsg()方法，setMsg()方法不用覆写
        return err;
    }

}
