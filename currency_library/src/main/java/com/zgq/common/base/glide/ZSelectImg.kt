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

    companion object{
        val instence : ZSelectImg by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            ZSelectImg()
        }
    }


    private var imgPicker : BasicImagePicker? = null

    /**
     * 打开相机
     */
    fun openCamera(context: Context?, callBack : OnSelectImgCallBack){
        open(context, callBack, true)
    }

    /**
     * 打开相册
     */
    fun openAlbum(context: Context?, callBack : OnSelectImgCallBack){
        open(context, callBack, false)
    }

    /** 调用库的对应方法 */
    private fun open(context: Context?, callBack : OnSelectImgCallBack, isCamera: Boolean){
        context?.let { context ->
            callBack?.let { callBack ->
                if(null == imgPicker){
                    imgPicker = RxImagePicker.create()
                }
                when(isCamera){
                    true -> {// 打开相册
                        imgPicker?.openCamera(context)?.subscribe{result -> uri(context, result?.uri?: null, callBack)}
                    }
                    false -> {// 打开相册
                        imgPicker?.openGallery(context)?.subscribe{result -> uri(context, result?.uri?: null, callBack)}
                    }
                }

            }
        }
    }

    /**
     * 获取到的图片Uri
     */
    private fun uri(context: Context?, uri : Uri?, callBack : OnSelectImgCallBack){
        context?.let { context ->
            uri?.let { uri ->
                callBack?.let { callBack ->
                    val path : String = ZImgUtil.instence.uriToPath(context, uri)
                    ZLog.e("获取的路径 = $path")
                    callBack?.onFinish(path, uri)
                }
            }
        }

    }

    /**
     * 选择图片回调接口
     */
    interface OnSelectImgCallBack{
        fun onFinish(path : String, uri: Uri)
    }

}