package com.latifapp.latif.ui.auth.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import com.latifapp.latif.data.local.PreferenceConstants.Companion.Lang_PREFS
import com.latifapp.latif.databinding.ActivityLoginBinding
import com.latifapp.latif.ui.auth.signup.SignUpActivity
import com.latifapp.latif.ui.base.BaseActivity
import com.latifapp.latif.ui.main.home.MainActivity
import com.latifapp.latif.utiles.Utiles
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginActivity :BaseActivity<LoginViewModel,ActivityLoginBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        CoroutineScope(Dispatchers.Main).launch {
            appPrefsStorage.setValue(Lang_PREFS,"en")
            appPrefsStorage.getValueAsFlow(Lang_PREFS,"ar").collect {
                Utiles.log_D("msmsmsmsm",it)
            }

        }

        binding.signUpBtn.setOnClickListener {
            startActivity(Intent(this,SignUpActivity::class.java))
        }
        binding.loginBtn.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
        }
     }

    override fun setBindingView(inflater: LayoutInflater): ActivityLoginBinding {
        return  ActivityLoginBinding.inflate(inflater)

    }


    override fun showLoader() {
       // binding.loader.bar.visibility = View.VISIBLE
    }

    override fun hideLoader() {
       // binding.loader.bar.visibility = View.GONE
    }
}