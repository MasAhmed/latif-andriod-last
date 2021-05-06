package com.latifapp.latif.ui.details

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.PopupWindow
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.postsapplication.network.NetworkClient
import com.latifapp.latif.R
import com.latifapp.latif.data.models.ImagesModel
import com.latifapp.latif.data.models.UserModel
import com.latifapp.latif.databinding.ActivityDetailsBinding
import com.latifapp.latif.databinding.CallDialogBinding
import com.latifapp.latif.databinding.TopOptionMenuBinding
import com.latifapp.latif.ui.base.BaseActivity
import com.latifapp.latif.ui.details.dialog.ReportDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsActivity : BaseActivity<DetailsViewModel, ActivityDetailsBinding>() {
    private var phoneNum: String?=""
    private lateinit var topMenuPopUp: PopupWindow
    private lateinit var callPopUp: PopupWindow
    private var id :Int?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = ContextCompat.getColor(this, R.color.pippin)


        }

        id= intent.extras?.getInt("ID")
        getDetails()

        binding.callBtn.setOnClickListener {
            callDialogShow(it)
        }

        binding.menuBtn.setOnClickListener {

            topMenuDialogShow(it)

        }
        binding.backBtn.setOnClickListener {
            onBackPressed()
        }

    }

    private fun getDetails() {
        viewModel.getAdDetails(id).observe(this, Observer {
            if (it != null) {

                setListOfImages(it.images)

                binding.container.visibility = View.VISIBLE
                binding.dateTxt.text = it.created_at
                binding.priceTxt.text = "${it.price} EGP"
                binding.petName.text = it.name
                binding.descriptionTxt.text = it.description
                setSellerInfo(it.createdBy, it.external_link)

            }
        })
    }

    private fun setSellerInfo(createdBy: UserModel?, external: Boolean) {
        binding.sellerNameTxt.text = "${createdBy?.firstName} ${createdBy?.lastName}"
        phoneNum = createdBy?.phone
        val image =createdBy?.avatar
        if (!image.isNullOrEmpty()) {
            var imagePath=image
            if (!external)
                imagePath= NetworkClient.BASE_URL +imagePath
            Glide.with(this).load(imagePath)
                .error(R.drawable.ic_image)
                .placeholder(R.drawable.ic_image).into(binding.profilePic)
        }
    }

    private fun setListOfImages(images: List<ImagesModel>?) {
        binding.recyclerView.apply {
            layoutManager =
                LinearLayoutManager(this@DetailsActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = PetImageAdapter(images)
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
                ReportDialogFragment().show(supportFragmentManager, "ReportDialog")
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
                val intent = Intent(Intent.ACTION_VIEW,Uri.parse("sms:${Uri.parse(phoneNum)}"))
                startActivity(intent)
                callPopUp.dismiss()
            }
            popupBinding.callBtn.setOnClickListener {
                val intent = Intent(Intent.ACTION_DIAL,Uri.parse("tel:${Uri.parse(phoneNum)}"))
                startActivity(intent)
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

    override fun setBindingView(inflater: LayoutInflater): ActivityDetailsBinding {
        return ActivityDetailsBinding.inflate(inflater)
    }

    override fun showLoader() {
        binding.loader.bar.visibility=View.VISIBLE
    }

    override fun hideLoader() {
        binding.loader.bar.visibility=View.GONE
    }


}