package com.zgq.common.base.other

import java.math.BigDecimal
import java.util.regex.Pattern

/**
 * 字符串 管理类
 */
object ZStringUtil {

    const val PRICE = "¥"


    /**
     * 字符串是否为空 true：是  false：否
     */
    fun isEmpty(content: String): Boolean {
        if (content.isEmpty() || content.contentEquals("null")) {
            return true
        }
        return false
    }

    /**
     * 是否是图片地址 true：是  false：否
     */
    fun isImgUrl(url: String): Boolean {
        if (isEmpty(url) || (!url.contains("http://") && !url.contains("https://"))) {
            return false
        }
        if (!url.contains(".jpg") && !url.contains(".png") && !url.contains(".git") && !url.contains(".jpeg")
                && !url.contains(".bmp") && !url.contains(".webp") && !url.contains(".JPG") && !url.contains(".PNG")
                && !url.contains(".GIF") && !url.contains(".JPEG") && !url.contains(".BMP") && !url.contains(".WEBP")) {
            return false
        }
        return true
    }

    /**
     * 价格保留小数位
     * @param price
     * @return
     */
    fun price(price: Double): String {
        return price(price, true)
    }

    /**
     * 价格保留小数位
     * @param price
     * @param rmb   true ： 返回人民币符号  false：不返回
     * @return
     */
    fun price(price: Double, rmb: Boolean): String {
        val bg = BigDecimal(price)
        // 仅保留有效位
        return (if (rmb) PRICE else "") + bg.setScale(2, BigDecimal.ROUND_HALF_UP).toString()
    }

    /**
     * 判断是否是手机号
     * @param phone
     * @return
     */
    fun isPhoneNumber(phone: String): Boolean {
        if (isEmpty(phone)) {
            return false
        }
        return Pattern.compile("^((0?(1[3-9][0-9]))\\d{8})$").matcher(phone).matches()
    }


}