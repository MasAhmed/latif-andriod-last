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
import com.latifapp.latif.utiles.Permissions.MapRequest
import com.latifapp.latif.utiles.Permissions.galleryRequest
import com.latifapp.latif.utiles.Utiles
import com.latifapp.latif.utiles.getRealPathFromGallery
import com.nguyenhoanglam.imagepicker.model.Config
import com.nguyenhoanglam.imagepicker.model.Image
import com.nguyenhoanglam.imagepicker.ui.imagepicker.ImagePicker
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_sell.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import java.io.File
import java.util.*

@AndroidEntryPoint
class SellActivity : BaseActivity<SellViewModel, ActivitySellBinding>(),
    AdapterView.OnItemSelectedListener {

    private var url: String? = ""
    private lateinit var typeList: List<AdsTypeModel>
    private var items = arrayOf<String>()
    private var lat = 0.0
    private var lng = 0.0
    private lateinit var liveData: MutableLiveData<String>
    private val hashMap: MutableMap<String, Any> = mutableMapOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.backBtn.setOnClickListener { onBackPressed() }
        items = arrayOf<String>(
            getString(R.string.camera),
            getString(R.string.gallery),
            getString(R.string.cancel_)
        )

        getAdsType()

        binding.submitBtn.setOnClickListener {
            submitAdForm()
        }

    }

    private fun submitAdForm() {
        if (hashMap.isNullOrEmpty())
            toastMsg_Warning(getString(R.string.addFormValue), binding.root, this)
        else
            lifecycleScope.launchWhenStarted {
                viewModel.saveForm(url!!, hashMap).observe(this@SellActivity, Observer {
                    if (!it.msg.isNullOrEmpty()) {
                        toastMsg_Success(it.msg, binding.root, this@SellActivity)

                    }
                    onBackPressed()
                })
            }
    }

    private fun getAdsType() {
        lifecycleScope.launchWhenStarted {
            viewModel.getAdsTypeList().collect {
                if (!it.isNullOrEmpty()) {
                    typeList = it
                    setAdsTypeSpinnerData()
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

        binding.spinner.apply {
            adsTypeSpinner.setAdapter(arrayAdapter)
            adsTypeSpinner.onItemSelectedListener = this@SellActivity
            label.text = getString(R.string.ads_types)
            root.visibility = VISIBLE

        }
    }

    private fun getForm(type: String?) {


        lifecycleScope.launchWhenStarted {
            viewModel.getCreateForm(type!!).collect {
                if (!it.form.isNullOrEmpty()) {
                    url = it.url
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
                "url_option" -> getUrlInfo(model_)
                else -> createEditText(model_)


            }
    }

    private fun getUrlInfo(model_: RequireModel) {
        lifecycleScope.launchWhenStarted {
            viewModel.getUrlInfo(model_).observe(this@SellActivity, Observer {
                if (it != null)
                    createSpinner(it)
            })


        }
    }


    private fun createCheckBoxGroup(model: RequireModel) {
        var header=model.label
        if (!lang.equals("en"))
         header=model.label_ar
        val text = CustomCheckBoxGroup(this, header!!, model.options!!, object :
            CustomParentView.ViewAction<String> {
            override fun getActionId(text: String) {
                setHashMapValues("${model.name}", "$text")
            }
        }
        )
        binding.container.addView(text.getView())
    }

    private fun createRadioButtonGroup(model: RequireModel) {
        var header=model.label
        if (!lang.equals("en"))
            header=model.label_ar
        val text = CustomRadioGroup(this, header!!, model.options!!, object :
            CustomParentView.ViewAction<String> {
            override fun getActionId(text: String) {
                setHashMapValues("${model.name}", "$text")
            }
        }
        )
        binding.container.addView(text.getView())
    }

    private fun createMapBtn(model: RequireModel) {
        binding.mapContainer.visibility = View.VISIBLE
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

                startActivityForResult(Intent(this, MapsActivity::class.java), MapRequest)
            }
        }
    }

    private fun createImage(model: RequireModel) {
        var header=model.label
        if (!lang.equals("en"))
            header=model.label_ar
        val view = CustomImage(this, header!!, object :
            CustomParentView.ViewAction<ImageView> {
            override fun getActionId(imageView: ImageView) {
                liveData = MutableLiveData<String>()
                liveData.observe(this@SellActivity, Observer {
                    Glide.with(this@SellActivity).load(it).into(imageView)
                    setHashMapValues(model.name!!, it)
                })
                choose(false)
            }
        })
        binding.container.addView(view.getView())
    }

    private fun createImagesList(model: RequireModel) {
        val adapter = ImagesAdapter()
        val listOfImages = mutableListOf<String>()
        var header=model.label
        if (!lang.equals("en"))
            header=model.label_ar
        val view = CustomImagesList(this, header!!, adapter, object :
            CustomParentView.ViewAction<View> {
            override fun getActionId(btn: View) {
                liveData = MutableLiveData<String>()
                liveData.observe(this@SellActivity, Observer {
                    adapter.list.add(it)
                    adapter.notifyDataSetChanged()
                    //   listOfImages.add(it)
                    //  hashMap.put(model.name!!, listOfImages)
                })
                choose(true)
            }
        })


        binding.container.addView(view.getView())

    }

    fun createEditText(model: RequireModel) {
        if (model.label.isNullOrEmpty()) return
        var header=model.label
        if (!lang.equals("en"))
            header=model.label_ar
        val text =
            CustomEditText(this, header!!, model.type?.toLowerCase().equals("string"), object :
                CustomParentView.ViewAction<String> {
                override fun getActionId(text: String) {
                    setHashMapValues("${model.name}", "$text")
                }
            }
            )
        binding.container.addView(text.getView())
    }

    fun createSwitch(model: RequireModel) {
        var header=model.label
        if (!lang.equals("en"))
            header=model.label_ar
        setHashMapValues("${model.name}", "false")
        val switch = CustomSwitch(this, header!!, object :
            CustomParentView.ViewAction<Boolean> {
            override fun getActionId(isON: Boolean) {
                setHashMapValues("${model.name}", "$isON")
            }
        }
        )
        binding.container.addView(switch.getView())
    }

    fun createSpinner(model: RequireModel) {
        var header=model.label
        if (!lang.equals("en"))
            header=model.label_ar
        val text = CustomSpinner(this, header!!, model.options!!, object :
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
            .setMultipleMode(false) //  Select multiple images or single image
            .setFolderMode(true) //  Folder mode
            .setShowCamera(false) //  Show camera button
            .setDoneTitle("Done") //  Done button title
            .setLimitMessage("You have reached selection limit") // Selection limit message
            .setAlwaysShowDoneButton(true) //  Set always show done button in multiple mode
            .setRequestCode(galleryRequest) //  Set request code, default Config.RC_PICK_IMAGES
            .setKeepScreenOn(true)
            .setSavePath("latif")
            .setMaxSize(1)
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
                    for (image in list)
                        viewModel.uploadImage(image).observe(this, Observer {
                            if (!it.isNullOrEmpty() && !it.equals("-1"))
                                liveData.postValue(it)
                        })


                } catch (e: Exception) {
                    e.printStackTrace()
                }
            } else if (requestCode == MapRequest) {
                lat = data!!.extras!!.getDouble("lat")
                lng = data!!.extras!!.getDouble("lng")
                setHashMapValues("latitude", "$lat")
                setHashMapValues("longitude", "$lng")
                val placename = data!!.extras!!.getString("placeName")
                binding.placeNme.text = placename
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


    fun setHashMapValues(key: String, value: String) {
        if (value.isNullOrEmpty())
            hashMap.remove(key)
        else
            hashMap.put(key, value)


        Utiles.log_D("cncnncncncncn", hashMap)
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        getForm(typeList.get(position).name)
        binding.container.removeAllViews()
        binding.mapContainer.visibility = GONE
        binding.placeNme.text = ""
        hashMap.clear()
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }
}