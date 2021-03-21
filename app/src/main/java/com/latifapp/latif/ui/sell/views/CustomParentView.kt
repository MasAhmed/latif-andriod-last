package com.latifapp.latif.ui.sell.views

import android.content.Context
import android.view.View

abstract class CustomParentView(val context:Context,val label:String) {
    protected  var view: View? =null
    init {
        createView()
    }
    abstract fun createView()

    @JvmName("getView1")
    fun getView(): View? = view
}