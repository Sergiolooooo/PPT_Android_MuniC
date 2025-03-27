package com.example.ppt_munic.pantallas.menu

import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.example.ppt_munic.R
import com.google.android.material.navigation.NavigationView
import com.example.ppt_munic.pantallas.menu.DrawerManager



open class DrawerActivity : AppCompatActivity() {

    protected lateinit var drawerLayout: DrawerLayout
    protected lateinit var navView: NavigationView
    protected lateinit var menuIcon: ImageView

    override fun setContentView(layoutResID: Int) {
        super.setContentView(layoutResID)

        drawerLayout = findViewById(R.id.drawer_layout)
        navView = findViewById(R.id.nav_view)
        menuIcon = findViewById(R.id.menu_icon)

        DrawerManager.setupDrawer(this, drawerLayout, navView, menuIcon)
    }
}