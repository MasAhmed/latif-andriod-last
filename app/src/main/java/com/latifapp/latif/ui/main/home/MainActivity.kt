package com.latifapp.latif.ui.main.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.ImageView
import androidx.appcompat.widget.SearchView
import androidx.core.view.GravityCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.etebarian.meowbottomnavigation.MeowBottomNavigation
import com.latifapp.latif.R
import com.latifapp.latif.databinding.ActivityMainBinding
import com.latifapp.latif.ui.base.BaseActivity
import com.latifapp.latif.ui.filter.FilterActivity
import com.latifapp.latif.ui.filter.FilterFormActivity
import com.latifapp.latif.ui.main.profile.ProfileActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.coroutineContext
@AndroidEntryPoint
class MainActivity : BaseActivity<MainViewModel,ActivityMainBinding>(), NavController.OnDestinationChangedListener,
    MenuAdapter.MenuAction, BottomNavItemsAdapter.Action {
    private val bottomAdapter=BottomNavItemsAdapter(this@MainActivity)
    private lateinit var navigation: NavController
    public lateinit var searchview:ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        searchview=binding.toolbar.searchBtn

         navigation = Navigation.findNavController(
            this,
            R.id.fragment_container
        )
        searchview.setOnClickListener {
            startActivity(Intent(this@MainActivity, FilterFormActivity::class.java))
        }

        setBottomBarNav()
        navigation.addOnDestinationChangedListener(this)
        setMenu()
    }

    private fun setBottomBarNav() {
        binding.bottomNavRecyclerView.apply {
            layoutManager=GridLayoutManager(this@MainActivity,5)
            adapter=bottomAdapter
        }
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
            R.id.pets_fragments -> bottomAdapter.show(0)
            R.id.items_fragments -> bottomAdapter.show(1)
            R.id.clinic_fragments -> bottomAdapter.show(2)
            R.id.services_fragments -> bottomAdapter.show(3)
            R.id.chat_fragments -> bottomAdapter.show(4)
        }
    }

    override fun menuClick(enum: MenuAdapter.MenuEnum) {
        when (enum) {
            MenuAdapter.MenuEnum.profile -> startActivity(
                Intent(this, ProfileActivity::class.java))
            MenuAdapter.MenuEnum.blogs -> navigation.navigate(R.id.nav_blogs_fragments)
            MenuAdapter.MenuEnum.pets -> navigation.navigate(R.id.nav_pets_list_fragments)
            MenuAdapter.MenuEnum.items -> navigation.navigate(R.id.nav_items_fragments)
            MenuAdapter.MenuEnum.service -> navigation.navigate(R.id.nav_services_fragments)
         }

        runBlocking {
            binding.drawerLayout.closeDrawers()
        }

    }

    override fun selectedItem(pos: Int) {
         when(pos){
             0 -> navigation.navigate(R.id.nav_pets_fragments)
             1 -> navigation.navigate(R.id.nav_items_fragments)
             2 -> navigation.navigate(R.id.nav_clinic_fragments)
             3 -> navigation.navigate(R.id.nav_services_fragments)
             4 -> navigation.navigate(R.id.nav_chat_fragments)
         }
    }

    override fun setBindingView(inflater: LayoutInflater): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun showLoader() {
     }

    override fun hideLoader() {
     }
}