package com.zgq.common.base.other

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

/**
 * 权限 管理类
 */

object ZPermission {

    val REQUEST_CODE : Int = 1000

    /**
     * 检查权限 有：直接返回true  否：返回false且请求权限
     */
    fun checkPermission(context: Context?, permissions : Array<String>?, requestCode : Int) : Boolean{
        if(null == context || context !is Activity || null == permissions || permissions.isEmpty()){
            return false
        }

        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M){// 小于23 默认有权限
            return true
        }

        for (permission in permissions) {
            if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {// 没有权限，去授权
                requestPermission(context, permissions, requestCode)
                return false
            }
        }
        return true
    }

    /**
     * 请求系统权限
     */
    fun requestPermission(context: Context?, permissions: Array<String>?, requestCode: Int){
        if(null == context || context !is Activity || null == permissions || permissions.isEmpty()){
            return
        }

        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
            return
        }
        // 请求系统权限
        ActivityCompat.requestPermissions(context, permissions, requestCode)
    }

    /**
     * 判断是否可以弹出系统权限框
     * @return              true: 没有选中不再提示（可以弹出权限框）  false: 选中不再提示（不能弹出权限框）
     */
    fun showPermission(context: Context?, permission: String): Boolean {
        if (null == context || context !is Activity || ZStringUtil.isEmpty(permission)) {
            return true
        }
        return if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {// 小于23默认都有权限
            true
        } else ActivityCompat.shouldShowRequestPermissionRationale(context, permission)
    }

    /**
     * 打开应用详情页面
     * @param context
     * @param packageName   应用applicationId
     */
    fun openAppSetting(context: Context?, packageName: String?) {
        context?.let {
            packageName?.let {it1 ->
                val intent = Intent()
                intent?.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                val uri = Uri.fromParts("package", it1, null)
                intent?.data = uri
                it?.startActivity(intent)
            }
        }
    }

    interface OnPermissionCallBack{
        fun onAgree()
    }

}