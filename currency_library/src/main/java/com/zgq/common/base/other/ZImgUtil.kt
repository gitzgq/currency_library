package com.zgq.common.base.other

import android.content.Context
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.provider.MediaStore
import android.util.Base64
import androidx.loader.content.CursorLoader
import java.io.*

import kotlin.math.roundToInt

/**
 * 图片压缩工具类
 */
class ZImgUtil {

    companion object {
        val instence: ZImgUtil by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { ZImgUtil() }
    }

    // 默认压缩比例
    private val QUALITY: Int = 90

    private val MB_20: Long = 20971520
    private val MB_50: Long = 52428800

    private val C_W = 1080
    private val C_H = 1920


    /**
     * 图片压缩-质量压缩
     * @param filePath 源图片路径
     * @return 压缩后的路径
     */
    fun compressImage(filePath: String): String {
        return compressImage(filePath, QUALITY)
    }

    /**
     * 图片压缩-质量压缩
     * @param filePath 源图片路径
     * @param quality  压缩比例 0 - 100 (数值越小压缩的越厉害)
     * @return 压缩后的路径
     */
    fun compressImage(filePath: String, quality: Int): String {
        return compressImage(filePath, quality, 0)
    }

    /**
     * 图片压缩-质量压缩
     * @param filePath 源图片路径
     * @param quality  压缩比例 0 - 100 (数值越小压缩的越厉害)
     * @param size     超过这个长度才压缩
     * @return 压缩后的路径
     */
    fun compressImage(filePath: String, quality: Int, size: Long): String {
        return compressImage(filePath, filePath, quality, size, C_W, C_H)
    }

    fun compressImage(filePath: String, quality: Int, size: Long, w: Int, h: Int): String {
        return compressImage(filePath, filePath, quality, size, w, h)
    }

    /**
     * 图片压缩-质量压缩
     * @param filePath 源图片路径
     * @param newFilePath 压缩之后的图片路径
     * @return 压缩后的路径
     */
    fun compressImage(filePath: String, newFilePath: String): String {
        return compressImage(filePath, newFilePath, 0)
    }

    /**
     * 图片压缩-质量压缩
     * @param filePath 源图片路径
     * @param newFilePath 压缩之后的图片路径
     * @param size        超过这个长度才压缩
     * @return 压缩后的路径
     */
    fun compressImage(filePath: String, newFilePath: String, size: Long): String {
        return compressImage(filePath, newFilePath, QUALITY, size, C_W, C_H)
    }

    fun compressImage(filePath: String, newFilePath: String, size: Long, w: Int, h: Int): String {
        return compressImage(filePath, newFilePath, QUALITY, size, w, h)
    }

    /**
     * 图片压缩-质量压缩
     * @param filePath 源图片路径
     * @param newFilePath 压缩之后的图片路径
     * @param quality  压缩比例 0 - 100 (数值越小压缩的越厉害)
     * @param size     超过这个长度才压缩(bytes)
     * @return 压缩后的路径
     */
    fun compressImage(filePath: String, newFilePath: String, quality: Int, size: Long): String {
        return compressImage(filePath, newFilePath, quality, size, C_W, C_H)
    }

    fun compressImage(filePath: String, newFilePath: String, quality: Int, size: Long, w: Int, h: Int): String {
        // 压缩前的图片完整路径为空时，返回空字符串
        if (ZStringUtil.isEmpty(filePath)) {
            return ""
        }
        // 压缩后的图片目录为空时，返回压缩前的图片完整路径
        if (ZStringUtil.isEmpty(newFilePath)) {
            return filePath
        }
        // 压缩前的图片文件
        val oldFile = File(filePath)
        // 压缩前的图片大小
        val length = oldFile.length()
        ZLog.e("文件长度 = ${length / 1024}")
        ZLog.e("压缩前-图片完整路径 = $filePath")
        ZLog.e("压缩后-图片目录 = $newFilePath")
        // 图片大小 <= 传过来的固定大小，不执行压缩，直接返回压缩前的图片完整路径
        if (length <= size) {
            return filePath
        }
        // 压缩比例
        var qualityB = quality
        // 大于20M，小于50M，默认设置压缩比例 >=90，则为90
        if (length in (MB_20 + 1) until MB_50) {
            qualityB = if (qualityB >= 90) 90 else qualityB
        } else if (length > MB_50) {
            // 大于50M，默认设置压缩比例 >=80，则为80
            qualityB = if (qualityB >= 80) 80 else qualityB
        }
        ZLog.e("压缩的比例 = $qualityB")
        var bm: Bitmap? = getSmallBitmap(filePath, w, h)//获取一定尺寸的图片
        val degree = getRotateAngle(filePath)//获取相片拍摄角度
        bm?.let { it1 ->
            //旋转照片角度，防止头像横着显示
            if (degree != 0) {
                bm = setRotateAngle(degree, it1)
            }
            // 创建压缩后的图片完整路径
            var newFile = File(newFilePath, oldFile.name)
            try {
                bm?.let { it2 ->
                    val out = FileOutputStream(newFile)
                    it2.compress(Bitmap.CompressFormat.JPEG, qualityB, out)
                    out.close()
                    ZLog.e("压缩后-图片完整路径 = ${newFile.path}")
                }
            } catch (e: Exception) {
                ZLog.e("压缩异常 = " + e.message)
                return filePath
            }
            return newFile.path
        }
        return ""
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
        try {
            val exifInterface = ExifInterface(filePath)
            when (exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)) {
                ExifInterface.ORIENTATION_ROTATE_90 -> return 90
                ExifInterface.ORIENTATION_ROTATE_180 -> return 180
                ExifInterface.ORIENTATION_ROTATE_270 -> return 270
            }
        } catch (e: IOException) {
            e.printStackTrace()
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
    private fun setRotateAngle(angle: Int, bitmap: Bitmap): Bitmap {
        val m = Matrix()
        m.postRotate(angle.toFloat())
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, m, true)

    }


