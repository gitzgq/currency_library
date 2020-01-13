package com.example.myapplication.mvp

import MainPresenter
import android.net.Uri
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.adapter.CommenRecylerViewAdapter
import com.example.myapplication.utils.InvokeUtil
import com.zgq.common.base.data.ZEventData
import com.zgq.common.base.glide.ZGlide
import com.zgq.common.base.glide.ZSelectImg
import com.zgq.common.base.mvp.ZBasePermissionActivity
import com.zgq.common.base.other.ZLog
import com.zgq.common.base.other.ZPermission
import com.zgq.common.base.view.ZRecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import org.greenrobot.eventbus.Subscribe

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
