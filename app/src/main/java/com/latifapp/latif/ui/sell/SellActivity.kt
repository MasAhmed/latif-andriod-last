package com.latifapp.latif.ui.sell

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.latifapp.latif.data.models.RequireModel
import com.latifapp.latif.data.models.SellModel
import com.latifapp.latif.databinding.ActivitySellBinding
import com.latifapp.latif.ui.sell.views.*

class SellActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySellBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySellBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val model = SellModel(
            "",
            listOf(
                RequireModel(
                    type = "String",
                    required = true,
                    name = "Code",
                    label = "Code"
                ),
                RequireModel(
                    type = "select",
                    required = true,
                    name = "training",
                    label = "training",
                    options = listOf("ncn", "ncnckc", "kckckc")
                ),
                RequireModel(
                    type = "boolean",
                    required = true,
                    name = "playWithKids",
                    label = "play With Kids"
                ),
                RequireModel(
                    type = "boolean",
                    required = true,
                    name = "playWithKids",
                    label = "play With Kids"
                ),
                RequireModel(
                    type = "boolean",
                    required = true,
                    name = "playWithKids",
                    label = "play With Kids"
                ),
                RequireModel(
                    type = "boolean",
                    required = true,
                    name = "playWithKids",
                    label = "play With Kids"
                ), RequireModel(
                    type = "select",
                    required = true,
                    name = "training",
                    label = "training",
                    options = listOf("ncn", "ncnckc", "kckckc")
                ), RequireModel(
                    type = "select",
                    required = true,
                    name = "training",
                    label = "training",
                    options = listOf("ncn", "ncnckc", "kckckc")
                ), RequireModel(
                    type = "select",
                    required = true,
                    name = "training",
                    label = "training",
                    options = listOf("ncn", "ncnckc", "kckckc")
                ),
                RequireModel(
                    type = "String",
                    required = true,
                    name = "Code",
                    label = "Code"
                ), RequireModel(
                    type = "String",
                    required = true,
                    name = "Code",
                    label = "Code"
                )
            )
        )

        for (model_ in model.list)
            when (model_.type) {
                "boolean" -> createSwitch(model_)
                "String" -> createEditText(model_)
                "select" -> {
//                    createTextView(model_)
                    createSpinner(model_)
                }
            }

    }

    private fun createTextView(model: RequireModel) {
        val text = CustomTextView(this, model.label!!)
        binding.container.addView(text.getView())
    }


    fun createEditText(model: RequireModel) {
        val text = CustomEditText(this, model.label!!, object :
            CustomParentView.ViewAction<String> {
            override fun getActionId(text: String) {

            }
        }
        )
        binding.container.addView(text.getView())
    }

    fun createSwitch(model: RequireModel) {
        val switch = CustomSwitch(this, model.label!!, object :
            CustomParentView.ViewAction<Boolean> {
            override fun getActionId(isON: Boolean) {

            }
        }
        )
        binding.container.addView(switch.getView())
    }

    fun createSpinner(model: RequireModel) {
        val text = CustomSpinner(this, model.label!!, model.options!!, object :
            CustomParentView.ViewAction<String> {
            override fun getActionId(text: String) {

            }
        }
        )
        binding.container.addView(text.getView())
    }
}