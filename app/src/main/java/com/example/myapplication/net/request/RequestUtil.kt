package com.example.myapplication.net.request

import com.example.myapplication.net.response.BaseObserver
import com.example.myapplication.app.ConfigUtil
import com.example.myapplication.bean.Template101Bean
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * 接口请求管理类
 */
object RequestUtil {

    /** 首页banner */
    fun loadHomeBanner(observer: BaseObserver<ArrayList<Template101Bean>>) {
        ConfigUtil.service
                .loadBanner()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer)

    }


}