package com.latifapp.latif.ui.base

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.latifapp.latif.data.local.AppPrefsStorage
import com.latifapp.latif.data.local.PreferenceConstants
import com.latifapp.latif.utiles.Utiles
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import javax.inject.Inject

open abstract class BaseActivity<viewmodel : BaseViewModel, viewbind : ViewBinding>():AppCompatActivity(),BaseView<viewbind> {
    @Inject
    lateinit var viewModel: viewmodel
    @Inject
    lateinit var appPrefsStorage: AppPrefsStorage
    public lateinit var binding: viewbind
    val lang
    get() = viewModel.lang

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = setBindingView(layoutInflater)
        setContentView(binding.root)

//        lifecycleScope.launch  {
//            appPrefsStorage.getValueAsFlow(PreferenceConstants.Lang_PREFS, "en").collect {
//                lang = it
//            }
//        }
            viewModel.errorMsg_.observe(this@BaseActivity, Observer {
                if (it.isNotEmpty())
                // Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                    toastMsg_Warning(it, binding.root, this@BaseActivity)
            })





        Utiles.log_D("nffnnnfnfnf22","${lang!="en"}  $lang")
         lifecycleScope.launchWhenStarted {
            withContext(Dispatchers.Main) {
                viewModel.loader_.collect {
                    if (it)
                        showLoader()
                    else hideLoader()
                }

            }
        }
    }
}