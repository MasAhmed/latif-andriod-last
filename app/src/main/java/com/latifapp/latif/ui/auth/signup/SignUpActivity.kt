package com.latifapp.latif.ui.auth.signup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

    }
}