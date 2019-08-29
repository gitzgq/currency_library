package com.example.myapplication

import MainPresenter
import com.zgq.common.base.mvp.ZBasePermissionActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : ZBasePermissionActivity<MainPresenter>() {

    override val contentView: Int
        get() = R.layout.activity_main

    override val getPresenter: MainPresenter
        get() = MainPresenter(this)

    override fun initView() {
        mPresenter?.loadBanner()
    }

    /**
     * banner数据获取成功
     */
    fun onSuccess(data: ArrayList<HomepageBannerBean>?){
        m_main_tv_title?.text = data?.get(0)?.linkTitle?: ""
    }

    override fun statusType(): Int {
        return TRANSPAREN
    }
}
