package com.example.myapplication.bean

/**
 * json数据公共字段
 */
class ZBaseBean<T>{
    var code: Int = -1
    var message: String? = null
    var data: T? = null
    var list: T? = null
    var model: T? = null
    var imageUri: T? = null
    // 自定义空数据字段
    var customEmptyData: T? = null
}
