package com.example.ppt_munic.pantallas.menu

import android.app.Activity
import android.content.Intent
import android.widget.ImageView
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.ppt_munic.R
import com.example.ppt_munic.pantallas.categoria.CategoriasActivity
import com.example.ppt_munic.pantallas.noticia.NoticiasActivity
import com.google.android.material.navigation.NavigationView

object DrawerManager {

    fun setupDrawer(activity: Activity, drawerLayout: DrawerLayout, navView: NavigationView, menuIcon: ImageView) {

        // Abre Drawer desde tu botón personalizado
        menuIcon.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }

        // Cierra Drawer desde el botón (X) del header
        val header = navView.getHeaderView(0)
        val closeDrawer = header.findViewById<ImageView>(R.id.close_drawer)
        closeDrawer.setOnClickListener {
            drawerLayout.closeDrawer(GravityCompat.START)
        }

        // Listener de opciones del Drawer
        navView.setNavigationItemSelectedListener {
            when (it.itemId) {

                R.id.nav_categoria -> {
                    val intent = Intent(activity, CategoriasActivity::class.java)
                    activity.startActivity(intent)
                }
                R.id.nav_noticias -> {
                    val intent = Intent(activity, NoticiasActivity::class.java)
                    activity.startActivity(intent)
                }
                R.id.nav_acerca -> {
                    Toast.makeText(activity, "Acerca de", Toast.LENGTH_SHORT).show()
                }
                R.id.nav_voz -> {
                    Toast.makeText(activity, "Voz del Pueblo", Toast.LENGTH_SHORT).show()
                }
            }

            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }
    }
}