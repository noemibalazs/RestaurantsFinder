package com.example.restaurantsfinder.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.example.restaurantsfinder.R
import com.example.restaurantsfinder.base.BaseActivity
import com.example.restaurantsfinder.base.BaseViewModel
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_brewery_landing.*

@SuppressLint("Registered")
class BreweryLandingActivity : BaseActivity<BaseViewModel>(),
    NavigationView.OnNavigationItemSelectedListener {

    override fun initViewModel(): BaseViewModel {
        return ViewModelProviders.of(this).get(BaseViewModel::class.java)
    }

    val navController: NavController by lazy {
        return@lazy Navigation.findNavController(this, R.id.nav_host_fragment)
    }

    var myToolbar: Toolbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_brewery_landing)

        myToolbar = toolBar

        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolBar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navView.setNavigationItemSelectedListener(this)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            toolBar.title = destination.label
        }
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.navBreweryList -> {
                navController.navigate(R.id.breweriesFragment)
            }
            R.id.navBreweryCity -> {
                navController.navigate(R.id.breweriesByCity)
            }

            R.id.navBreweryState -> {
                navController.navigate(R.id.breweriesByState)
            }

            R.id.navBreweryName -> {
                navController.navigate(R.id.breweriesByName)
            }

            R.id.navBreweryVisited -> {
                navController.navigate(R.id.breweriesVisited)
            }
        }
        drawerLayout.closeDrawers()
        return true
    }
}
