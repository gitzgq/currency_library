package com.zgq.common.base.other

import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Uri
import android.net.wifi.WifiManager
import android.view.View
import android.view.inputmethod.InputMethodManager
import java.net.Inet4Address
import java.net.NetworkInterface

object ZTool {

    /**
     * 强制显示键盘（配合定时器 Timer使用）
     */
    fun showKeyboard(view: View?) {
        if (null == view || null == view?.context) {
            return
        }
        val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm?.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS)
    }

    /**
     * 隐藏键盘
     */
    fun hideKeyboard(view: View?) {
        if (null == view || null == view?.context) {
            return
        }
        val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm?.hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
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
        if (null == context || ZStringUtil.isEmpty(content)) {
            return
        }
        val cm = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        // 将文本内容放到系统剪贴板里。
        cm?.text = content
        ZToast.toast(context, "复制成功")
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

}