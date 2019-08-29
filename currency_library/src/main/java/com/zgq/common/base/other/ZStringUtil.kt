package com.zgq.common.base.other

/**
 * 字符串 管理类
 */
object ZStringUtil {

    /**
     * 字符串是否为空 true：是  false：否
     */
    fun isEmpty(content: String): Boolean {
        if (content?.length <= 0 || content.contentEquals("null")) {
            return true
        }
        return false
    }

    /**
     * 是否是图片地址 true：是  false：否
     */
    fun isImgUrl(url: String): Boolean {
        if (url?.length <= 0 || (!url.contains("http://") && !url.contains("https://"))) {
            return false
        }
        if (!url.contains(".jpg") && !url.contains(".png") && !url.contains(".JPG") && !url.contains(".PNG")) {
            return false
        }
        return true
    }


}