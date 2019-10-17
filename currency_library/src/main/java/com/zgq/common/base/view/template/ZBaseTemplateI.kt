package com.zgq.common.base.view.template

import android.os.Bundle
import androidx.fragment.app.DialogFragment

interface ZBaseTemplateI {

    fun setData(bundle : Bundle?)

    fun setData(position : Int)

    fun setData(any : Any?)

    fun setData(any : Any?, position : Int)

    fun setData(any : Any?, bundle : Bundle?)

    fun setData(any : Any?, position : Int, bundle : Bundle?)

    fun setData(bundle : Bundle?, dialog : DialogFragment?, dialogClickListener: ZOnDialogClickListener?)

}