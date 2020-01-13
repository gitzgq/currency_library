package com.example.myapplication.bean

import com.example.myapplication.template.TemplateUtil

/**
 * 商品列表item
 */
class Template102Bean {

    val templateId: Int = TemplateUtil.T_102

    // 商品id
    var commodityId: String = ""
    // 商品标题
    var title: String = ""
    // 商品图片
    var mainPicUrl: String = ""
    // 商品价格
    var salePrice: String = ""
}