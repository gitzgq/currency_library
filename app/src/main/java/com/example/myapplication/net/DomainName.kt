package com.example.myapplication.net

/**
 * 车通云正式、测试、UAT域名管理类
 */
object DomainName {

    /** 其他通用域名 - 正式  */
    val BASE_FORMAL = "https://xcx.autocloudpro.com/"
    /** 其他通用域名 - UAT  */
    val BASE_UAT = "http://newapi.autocloudpro.com/dev/"
    /** 其他通用域名 - 测试  */
    val BASE_TEST = "http://newapi.autocloudpro.com/dev/"

    /** 用户中心域名 = 正式  */
    val USER_FORMAL = "http://usercenter.autocloudpro.com/"
    /** 用户中心域名 = UAT  */
    val USER_UAT = "http://test1-uc.autocloudpro.com/"
    /** 用户中心域名 = 测试  */
    val USER_TEST = "http://test1-uc.autocloudpro.com/"

    /** 上传VIN域名 = 正式  */
    val IMG_VIN_FORMAL = "http://file.autocloudpro.com/"
    /** 上传VIN域名 = UAT  */
    val IMG_VIN_UAT = "http://43.242.84.5:8080/"
    /** 上传VIN域名 = 测试  */
    val IMG_VIN_TEST = "http://43.242.84.5:8080/"

    /** 配件图片域名 - 通用  */
    val PART_IMG_URL = "https://api.autocloudpro.com/Public/car_logo_images/"
}