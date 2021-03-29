package com.latifapp.latif.ui.sell

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.latifapp.latif.R
import com.latifapp.latif.data.models.AdsTypeModel
import com.latifapp.latif.data.models.RequireModel
import com.latifapp.latif.databinding.ActivitySellBinding
import com.latifapp.latif.ui.base.BaseActivity
import com.latifapp.latif.ui.map.MapsActivity
import com.latifapp.latif.ui.sell.adapters.ImagesAdapter
import com.latifapp.latif.ui.sell.views.*
import com.latifapp.latif.utiles.Permissions
import com.latifapp.latif.utiles.Permissions.galleryRequest
import com.latifapp.latif.utiles.Utiles
import com.latifapp.latif.utiles.getRealPathFromGallery
import com.nguyenhoanglam.imagepicker.model.Config
import com.nguyenhoanglam.imagepicker.model.Image
import com.nguyenhoanglam.imagepicker.ui.imagepicker.ImagePicker
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import java.io.File
import java.util.*

@AndroidEntryPoint
class SellActivity : BaseActivity<SellViewModel, ActivitySellBinding>(),
    AdapterView.OnItemClickListener {

    private var url: String?=""
    private lateinit var typeList: List<AdsTypeModel>
    private var items = arrayOf<String>()
    private lateinit var liveData: MutableLiveData<MutableList<String>>
    private val hashMap: MutableMap<String, String> = mutableMapOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launchWhenStarted {
            viewModel.getAdsTypeList().collect {
                if (!it.isNullOrEmpty()) {
                    typeList = it
                    setAdsTypeSpinnerData()
                }
            }
        }

        items = arrayOf<String>(
            getString(R.string.camera),
            getString(R.string.gallery),
            getString(R.string.cancel_)
        )

        binding.submitBtn.setOnClickListener {
            lifecycleScope.launchWhenStarted {
                viewModel.saveForm(url!!, hashMap).collect {

                }
            }
        }

    }

    private fun setAdsTypeSpinnerData() {
        val list_ = typeList.map { it.name }
        val arrayAdapter = this@SellActivity?.let {
            ArrayAdapter<String>(
                it, android.R.layout.simple_list_item_1, list_
            )
        }

        binding.adsTypeSpinner.apply {
            setAdapter(arrayAdapter)
            setOnClickListener {
                showDropDown()
            }
            setOnFocusChangeListener { _, _ ->
                showDropDown()
            }
            onItemClickListener = this@SellActivity
            visibility = VISIBLE

        }
    }

    private fun getForm(type: String?) {
        lifecycleScope.launchWhenStarted {
            viewModel.getCreateForm(type!!).collect {
                if (!it.form.isNullOrEmpty()) {
                    url=it.url
                    setFormViews(it.form)
                }
            }
        }
    }

    private fun setFormViews(form: List<RequireModel>) {
        for (model_ in form)
            when (model_.type?.toLowerCase()) {
                "boolean" -> createSwitch(model_)
                "file" -> createImage(model_)
                "files" -> createImagesList(model_)
                "select" -> createCheckBoxGroup(model_)
                "dropdown" -> createSpinner(model_)
                "radiobutton" -> createRadioButtonGroup(model_)
                "map" -> createMapBtn(model_)
                else -> createEditText(model_)


            }
    }


    private fun createCheckBoxGroup(model: RequireModel) {
        val text = CustomCheckBoxGroup(this, model.label!!, model.options!!, object :
            CustomParentView.ViewAction<String> {
            override fun getActionId(text: String) {
                setHashMapValues("${model.name}", "$text")
            }
        }
        )
        binding.container.addView(text.getView())
    }

    private fun createRadioButtonGroup(model: RequireModel) {
        val text = CustomRadioGroup(this, model.label!!, model.options!!, object :
            CustomParentView.ViewAction<String> {
            override fun getActionId(text: String) {
                setHashMapValues("${model.name}", "$text")
            }
        }
        )
        binding.container.addView(text.getView())
    }

    private fun createMapBtn(model: RequireModel) {
        binding.mapBtn.visibility = View.VISIBLE
        binding.mapBtn.setOnClickListener {
            // intent to map
            if (!Permissions.checkLocationPermissions(this)) {
                Permissions.showPermissionsDialog(
                    this,
                    "Request Location permission Is Needed",
                    Permissions.locationManifestPermissionsList,
                    0
                )
            } else {

                startActivity(Intent(this, MapsActivity::class.java))
            }
        }
    }

    private fun createImage(model: RequireModel) {

        val view = CustomImage(this, model.label!!, object :
            CustomParentView.ViewAction<ImageView> {
            override fun getActionId(imageView: ImageView) {
                liveData = MutableLiveData<MutableList<String>>()
                liveData.observe(this@SellActivity, Observer {
                    Glide.with(this@SellActivity).load(File(it.get(0))).into(imageView)
                })
                choose(false)
            }
        })
        binding.container.addView(view.getView())
    }

    private fun createImagesList(model: RequireModel) {
        val adapter = ImagesAdapter()
        val view = CustomImagesList(this, model.label!!, adapter, object :
            CustomParentView.ViewAction<View> {
            override fun getActionId(btn: View) {
                liveData = MutableLiveData<MutableList<String>>()
                liveData.observe(this@SellActivity, Observer {
                    adapter.list.addAll(it)
                    adapter.notifyDataSetChanged()
                })
                choose(true)
            }
        })


        binding.container.addView(view.getView())

    }


    fun createEditText(model: RequireModel) {
        if (model.label.isNullOrEmpty()) return
        val text =
            CustomEditText(this, model.label!!, model.type?.toLowerCase().equals("string"), object :
                CustomParentView.ViewAction<String> {
                override fun getActionId(text: String) {
                    setHashMapValues("${model.name}", "$text")
                }
            }
            )
        binding.container.addView(text.getView())
    }

    fun createSwitch(model: RequireModel) {
        setHashMapValues("${model.name}", "false")
        val switch = CustomSwitch(this, model.label!!, object :
            CustomParentView.ViewAction<Boolean> {
            override fun getActionId(isON: Boolean) {
                setHashMapValues("${model.name}", "$isON")
            }
        }
        )
        binding.container.addView(switch.getView())
    }

    fun createSpinner(model: RequireModel) {
        val text = CustomSpinner(this, model.label!!, model.options!!, object :
            CustomParentView.ViewAction<String> {
            override fun getActionId(text: String) {
                setHashMapValues("${model.name}", "$text")
            }
        }
        )
        binding.container.addView(text.getView())
    }


    private fun choose(isMultiple: Boolean) {
        val builder = AlertDialog.Builder(this)
        builder.setItems(items, { dialog, which ->
            // Get the dialog selected item
            val selected = items[which]
            when (selected) {
                getString(R.string.camera) -> checkCamera()
                getString(R.string.gallery) -> checkGallery(isMultiple)
                getString(R.string.cancel_) -> dialog.dismiss()
            }

        })

        builder.create().show()
    }

    private fun checkGallery(isMultiple: Boolean) {
        if (!Permissions.checkCameraPermissions(this)) {
            Permissions.showPermissionsDialog(
                this,
                "External Storage  Permission Is Needed",
                Permissions.cameraManifestPermissionsList,
                galleryRequest
            )
        } else {
            selectFromGallery(galleryRequest, isMultiple)
        }
    }

    private fun selectFromGallery(galleryRequest: Int, isMultiple: Boolean) {
        ImagePicker.with(this) //  Initialize ImagePicker with activity or fragment context
            .setToolbarColor("#212121") //  Toolbar color
            .setStatusBarColor("#000000") //  StatusBar color (works with SDK >= 21  )
            .setToolbarTextColor("#FFFFFF") //  Toolbar text color (Title and Done button)
            .setToolbarIconColor("#FFFFFF") //  Toolbar icon color (Back and Camera button)
            .setProgressBarColor("#4CAF50") //  ProgressBar color
            .setBackgroundColor("#212121") //  Background color
            .setCameraOnly(false) //  Camera mode
            .setMultipleMode(isMultiple) //  Select multiple images or single image
            .setFolderMode(true) //  Folder mode
            .setShowCamera(false) //  Show camera button
            .setDoneTitle("Done") //  Done button title
            .setLimitMessage("You have reached selection limit") // Selection limit message
            .setAlwaysShowDoneButton(true) //  Set always show done button in multiple mode
            .setRequestCode(galleryRequest) //  Set request code, default Config.RC_PICK_IMAGES
            .setKeepScreenOn(true)
            .setSavePath("latif")
            .setMaxSize(4)
            .start()
    }


    private fun checkCamera() {
        if (!Permissions.checkCameraPermissions(this))
            Permissions.showPermissionsDialog(
                this,
                "Camera And Gallery Permission Is Needed",
                Permissions.cameraManifestPermissionsList,
                galleryRequest
            )
        else takePhoto()
    }

    private fun takePhoto() {
        ImagePicker.with(this) //  Initialize ImagePicker with activity or fragment context
            .setCameraOnly(true)
            .setMultipleMode(false) //  Select multiple images or single image
            .setFolderMode(true) //  Folder mode
            .setShowCamera(false)
            .setAlwaysShowDoneButton(true) //  Set always show done button in multiple mode
            .setRequestCode(galleryRequest)
            .setKeepScreenOn(true)
            .setSavePath("latif")
            .setMaxSize(1)
            .start()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {

            if (requestCode == galleryRequest) {
                try {
                    val imagesList: ArrayList<Image> =
                        data!!.getParcelableArrayListExtra(Config.EXTRA_IMAGES)!!
                    val list = mutableListOf<String>()
                    for (model in imagesList) {
                        val uri = Uri.fromFile(File(model.path))
                        val path: String = uri.getRealPathFromGallery(this)
                        Utiles.log_D("dndnddd,dkkdkd2", path)
                        list.add(path)
                    }
                    liveData.postValue(list)


                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    override fun setBindingView(inflater: LayoutInflater): ActivitySellBinding {
        return ActivitySellBinding.inflate(inflater)
    }

    override fun showLoader() {
        binding.loader.bar.visibility = View.VISIBLE
    }

    override fun hideLoader() {

        binding.loader.bar.visibility = View.GONE
    }


    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        getForm(typeList.get(position).name)
        binding.container.removeAllViews()
        binding.mapBtn.visibility = GONE
        hashMap.clear()
    }


    fun setHashMapValues(key: String, value: String) {
        if (value.isNullOrEmpty())
            hashMap.remove(key)
        else
            hashMap.put(key, value)


        Utiles.log_D("cncnncncncncn", hashMap)
    }
}