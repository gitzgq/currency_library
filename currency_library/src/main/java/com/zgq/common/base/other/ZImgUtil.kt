package com.zgq.common.base.other

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.util.Base64

import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import kotlin.math.roundToInt

/**
 * 图片压缩工具类
 */
class ZImgUtil {

    companion object{
        val instence : ZImgUtil by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { ZImgUtil() }
    }

    /**
     * 图片压缩-质量压缩
     * @param filePath 源图片路径
     * @return 压缩后的路径
     */
    fun compressImage(filePath: String?): String {
        return compressImage(filePath, 80)
    }

    /**
     * 图片压缩-质量压缩
     * @param filePath 源图片路径
     * @param quality  压缩比例 0 - 100 (数值越小压缩的越厉害)
     * @return 压缩后的路径
     */
    fun compressImage(filePath: String?, quality: Int = 80): String {
        return compressImage(filePath, filePath, quality)
    }

    /**
     * 图片压缩-质量压缩
     * @param filePath 源图片路径
     * @param newFilePath 压缩之后的图片路径
     * @return 压缩后的路径
     */
    fun compressImage(filePath: String?, newFilePath: String?): String {
        return compressImage(filePath, newFilePath, 80)
    }

    /**
     * 图片压缩-质量压缩
     * @param filePath 源图片路径
     * @param newFilePath 压缩之后的图片路径
     * @param quality  压缩比例 0 - 100 (数值越小压缩的越厉害)
     * @return 压缩后的路径
     */
    fun compressImage(filePath: String?, newFilePath: String?, quality: Int = 80): String {
        filePath?.let {
            if(!ZStringUtil.isEmpty(it)){
                var bm: Bitmap? = getSmallBitmap(it)//获取一定尺寸的图片
                val degree = getRotateAngle(it)//获取相片拍摄角度
                bm?.let { it1 ->
                    if (degree != 0) {//旋转照片角度，防止头像横着显示
                        bm = setRotateAngle(degree, it1)
                    }
                    val oldFile = File(it)
                    var newFile: File?
                    newFile = if(ZStringUtil.isEmpty(newFilePath)){
                        oldFile
                    }else{
                        File(newFilePath, oldFile.name)
                    }
                    try {
                        val out = FileOutputStream(newFile)
                        bm?.compress(Bitmap.CompressFormat.JPEG, quality, out)
                        out?.close()
                    } catch (e: Exception) {
                        ZLog.e("压缩异常 = " + e.message)
                        return it
                    }
                    return newFile?.path?: it
                }
            }
        }
        return ""
    }

    /**
     * 获取图片的旋转角度
     *
     * @param filePath
     * @return
     */
    fun getRotateAngle(filePath: String?): Int {
        filePath?.let {
            try {
                val exifInterface = ExifInterface(it)
                when (exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)) {
                    ExifInterface.ORIENTATION_ROTATE_90 -> return 90
                    ExifInterface.ORIENTATION_ROTATE_180 -> return 180
                    ExifInterface.ORIENTATION_ROTATE_270 -> return 270
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return 0
    }

    /**
     * 旋转图片角度
     *
     * @param angle
     * @param bitmap
     * @return
     */
    private fun setRotateAngle(angle: Int, bitmap: Bitmap?): Bitmap? {
        var bitmap = bitmap
        bitmap?.let {
            val m = Matrix()
            m.postRotate(angle.toFloat())
            bitmap = Bitmap.createBitmap(it, 0, 0, it.width,
                    it.height, m, true)
        }
        return bitmap

    }


    /**
     * 根据路径获得图片信息并按比例压缩，返回bitmap
     */
    fun getSmallBitmap(filePath: String?): Bitmap? {
        filePath?.let {
            val options = BitmapFactory.Options()
            options.inJustDecodeBounds = true//只解析图片边沿，获取宽高
            BitmapFactory.decodeFile(it, options)
            // 计算缩放比
            options.inSampleSize = calculateInSampleSize(options, 480, 800)
            // 完整解析图片返回bitmap
            options.inJustDecodeBounds = false
            return BitmapFactory.decodeFile(it, options)
        }
        return null

    }


    private fun calculateInSampleSize(options: BitmapFactory.Options?,
                                      reqWidth: Int, reqHeight: Int): Int {
        var inSampleSize = 1
        options?.let {
            val height = it.outHeight
            val width = it.outWidth

            if (height > reqHeight || width > reqWidth) {
                val heightRatio = (height.toFloat() / reqHeight.toFloat()).roundToInt()
                val widthRatio = (width.toFloat() / reqWidth.toFloat()).roundToInt()
                inSampleSize = if (heightRatio < widthRatio) heightRatio else widthRatio
            }
        }
        return inSampleSize
    }


    /**
     * base64转为bitmap
     * @param base64Str
     */
    fun base64StrToBitmap(base64Str: String?): Bitmap? {
        base64Str?.let {
            return try {
                val bytes = Base64.decode(it, Base64.DEFAULT)
                BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
            }catch (e: Exception){
                null
            }
        }
        return null

    }


}
