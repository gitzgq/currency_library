package com.example.myapplication

import MainPresenter
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.adapter.CommenRecylerViewAdapter
import com.example.myapplication.bean.Template101Bean
import com.zgq.common.base.data.ZEventData
import com.zgq.common.base.glide.ZGlide
import com.zgq.common.base.glide.ZSelectImg
import com.zgq.common.base.mvp.ZBasePermissionActivity
import com.zgq.common.base.other.ZLog
import com.zgq.common.base.other.ZPermission
import com.zgq.common.base.other.ZScreenUI
import com.zgq.common.base.view.ZRecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import java.io.File
import java.net.URI

class MainActivity : ZBasePermissionActivity<MainPresenter>(), ZRecyclerView.OnZLoadMoreListener {


    var mAdapter : CommenRecylerViewAdapter? = null
    var mListData = ArrayList<Any>()
    var pageIndex : Int = 1

    override val contentView: Int
        get() = R.layout.activity_main

    override val getPresenter: MainPresenter
        get() = MainPresenter(this)

    override fun initView() {
        mRecyclerView?.layoutManager = LinearLayoutManager(this)
        mAdapter = CommenRecylerViewAdapter(this, mListData, true)
        mRecyclerView?.adapter = mAdapter
        mRecyclerView?.setOnZLoadMoreListener(this)
        mPresenter?.loadBanner()

        var permissions = arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
        m_main_fwgxwj?.setOnClickListener {
            checkPermission(permissions, object : ZPermission.OnPermissionCallBack {
                override fun onAgree() {
                    ZSelectImg.instence.openAlbum(this@MainActivity, object : ZSelectImg.OnSelectImgCallBack{
                        override fun onFinish(uri: Uri) {
                            ZGlide.instence.loadImg(this@MainActivity, mIvImg, uri)
                        }
                    })
                }
            })
        }

    }

    /**
     * banner数据获取成功
     */
    fun onSuccess(oldSize : Int, currentSize : Int){
        mRecyclerView?.finishLoadMore(oldSize, currentSize, pageIndex)
        mRecyclerView?.finishLoadMore(true)
    }

    override fun statusType(): Int {
        return TRANSPAREN
    }

    override fun onCtyLoadMore() {
        mPresenter?.loadBanner()
    }

    override fun onCtyLoadErrorClick() {
        mPresenter?.loadBanner()
    }

    override fun registerEventBus(): Boolean {
        return true
    }

    @Subscribe
    fun onEvent(data: ZEventData){
        ZLog.e("id = ${data.id}")
    }
}
