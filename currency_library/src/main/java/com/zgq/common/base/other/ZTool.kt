package com.zgq.common.base.other

import android.R.attr
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Uri
import android.net.wifi.WifiManager
import android.os.Build
import android.util.Base64
import android.view.View
import android.view.inputmethod.InputMethodManager
import java.io.FileInputStream
import java.io.IOException
import java.io.InputStream
import java.net.Inet4Address
import java.net.NetworkInterface
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import kotlin.experimental.and


object ZTool {

    /**
     * 强制显示键盘（配合定时器 Timer使用）
     */
    fun showKeyboard(view: View?) {
        view?.let {view ->
            view.context?.let {context ->
                val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm?.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS)
            }

        }

    }

    /**
     * 隐藏键盘
     */
    fun hideKeyboard(view: View?) {
        view?.let {view ->
            view.context?.let { context ->
                val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm?.hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
            }
        }
    }

    /**
     * 获取当前手机ip
     *
     * @param context
     * @return
     */
    fun ip(context: Context?): String {
        if (null == context) {
            return ""
        }
        val info = (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo
        if (null != info && info.isConnected) {
            if (info.type == ConnectivityManager.TYPE_MOBILE) {
                try {
                    val en = NetworkInterface.getNetworkInterfaces()
                    while (en.hasMoreElements()) {
                        val intf = en.nextElement()
                        val enumIpAddr = intf.inetAddresses
                        while (enumIpAddr.hasMoreElements()) {
                            val inetAddress = enumIpAddr.nextElement()
                            if (!inetAddress.isLoopbackAddress && inetAddress is Inet4Address) {
                                return inetAddress.getHostAddress()
                            }
                        }
                    }
                } catch (e: Exception) {

                }

            } else if (info.type == ConnectivityManager.TYPE_WIFI) {//当前使用无线网络
                val wifiManager = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
                val wifiInfo = wifiManager.connectionInfo
                return intIPToStringIP(wifiInfo.ipAddress)
            }
        }
        return ""
    }

    /**
     * 将得到的int类型的IP转换为String类型
     */
    private fun intIPToStringIP(ip: Int): String {
        return (ip and 0xFF).toString() + "." +
                (ip shr 8 and 0xFF) + "." +
                (ip shr 16 and 0xFF) + "." +
                (ip shr 24 and 0xFF)
    }

    /**
     * 复制内容到剪切板
     * @param context
     * @param content
     */
    fun clipContent(context: Context?, content: String) {
        context?.let {context ->
            content?.let {content ->
                val cm = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                // 将文本内容放到系统剪贴板里。
                cm?.text = content
                ZToast.toast(context, "复制成功")
            }
        }
    }

    /**
     * 拨打电话（跳转到拨号界面，用户手动点击拨打）
     * @param phone 号码
     */
    fun callPhone(context: Context?, phone: String) {
        if (null == context || ZStringUtil.isEmpty(phone)) {
            ZToast.toast(context, "客服号码不正确")
            return
        }
        val intent = Intent(Intent.ACTION_DIAL)
        val data = Uri.parse("tel:$phone")
        intent.data = data
        context.startActivity(intent)
    }

    /**
     * 手机设备的唯一标识
     * 通过硬件信息拼接 + MD5 = 唯一标识
     * @return
     */
    fun deviceId(): String {
        ZLog.e("主板 = " + Build.BOARD)
        ZLog.e("系统定制商 = " + Build.BRAND)
        ZLog.e("硬件识别码 = " + Build.FINGERPRINT)
        ZLog.e("硬件名称 = " + Build.HARDWARE)
        ZLog.e("硬件制造商 = " + Build.MANUFACTURER)
        ZLog.e("硬件序列号 = " + Build.SERIAL)
        ZLog.e("手机制造商 = " + Build.PRODUCT)
        ZLog.e("TIME = " + Build.TIME)
        ZLog.e("版本 = " + Build.MODEL)
        val deviceId = Build.BOARD + Build.BRAND + Build.FINGERPRINT + Build.HARDWARE + Build.MANUFACTURER + Build.SERIAL + Build.PRODUCT + Build.TIME + Build.MODEL
        ZLog.e("加密前deviceId = $deviceId")
        ZLog.e("加密后deviceId = " + md5(deviceId))
        return md5(deviceId)
    }

    /**
     * MD5加密  32位 大写
     * @param content
     * @return
     */
    fun md5(content: String?): String {
        content?.let {content ->
            try {
                val md5 = MessageDigest.getInstance("MD5")
                val bytes = md5.digest(content.toByteArray())
                var result = ""
                for (b in bytes) {
                    var temp = Integer.toHexString((b and 0xff.toByte()).toInt())
                    if (temp.length == 1) {
                        temp = "0$temp"
                    }
                    result += temp
                }
                return result.toUpperCase()
            } catch (e: NoSuchAlgorithmException) {
                e.printStackTrace()
            }
            return content
        }
        return ""
        
    }

    /**
     * 图片转Base64
     */
    fun imgToBase64(path: String?): String{
        path?.let {path ->
            ZLog.e("图片路径 = $path" )
            var inputStream: InputStream? = null
            var result = ""
            try {
                inputStream = FileInputStream(path)
                inputStream?.let {inputStream ->
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
            ZLog.e("转换base64之后 = $result" )
            return result
        }
        return ""
    }

}