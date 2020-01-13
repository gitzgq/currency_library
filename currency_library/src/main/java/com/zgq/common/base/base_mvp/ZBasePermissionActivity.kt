package com.zgq.common.base.base_mvp

import android.content.pm.PackageManager
import com.zgq.common.base.other.ZPermission
import com.zgq.common.base.other.ZToast

/**
 * activity权限请求基类
 */
open abstract class ZBasePermissionActivity<P : ZBasePresenter<*>> : ZBaseActivity<P>(){

    // 权限回调接口
    private var permissionCallBack : ZPermission.OnPermissionCallBack? = null

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == ZPermission.REQUEST_CODE){
            var size : Int = grantResults.size
            if(size <= 0){
                return
            }
            // 0..size 包含size    0 until size 不包含size
            for(index in 0 until size){
                if(grantResults[index] != PackageManager.PERMISSION_GRANTED) {// 拒绝
                    if(ZPermission.showPermission(this, permissions[index])){// 没有勾选 不再提示
                        ZPermission.requestPermission(this, permissions, requestCode)
                    }else{
                        ZToast.toast(this, "请去设置-打开权限")
                    }
                    return
                }
            }
            // 同意请求的所有权限
            runOnUiThread{
                permissionCallBack?.onAgree()
            }
        }
    }

    /**
     * 检查是否有权限  否：请求权限
     */
    fun checkPermission(permissions: Array<String>?, callBack : ZPermission.OnPermissionCallBack) : Boolean{
        return checkPermission(permissions, ZPermission.REQUEST_CODE, callBack)
    }

    /**
     * 检查是否有权限  否：请求权限
     */
    fun checkPermission(permissions: Array<String>?, requestCode: Int, callBack : ZPermission.OnPermissionCallBack) : Boolean{
        if(ZPermission.checkPermission(this, permissions, requestCode)){
            callBack.onAgree()
            return true
        }
        this.permissionCallBack = callBack
        return false
    }

}