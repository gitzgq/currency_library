package com.zgq.common.base

import android.os.Bundle
import com.zgq.common.base.bean.HomepageBannerBean
import com.zgq.common.base.bean.ZEventData
import com.zgq.common.base.glide.ZGlide
import com.zgq.common.base.glide.ZSelectImg
import com.zgq.common.base.mvp.ZBaseActivity
import com.zgq.common.base.mvp.ZBasePermissionActivity
import com.zgq.common.base.other.ZEventBus
import com.zgq.common.base.other.ZLog
import com.zgq.common.base.other.ZPermission
import com.zgq.common.base.other.ZToast
import kotlinx.android.synthetic.main.activity_main_layout.*
import org.greenrobot.eventbus.Subscribe
import java.io.File

class MainActivity : ZBasePermissionActivity<MainPresenter>(){
    override val contentView: Int
        get() = R.layout.activity_main_layout
    override val getPresenter: MainPresenter
        get() = MainPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_layout)
        mPresenter?.loadBanner()
//        val service : RetrofitService = RetrofitManager.getService(RetrofitService :: class.java)
//        service.load().subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe(object : BaseObserver<String>() {
//
//                override fun disposable(d: Disposable) {
//
//                }
//
//                override fun onSuccess(data: String?) {
//                    ZLog.e("获取到的数据 = " + data)
////                    ZLog.e("获取到的数据个数 = " + data?.size)
////                    ZLog.e("获取到的数据个数 = " + data?.get(0)?.linkTitle)
//                }
//
//                override fun onError(code: Int, errorMsg: String?) {
//                    ZLog.e("异常 = " + errorMsg)
//                }
//            })
        val url: String = "http://pic.97ui.com/yc_pic/00/00/08/79/53e3829fe1012cf8b092a602b7ba0e16.jpg%21w1200"
//        GlideUtil.instence.loadImg(this, iv, url)
//        GlideUtil.instence.loadRoundImg(this, iv1, url)
//        GlideUtil.instence.loadCircleImg(this, iv2, url)
        ZGlide.instence.loadOriginal(this, iv3, url)
//        GlideUtil.instence.loadOriginal(this, iv4, url)
//        GlideUtil.instence.loadOriginal(this, iv5, url)
//        GlideUtil.instence.loadOriginal(this, iv6, url)
//        GlideUtil.instence.loadOriginal(this, iv7, url)
//        GlideUtil.instence.loadOriginal(this, iv8, url)

        iv.setOnClickListener {
            postSticky(ZEventData(ZEventBus.E_100, "测试"))
//            checkPermission(ZPermission.STORAGE, object : ZPermission.OnPermissionCallBack{
//                override fun onAgree() {
//                    ZLog.e("同意了")
//                    ZSelectImg.instence.openAlbum(this@MainActivity, object : ZSelectImg.OnSelectImgCallBack{
//                        override fun onFinish(path: String) {
//                            ZGlide.instence.loadOriginal(this@MainActivity, iv, File(path))
//                        }
//                    })
//                }
//            })
        }
    }

    fun onSuccess(data : List<HomepageBannerBean>?){
        var size : Int = data?.size ?: 0
        if(size >= 1){
            ZGlide.instence.loadImg(this, iv, data?.get(0)?.image?: "")
        }
        if(size >= 2){
            ZGlide.instence.loadImg(this, iv1, data?.get(1)?.image?: "")
        }
        if(size >= 3){
            ZGlide.instence.loadImg(this, iv2, data?.get(2)?.image?: "")
        }
    }

    override fun registerEventBus(): Boolean {
        return true
    }

    @Subscribe
    fun onEvent(event : ZEventData){
        if(null == event || event.id < ZEventBus.E_100){
            return
        }
    }
}