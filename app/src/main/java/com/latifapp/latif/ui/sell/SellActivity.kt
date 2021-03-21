package com.latifapp.latif.ui.sell

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.latifapp.latif.R
import com.latifapp.latif.databinding.ActivityFilterBinding
import com.latifapp.latif.databinding.ActivitySellBinding
import com.latifapp.latif.ui.sell.views.CustomEditText
import com.latifapp.latif.ui.sell.views.CustomSpinner
import com.latifapp.latif.ui.sell.views.CustomSwitch
import com.latifapp.latif.ui.sell.views.CustomTextView

class SellActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySellBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySellBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val text= CustomEditText(this,"dhdhdhd ddhdhhdhdh")
        val text2= CustomSpinner(this,"dhdhdhd ddhdhhdhdh")
        binding.container.apply {
            addView(text2.getView())
        }
        binding.container.apply {
            addView(text.getView())
        }
    }
}