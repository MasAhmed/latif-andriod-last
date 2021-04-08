package com.latifapp.latif.ui.sell.views

import android.content.Context
import android.view.View

abstract class CustomParentView<T>(val context:Context,val label:String,val action :ViewAction<T>?) {
    protected  var view: View? =null
    init {

    }
    abstract fun createView()

    @JvmName("getView1")
    fun getView(): View? {
        createView()
         return view
    }

    public interface ViewAction<T>{
        fun getActionId(text:T)
    }
}