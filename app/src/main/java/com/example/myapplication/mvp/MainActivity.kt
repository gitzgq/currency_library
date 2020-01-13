package com.example.myapplication.mvp

import MainPresenter
import android.view.View
import com.example.myapplication.R
import com.example.myapplication.utils.InvokeUtil
import com.zgq.common.base.base_mvp.ZBasePermissionActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : ZBasePermissionActivity<MainPresenter>(), View.OnClickListener {

    override val contentView: Int
        get() = R.layout.activity_main

    override val getPresenter: MainPresenter
        get() = MainPresenter(this)

    override fun initView() {
        m_btn_recyclerview.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.m_btn_recyclerview ->{
                InvokeUtil.instance.invoke(this, InvokeUtil.RECYCLERVIEW_LIST)
            }
        }
    }
}
