package com.latifapp.latif.ui.details

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.PopupWindow
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.latifapp.latif.R
import com.latifapp.latif.databinding.ActivityDetailsBinding
import com.latifapp.latif.databinding.CallDialogBinding
import com.latifapp.latif.databinding.TopOptionMenuBinding
import com.latifapp.latif.ui.details.dialog.ReportDialogFragment


class DetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailsBinding
    private lateinit var topMenuPopUp: PopupWindow
    private lateinit var callPopUp: PopupWindow

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = ContextCompat.getColor(this, R.color.pippin)
        }
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.callBtn.setOnClickListener {
            callDialogShow(it)
        }

        binding.menuBtn.setOnClickListener {

            topMenuDialogShow(it)

        }

        setListOfImages()
    }

    private fun setListOfImages() {
        binding.recyclerView.apply {
            layoutManager =
                LinearLayoutManager(this@DetailsActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = PetImageAdapter()
        }
    }

    private fun topMenuDialogShow(view: View?) {
        if (!::topMenuPopUp.isInitialized) {
            val popupBinding = TopOptionMenuBinding.inflate(layoutInflater)
            topMenuPopUp = PopupWindow(
                popupBinding.root,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            topMenuPopUp.setFocusable(true)
            topMenuPopUp.setOutsideTouchable(true)
            popupBinding.parentBg.setOnTouchListener { v, event ->
                topMenuPopUp.dismiss()
                true
            }
            popupBinding.shareBtn.setOnClickListener {
                topMenuPopUp.dismiss()
            }
            popupBinding.reportBtn.setOnClickListener {
                // show report dialog
                ReportDialogFragment().show(supportFragmentManager,"ReportDialog")
                topMenuPopUp.dismiss()
            }
        }
        if (topMenuPopUp.isShowing)
            topMenuPopUp.dismiss()
        else
            topMenuPopUp.showAtLocation(view, Gravity.END, 50, 50)
    }


    private fun callDialogShow(view: View) {
        if (!::callPopUp.isInitialized) {
            val popupBinding = CallDialogBinding.inflate(layoutInflater)
            callPopUp = PopupWindow(
                popupBinding.root,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            callPopUp.setFocusable(true)
            callPopUp.setOutsideTouchable(true)
            popupBinding.smsBtn.setOnClickListener {
                callPopUp.dismiss()
            }
            popupBinding.callBtn.setOnClickListener {
                callPopUp.dismiss()
            }
            popupBinding.chatBtn.setOnClickListener {
                callPopUp.dismiss()
            }
        }
        if (callPopUp.isShowing)
            callPopUp.dismiss()
        else
            callPopUp.showAsDropDown(view)
    }


}