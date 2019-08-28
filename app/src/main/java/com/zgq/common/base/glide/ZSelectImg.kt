package com.zgq.common.base.glide

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import androidx.loader.content.CursorLoader
import com.qingmei2.rximagepicker.core.RxImagePicker
import com.qingmei2.rximagepicker.ui.BasicImagePicker
import com.zgq.common.base.other.ZLog
import com.zgq.common.base.other.ZStringUtil

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
        if(null == context || null == callBack){
            return
        }
        if(null == imgPicker){
            imgPicker = RxImagePicker.create()
        }
        imgPicker?.openCamera(context)?.subscribe{result -> uri(context, result?.uri?: null, callBack)}
    }

    /**
     * 打开相册
     */
    fun openAlbum(context: Context?, callBack : OnSelectImgCallBack){
        if(null == context || null == callBack){
            return
        }
        if(null == imgPicker){
            imgPicker = RxImagePicker.create()
        }
        imgPicker?.openGallery(context)?.subscribe{result -> uri(context, result?.uri?: null, callBack)}
    }

    /**
     * 获取到的图片Uri
     */
    private fun uri(context: Context?, uri : Uri?, callBack : OnSelectImgCallBack){
        if(null == context || null == uri || null == callBack){
            return
        }
        val path : String = uriToPath(context, uri)
        if(ZStringUtil.isEmpty(path)){
            return
        }
        ZLog.e("获取的路径 = $path")
        callBack?.onFinish(path)
    }

    /**
     * Uri转path
     * @param context
     * @param uri
     * @return
     */
    private fun uriToPath(context: Context?, uri: Uri?): String {
        if (null == context || null == uri) {
            return ""
        }
        //由打印的contentUri可以看到：2种结构。正常的是：content://那么这种就要去数据库读取path。
        //另外一种是Uri是 file:///那么这种是 Uri.fromFile(File file);得到的
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        var path: String?
        var cursor: Cursor? = null
        try {
            val loader = CursorLoader(context, uri, projection, null, null, null)
            cursor = loader.loadInBackground()

            val column_index : Int = cursor?.getColumnIndex(projection[0])?: 0
            cursor?.moveToFirst()
            path = cursor?.getString(column_index)
            //如果是正常的查询到数据库。然后返回结构
            return path?: ""
        } catch (e: Exception) {
            ZLog.e("异常 = " + e.message)
        } finally {
            cursor?.close()
        }

        //如果是文件。Uri.fromFile(File file)生成的uri。那么下面这个方法可以得到结果
        path = uri.path
        return path?: ""
    }

    /**
     * 选择图片回调接口
     */
    interface OnSelectImgCallBack{
        fun onFinish(path : String)
    }

}