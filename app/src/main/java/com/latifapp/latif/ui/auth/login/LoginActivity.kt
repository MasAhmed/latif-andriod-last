package com.latifapp.latif.ui.auth.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.latifapp.latif.databinding.ActivityLoginBinding
import com.latifapp.latif.ui.auth.signup.SignUpActivity
import com.latifapp.latif.ui.main.home.MainActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signUpBtn.setOnClickListener {
            startActivity(Intent(this,SignUpActivity::class.java))
        }
        binding.loginBtn.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
        }
     }
}