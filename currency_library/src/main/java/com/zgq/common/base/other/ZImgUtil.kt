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
    fun compressImage(filePath: String?): String {
        return compressImage(filePath, QUALITY)
    }

    /**
     * 图片压缩-质量压缩
     * @param filePath 源图片路径
     * @param quality  压缩比例 0 - 100 (数值越小压缩的越厉害)
     * @return 压缩后的路径
     */
    fun compressImage(filePath: String?, quality: Int): String {
        return compressImage(filePath, quality, 0)
    }

    /**
     * 图片压缩-质量压缩
     * @param filePath 源图片路径
     * @param quality  压缩比例 0 - 100 (数值越小压缩的越厉害)
     * @param size     超过这个长度才压缩
     * @return 压缩后的路径
     */
    fun compressImage(filePath: String?, quality: Int, size: Long): String {
        return compressImage(filePath, filePath, quality, size, C_W, C_H)
    }

    fun compressImage(filePath: String?, quality: Int, size: Long, w: Int, h: Int): String {
        return compressImage(filePath, filePath, quality, size, w, h)
    }

    /**
     * 图片压缩-质量压缩
     * @param filePath 源图片路径
     * @param newFilePath 压缩之后的图片路径
     * @return 压缩后的路径
     */
    fun compressImage(filePath: String?, newFilePath: String?): String {
        return compressImage(filePath, newFilePath, 0)
    }

    /**
     * 图片压缩-质量压缩
     * @param filePath 源图片路径
     * @param newFilePath 压缩之后的图片路径
     * @param size        超过这个长度才压缩
     * @return 压缩后的路径
     */
    fun compressImage(filePath: String?, newFilePath: String?, size: Long): String {
        return compressImage(filePath, newFilePath, QUALITY, size, C_W, C_H)
    }

    fun compressImage(filePath: String?, newFilePath: String?, size: Long, w: Int, h: Int): String {
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
    fun compressImage(filePath: String?, newFilePath: String?, quality: Int, size: Long): String {
        return compressImage(filePath, newFilePath, quality, size, C_W, C_H)
    }

    fun compressImage(filePath: String?, newFilePath: String?, quality: Int, size: Long, w: Int, h: Int): String {
        filePath?.let {
            val oldFile = File(it)
            val length = oldFile.length()
            ZLog.e("文件长度 = $length")
            ZLog.e("文件路径 = $it")
            ZLog.e("文件新路径 = ${newFilePath ?: "没有新的路径"}")
            if (length <= size) {// 文件长度 <= 传过来的固定长度，不执行压缩
                return filePath
            }
            var qualityB = quality
            if (length in (MB_20 + 1) until MB_50) {// 大于20M，小于50M
                qualityB = if (qualityB > 90) 90 else qualityB
            } else if (length > MB_50) {
                qualityB = 80
            }
            ZLog.e("压缩的比例 = $qualityB")
            if (!ZStringUtil.isEmpty(it)) {
                var bm: Bitmap? = getSmallBitmap(it, w, h)//获取一定尺寸的图片
                val degree = getRotateAngle(it)//获取相片拍摄角度
                bm?.let { it1 ->
                    if (degree != 0) {//旋转照片角度，防止头像横着显示
                        bm = setRotateAngle(degree, it1)
                    }
                    var newFile: File? = if (ZStringUtil.isEmpty(newFilePath)) {
                        oldFile
                    } else {
                        File(newFilePath, oldFile.name)
                    }
                    try {
                        val out = FileOutputStream(newFile)
                        bm?.compress(Bitmap.CompressFormat.JPEG, qualityB, out)
                        out?.close()
                    } catch (e: Exception) {
                        ZLog.e("压缩异常 = " + e.message)
                        return it
                    }
                    return newFile?.path ?: it
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
        return getSmallBitmap(filePath, C_W, C_H)
    }

    fun getSmallBitmap(filePath: String?, w: Int, h: Int): Bitmap? {
        ZLog.e("压缩尺寸大小 = $w + $h")
        filePath?.let {
            val options = BitmapFactory.Options()
            options.inJustDecodeBounds = true//只解析图片边沿，获取宽高
            BitmapFactory.decodeFile(it, options)
            // 计算缩放比
            options.inSampleSize = calculateInSampleSize(options, w, h)
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
            } catch (e: Exception) {
                null
            }
        }
        return null

    }

    /**
     * 图片转Base64
     * path 图片路径
     */
    fun imgToBase64(path: String?): String {
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
     * 图片转Base64
     * inputStream IO
     */
    fun imgToBase64(inputStream: InputStream): String {
        var result = ""
        try {
            inputStream?.let { inputStream ->
                //创建一个字符流大小的数组。
                val data = ByteArray(inputStream.available())
                //写入数组
                inputStream.read(data)
                //用默认的编码格式进行编码
                result = Base64.encodeToString(data, Base64.NO_CLOSE)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            inputStream?.let {
                try {
                    it.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
        ZLog.e("转换base64之后 = $result")
        return result
    }

    /**
     * Uri转path
     * @param context
     * @param uri
     * @return
     */
    fun uriToPath(context: Context?, uri: Uri?): String {
        return uriToPath(context, uri, 0, 0)
    }

    /**
     *
     * Uri转path
     * @param context
     * @param uri
     * @param quality 压缩比例 0 - 100 (数值越小压缩的越厉害)
     * @param size    超过这个长度才压缩
     * @return
     */
    fun uriToPath(context: Context?, uri: Uri?, quality: Int, size: Long): String {
        context?.let { context ->
            uri?.let { uri ->
                //由打印的contentUri可以看到：2种结构。正常的是：content://那么这种就要去数据库读取path。
                //另外一种是Uri是 file:///那么这种是 Uri.fromFile(File file);得到的
                val projection = arrayOf(MediaStore.Images.Media.DATA)
                var cursor: Cursor? = null
                try {
                    val loader = CursorLoader(context, uri, projection, null, null, null)
                    cursor = loader.loadInBackground()

                    val column_index: Int = cursor?.getColumnIndex(projection[0]) ?: 0
                    cursor?.moveToFirst()
                    //如果是正常的查询到数据库。然后返回结构
                    val path = cursor?.getString(column_index) ?: ""
                    if (size <= 0) {
                        return path
                    }
                    return compressImage(path, context.getExternalFilesDir("COMPRESS_IMG")?.path, quality, size, C_W, C_H)
                } catch (e: Exception) {
                    ZLog.e("异常 = " + e.message)
                } finally {
                    cursor?.close()
                }
                //如果是文件。Uri.fromFile(File file)生成的uri。那么下面这个方法可以得到结果
                val path = uri.path ?: ""
                if (size <= 0) {
                    return uri.path ?: ""
                }
                return compressImage(path, context.getExternalFilesDir("COMPRESS_IMG")?.path, quality, size, C_W, C_H)
            }
            return ""
        }
        return ""
    }

    /**
     * 获取文件名称
     * path 文件的绝对路径(例：xxx/xxx/xxx.jpg)
     * */
    fun fileName(path: String): String{
        if(!ZStringUtil.isEmpty(path)){
            return File(path).name
        }
        return "${System.currentTimeMillis()}.jpg"
    }

    /**
     * Uri先把文件复制到指定文件夹，然后返回图片的绝对路径
     * uri 图片uri
     * filePath 指定文件夹的文件的路径（例：xxx/xxx/xx.jpg）
     */
    fun uriCopyToPath(context: Context, uri: Uri?, filePath: String): String{
        if(ZStringUtil.isEmpty(filePath)){
            return ""
        }
        return uriCopyToPath(context, uri, File(filePath))
    }

    /**
     * Uri先把文件复制到指定文件夹，然后返回图片的绝对路径
     * uri 图片uri
     * newFile 指定文件夹的文件
     */
    fun uriCopyToPath(context: Context, uri: Uri?, newFile: File): String{
        context?.let {context ->
            uri?.let { uri ->
                newFile?.let {newFile ->
                    ZLog.e("文件路径 = ${newFile.path}")
                    try {
                        // 新的文件
                        val fos = FileOutputStream(newFile)
                        // uri转成IO
                        val inputStream = context.contentResolver.openInputStream(uri)
                        // 读取
                        val bis = BufferedInputStream(inputStream)
                        val buffer = ByteArray(1024)
                        var len: Int
                        while (((bis.read(buffer)).also { len = it }) != -1) {
                            // 写入
                            fos.write(buffer, 0, len)
                        }
                        fos?.close()
                        bis?.close()
                        inputStream?.close()
                        return newFile.path?: ""
                    }catch (e: Exception){
                        return ""
                    }
                }
            }
        }
        return ""
    }


}
