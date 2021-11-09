package com.wan.system.router

import android.content.Context
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.facade.service.SerializationService
import com.alibaba.fastjson.JSON
import java.lang.reflect.Type

/**
 * Description:
 * @author lilu0916 on 2021/11/9
 *  No one knows this better than me
 */
@Route(path = "/aa/bb")
class JsonServiceImpl : SerializationService {
    override fun init(context: Context?) {
    }

    override fun <T : Any?> json2Object(input: String?, clazz: Class<T>?): T {
        return JSON.parseObject(input,clazz)
    }

    override fun object2Json(instance: Any?): String {
        return JSON.toJSONString(instance)
    }

    override fun <T : Any?> parseObject(input: String?, clazz: Type?): T {
        return JSON.parseObject(input,clazz)
    }
}