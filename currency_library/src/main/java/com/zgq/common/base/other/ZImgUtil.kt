package com.zgq.common.base.other

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.util.Base64

import java.io.File
import java.io.FileOutputStream
import java.io.IOException

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
     * @param quality  压缩比例 0 - 100 (数值越小压缩的越厉害)
     * @return 压缩后的路径
     */
    @JvmOverloads
    fun compressImage(filePath: String, quality: Int = 80): String {
        return compressImage(filePath, quality, 5000000)
    }

    /**
     * 图片压缩-质量压缩
     * @param filePath 源图片路径
     * @param quality  压缩比例 0 - 100 (数值越小压缩的越厉害)
     * @param size     图片大小
     * @return 压缩后的路径
     */
    fun compressImage(filePath: String, quality: Int = 80, size: Long): String {
        if (ZStringUtil.isEmpty(filePath)) {
            return ""
        }
        val file = File(filePath)
        if (file.exists() && file.isFile) {
            val length = file.length()
            if (length <= size) {// 小于直接返回，不进行压缩
                return filePath
            }
        }
        var bm: Bitmap? = getSmallBitmap(filePath)//获取一定尺寸的图片
        val degree = getRotateAngle(filePath)//获取相片拍摄角度

        if (degree != 0) {//旋转照片角度，防止头像横着显示
            bm = setRotateAngle(degree, bm)
        }
        val outputFile = File(filePath)
        try {
            val out = FileOutputStream(outputFile)
            bm?.compress(Bitmap.CompressFormat.JPEG, quality, out)
            out?.close()
        } catch (e: Exception) {
            ZLog.e("压缩异常 = " + e.message)
            return filePath
        }

        return outputFile.path
    }

    /**
     * 获取图片的旋转角度
     *
     * @param filePath
     * @return
     */
    fun getRotateAngle(filePath: String): Int {
        if (ZStringUtil.isEmpty(filePath)) {
            return 0
        }
        var rotate_angle = 0
        try {
            val exifInterface = ExifInterface(filePath)
            val orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)
            when (orientation) {
                ExifInterface.ORIENTATION_ROTATE_90 -> rotate_angle = 90
                ExifInterface.ORIENTATION_ROTATE_180 -> rotate_angle = 180
                ExifInterface.ORIENTATION_ROTATE_270 -> rotate_angle = 270
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return rotate_angle
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

        if (bitmap != null) {
            val m = Matrix()
            m.postRotate(angle.toFloat())
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.width,
                    bitmap.height, m, true)
            return bitmap
        }
        return bitmap

    }


    /**
     * 根据路径获得图片信息并按比例压缩，返回bitmap
     */
    fun getSmallBitmap(filePath: String): Bitmap {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true//只解析图片边沿，获取宽高
        BitmapFactory.decodeFile(filePath, options)
        // 计算缩放比
        options.inSampleSize = calculateInSampleSize(options, 480, 800)
        // 完整解析图片返回bitmap
        options.inJustDecodeBounds = false
        return BitmapFactory.decodeFile(filePath, options)
    }


    private fun calculateInSampleSize(options: BitmapFactory.Options,
                                      reqWidth: Int, reqHeight: Int): Int {
        val height = options.outHeight
        val width = options.outWidth
        var inSampleSize = 1
        if (height > reqHeight || width > reqWidth) {
            val heightRatio = Math.round(height.toFloat() / reqHeight.toFloat())
            val widthRatio = Math.round(width.toFloat() / reqWidth.toFloat())
            inSampleSize = if (heightRatio < widthRatio) heightRatio else widthRatio
        }
        return inSampleSize
    }


    /**
     * base64转为bitmap
     * @param base64Str
     */
    fun base64StrToBitmap(base64Str: String): Bitmap? {
        if (ZStringUtil.isEmpty(base64Str)) {
            return null
        }
        val bytes = Base64.decode(base64Str, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
    }


}
