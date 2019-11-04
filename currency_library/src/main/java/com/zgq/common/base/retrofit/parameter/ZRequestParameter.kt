package com.zgq.common.base.retrofit.parameter

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

/**
 * 接口参数管理类
 */
class ZRequestParameter{

    companion object{
        val instence : ZRequestParameter by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            ZRequestParameter()
        }
    }

    val typeJson = "application/json; charset=utf-8"
    val typeMultipart = "multipart/form-data"

    fun body(content: String): RequestBody {
        return body(content, typeJson)
    }

    fun bodyM(content: String): RequestBody {
        return body(content, typeMultipart)
    }

    /**
     * string类型的参数
     *
     * @param content
     * @return
     */
    fun body(content: String, type: String): RequestBody {
        return RequestBody.create(type.toMediaTypeOrNull(), content)
    }

    /**
     * 上传文件
     *
     * @param key
     * @param file
     * @return
     */
    fun body(key: String, file: File): MultipartBody.Part {
        return MultipartBody.Part.createFormData(key, file.name, bodyFile(file))
    }

    fun bodyFile(file: File): RequestBody {
        return RequestBody.create(typeMultipart.toMediaTypeOrNull(), file)
    }

}