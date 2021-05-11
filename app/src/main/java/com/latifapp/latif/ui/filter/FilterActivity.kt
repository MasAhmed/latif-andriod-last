package com.latifapp.latif.ui.filter

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.latifapp.latif.R
import com.latifapp.latif.data.models.AdsModel
import com.latifapp.latif.databinding.ActivityFilterBinding
import com.latifapp.latif.ui.base.BaseActivity
import com.latifapp.latif.ui.details.DetailsActivity
import com.latifapp.latif.ui.main.petsList.PetsListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FilterActivity : BaseActivity<FilterViewModel,ActivityFilterBinding>() {
    private var url: String?=""
    private var type: String?=""
    private var hashMap: MutableMap<String, Any> = mutableMapOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.backBtn.setOnClickListener {
            onBackPressed()
        }
        url=intent.extras?.getString("url")
        type=intent.extras?.getString("type")
        hashMap=intent.extras?.getSerializable("hashMap") as MutableMap<String, Any>

         getFilter()
    }

    private fun getFilter() {

        lifecycleScope.launchWhenStarted {
            viewModel.saveForm(url!!, hashMap,type).observe(this@FilterActivity, Observer {
                if (!it.response.data.isNullOrEmpty()) {
                     setList(it.response.data)
                }else toastMsg_Warning(getString(R.string.noAds),binding.root,this@FilterActivity)

            })
        }
    }

    private fun setList(data: List<AdsModel>?) {
        val adapter_ = PetsListAdapter()
        binding.list.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = adapter_
         }
        adapter_.action= object : PetsListAdapter.Action{
            override fun onAdClick(id: Int?) {
                val intent = Intent(this@FilterActivity, DetailsActivity::class.java)
                intent.putExtra("ID",id)
                startActivity(intent)
            }
        }
        adapter_.list=data as MutableList<AdsModel>
    }

    override fun setBindingView(inflater: LayoutInflater): ActivityFilterBinding {
        return ActivityFilterBinding.inflate(inflater)
    }

    override fun showLoader() {
        binding.loader.bar.visibility= View.VISIBLE
    }

    override fun hideLoader() {
        binding.loader.bar.visibility= View.GONE
    }
}