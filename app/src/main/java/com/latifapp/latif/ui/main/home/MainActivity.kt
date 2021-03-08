package com.latifapp.latif.ui.main.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.etebarian.meowbottomnavigation.MeowBottomNavigation
import com.latifapp.latif.R
import com.latifapp.latif.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.nav.add(MeowBottomNavigation.Model(1, R.drawable.ic_pets))
        binding.nav.add(MeowBottomNavigation.Model(2, R.drawable.ic_leash))
        binding.nav.add(MeowBottomNavigation.Model(3, R.drawable.ic_clinic))
        binding.nav.add(MeowBottomNavigation.Model(4, R.drawable.ic_services))
        binding.nav.add(MeowBottomNavigation.Model(5, R.drawable.ic_writing))
        binding.nav.show(1)
        setMenu()
    }

    private fun setMenu() {
        binding.menuRecycleview.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = MenuAdapter()
        }
        binding.toolbar.menuBtn.setOnClickListener {
            if (binding.drawerLayout.isOpen)
                binding.drawerLayout.closeDrawers()
            else
                binding.drawerLayout.openDrawer(GravityCompat.START)

        }
    }
}