package com.latifapp.latif.ui.auth.signup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.datastore.preferences.createDataStore
import androidx.datastore.preferences.edit
import androidx.datastore.preferences.preferencesKey
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.latifapp.latif.R
import com.latifapp.latif.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
         Navigation.findNavController(this,
            R.id.fragment_container
        )
        binding.backBtn.setOnClickListener({
            onBackPressed()
        })
        val x= createDataStore("")

        lifecycleScope.launchWhenStarted {
            x.edit {
                it[preferencesKey<Int>("dark_mode")]=1
            }
        }
    }
}