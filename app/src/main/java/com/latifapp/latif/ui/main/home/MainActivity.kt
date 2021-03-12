package com.latifapp.latif.ui.main.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.view.GravityCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.etebarian.meowbottomnavigation.MeowBottomNavigation
import com.latifapp.latif.R
import com.latifapp.latif.databinding.ActivityMainBinding
import com.latifapp.latif.ui.main.profile.ProfileActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.coroutineContext

class MainActivity : AppCompatActivity(), NavController.OnDestinationChangedListener,
    MenuAdapter.MenuAction {
    private lateinit var navigation: NavController
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


         navigation = Navigation.findNavController(
            this,
            R.id.fragment_container
        )
        binding.nav.setOnClickMenuListener {
            when (it.id) {
                1 -> navigation.navigate(R.id.pets_fragments)
                2 -> navigation.navigate(R.id.items_fragments)
                3 -> navigation.navigate(R.id.clinic_fragments)
                4 -> navigation.navigate(R.id.services_fragments)
                5 -> navigation.navigate(R.id.chat_fragments)
            }
        }
        navigation.addOnDestinationChangedListener(this)
        setMenu()
    }

    private fun setMenu() {
        binding.menuRecycleview.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = MenuAdapter(this@MainActivity)
        }
        binding.toolbar.menuBtn.setOnClickListener {
            if (binding.drawerLayout.isOpen)
                binding.drawerLayout.closeDrawers()
            else
                binding.drawerLayout.openDrawer(GravityCompat.START)

        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {
        when (destination.id) {
            R.id.pets_fragments -> binding.nav.show(1)
            R.id.items_fragments -> binding.nav.show(2)
            R.id.clinic_fragments -> binding.nav.show(3)
            R.id.services_fragments -> binding.nav.show(4)
            R.id.chat_fragments -> binding.nav.show(5)
        }
    }

    override fun menuClick(enum: MenuAdapter.MenuEnum) {
        when (enum) {
            MenuAdapter.MenuEnum.profile -> startActivity(
                Intent(this, ProfileActivity::class.java))
            MenuAdapter.MenuEnum.blogs -> navigation.navigate(R.id.blogs_fragments)
        }

        runBlocking {
            binding.drawerLayout.closeDrawers()
        }

    }
}