    /**
     * 根据路径获得图片信息并按比例压缩，返回bitmap
     */
    fun getSmallBitmap(filePath: String): Bitmap? {
        return getSmallBitmap(filePath, C_W, C_H)
    }

    fun getSmallBitmap(filePath: String, w: Int, h: Int): Bitmap? {
        ZLog.e("压缩尺寸大小 = $w + $h")
        if (ZStringUtil.isEmpty(filePath)) {
            return null
        }
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true//只解析图片边沿，获取宽高
        BitmapFactory.decodeFile(filePath, options)
        // 计算缩放比
        options.inSampleSize = calculateInSampleSize(options, w, h)
        // 完整解析图片返回bitmap
        options.inJustDecodeBounds = false
        return BitmapFactory.decodeFile(filePath, options)
    }


    private fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
        var inSampleSize = 1
        val height = options.outHeight
        val width = options.outWidth

        if (height > reqHeight || width > reqWidth) {
            val heightRatio = (height.toFloat() / reqHeight.toFloat()).roundToInt()
            val widthRatio = (width.toFloat() / reqWidth.toFloat()).roundToInt()
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
        return try {
            val bytes = Base64.decode(base64Str, Base64.DEFAULT)
            BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
        } catch (e: Exception) {
            null
        }
    }

    /**
     * 图片路径 转 Base64
     * path 图片路径
     * return base64字符串
     */
    fun imgToBase64(path: String): String {
        if (ZStringUtil.isEmpty(path)) {
            return ""
        }
        ZLog.e("图片路径 = $path")
        try {
            return imgToBase64(FileInputStream(path))
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ""
    }

    /**
     * 图片IO 转 Base64
     * inputStream IO
     * return base64字符串
     */
    fun imgToBase64(inputStream: InputStream): String {
        var result = ""
        try {
            //创建一个字符流大小的数组。
            val data = ByteArray(inputStream.available())
            //写入数组
            inputStream.read(data)
            //用默认的编码格式进行编码
            result = Base64.encodeToString(data, Base64.DEFAULT)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                inputStream.close()
                ZLog.e("关闭转换流")
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return result
    }

    /**
     * 获取文件名称
     * path 文件的绝对路径(例：xxx/xxx/xxx.jpg)
     * */
    fun fileName(path: String): String {
        if (!ZStringUtil.isEmpty(path)) {
            return File(path).name
        }
        return "${System.currentTimeMillis()}.jpg"
    }

    /**
     * Uri先把文件复制到指定文件夹，然后返回图片的绝对路径
     * uri 图片uri
     * filePath 指定文件夹的文件的路径（例：xxx/xxx/xx.jpg）
     */
    fun uriCopyToPath(context: Context, uri: Uri, filePath: String): String {
        return copyFileToNewPath(context, uri, FileOutputStream(filePath), filePath)
    }

    /**
     * Uri先把文件复制到指定文件夹，然后返回图片的绝对路径
     * uri 图片uri
     * newFile 指定文件夹的文件
     */
    fun uriCopyToPath(context: Context, uri: Uri, newFile: File): String {
        return copyFileToNewPath(context, uri, FileOutputStream(newFile), newFile.path)
    }

    /**
     * Uri先把文件复制到指定文件夹，然后返回图片的绝对路径
     * uri 原图图片uri
     * fos IO
     * newPath 新图片的路径（例：xxx/xxx/xx.jpg）
     */
    private fun copyFileToNewPath(context: Context, uri: Uri, fos: FileOutputStream, newPath: String): String {
        if (ZStringUtil.isEmpty(newPath)) {
            return ""
        }
        try {
            // uri转成IO
            val inputStream = context.contentResolver.openInputStream(uri)
            inputStream?.let { it1 ->
                // 读取
                val bis = BufferedInputStream(it1)
                val buffer = ByteArray(1024)
                var len: Int
                while (((bis.read(buffer)).also { len = it }) != -1) {
                    // 写入
                    fos.write(buffer, 0, len)
                }
                fos.close()
                bis.close()
                it1.close()
            }

            return newPath
        } catch (e: Exception) {
            return ""
        }
    }
}
