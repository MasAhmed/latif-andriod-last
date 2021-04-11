package com.latifapp.latif.ui.filter

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View

import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
 import com.latifapp.latif.R
 import com.latifapp.latif.data.models.RequireModel
import com.latifapp.latif.databinding.ActivitySellBinding
import com.latifapp.latif.ui.base.BaseActivity
import com.latifapp.latif.ui.map.MapsActivity
import com.latifapp.latif.ui.sell.SellViewModel
 import com.latifapp.latif.ui.sell.views.*
import com.latifapp.latif.utiles.Permissions
import com.latifapp.latif.utiles.Utiles

import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class FilterFormActivity : BaseActivity<FilterViewModel, ActivitySellBinding>(){

    private var url: String? = ""
    private var lat = 0.0
    private var lng = 0.0
    private val hashMap: MutableMap<String, Any> = mutableMapOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.backBtn.setOnClickListener { onBackPressed() }
        binding.title.setText(R.string.filter)
        binding.submitBtn.setText(R.string.filter)
        getForm("PETS")

        binding.submitBtn.setOnClickListener {
            submitAdForm()
        }

    }

    private fun submitAdForm() {
        if (hashMap.isNullOrEmpty())
            toastMsg_Warning(getString(R.string.addFormValue), binding.root, this)
        else
            lifecycleScope.launchWhenStarted {
                viewModel.saveForm(url!!, hashMap).observe(this@FilterFormActivity, Observer {
                    if (!it.msg.isNullOrEmpty()) {
                        toastMsg_Success(it.msg, binding.root, this@FilterFormActivity)

                    }
                    onBackPressed()
                })
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
            viewModel.getUrlInfo(model_).observe(this@FilterFormActivity, Observer {
                if (it != null)
                    createSpinner(it)
            })


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

                startActivityForResult(
                    Intent(this, MapsActivity::class.java),
                    Permissions.MapRequest
                )
            }
        }
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



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == Permissions.MapRequest) {
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



}