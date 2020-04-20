package com.example.restaurantsfinder.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
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

        findNavController(R.id.nav_host_fragment).addOnDestinationChangedListener { _, destination, _ ->
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
                findNavController(R.id.nav_host_fragment).navigate(R.id.breweriesFragment)
            }
//            R.id.navBreweryCity -> {
//                findNavController(R.id.nav_host_fragment).navigate(R.id.test)
//            }

//            R.id.navPolitics -> {
//                findNavController(R.id.nav_host_fragment).navigate(R.id.politicsFragment)
//            }
//
//            R.id.navVisited -> {
//                findNavController(R.id.nav_host_fragment).navigate(R.id.visitedFragment)
//            }
        }
        drawerLayout.closeDrawers()
        return true
    }
}
