package com.zgq.common.base.glide

import android.content.Context
import android.net.Uri
import com.qingmei2.rximagepicker.core.RxImagePicker
import com.qingmei2.rximagepicker.ui.BasicImagePicker
import com.zgq.common.base.other.ZImgUtil
import com.zgq.common.base.other.ZLog

/**
 * 调用系统的相机、相册工具类
 */
class ZSelectImg {

    companion object {
        val instence: ZSelectImg by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            ZSelectImg()
        }
    }


    lateinit var imgPicker: BasicImagePicker

    /**
     * 打开相机
     */
    fun openCamera(context: Context, callBack: OnSelectImgCallBack) {
        open(context, callBack, true)
    }

    /**
     * 打开相册
     */
    fun openAlbum(context: Context, callBack: OnSelectImgCallBack) {
        open(context, callBack, false)
    }

    /** 调用库的对应方法 */
    private fun open(context: Context, callBack: OnSelectImgCallBack, isCamera: Boolean) {
        imgPicker = RxImagePicker.create()
        when (isCamera) {
            true -> {// 打开相册
                imgPicker.openCamera(context).subscribe { result -> uri(result.uri, callBack) }
            }
            false -> {// 打开相册
                imgPicker.openGallery(context).subscribe { result -> uri(result.uri, callBack) }
            }
        }
    }

    /**
     * 获取到的图片Uri
     */
    private fun uri(uri: Uri, callBack: OnSelectImgCallBack) {
        callBack.onFinish(uri)
    }

    /**
     * 选择图片回调接口
     */
    interface OnSelectImgCallBack {
        fun onFinish(uri: Uri)
    }

}