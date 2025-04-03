package com.example.ppt_munic.pantallas.acercade

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.example.ppt_munic.R
import com.example.ppt_munic.pantallas.menu.DrawerActivity

class AcercaDeActivity : DrawerActivity() {

    private lateinit var btnCerrar: ImageView
    private lateinit var iconoComercio: ImageView
    private lateinit var tvTitulo: TextView
    private lateinit var tvContenido: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_acerca_de)

        btnCerrar = findViewById(R.id.btn_cerrar)
        iconoComercio = findViewById(R.id.iconoComercio)
        tvTitulo = findViewById(R.id.tvTituloAcerca)
        tvContenido = findViewById(R.id.tvContenidoAcerca)

        tvTitulo.text = "Acerca de Tenori"
        tvContenido.text = """
            ¡Bienvenidos a Tenori!
            
            Tenori es una plataforma móvil impulsada por la Municipalidad de Cañas con el objetivo de conectar a los ciudadanos con los comercios locales y ofrecer un canal ágil y seguro para reportar incidencias en la comunidad. Queremos que te sientas parte activa de este espacio, donde tu participación contribuye directamente al bienestar y desarrollo de nuestro cantón.

            A través de esta aplicación podrás descubrir los negocios cercanos, conocer sus servicios y apoyar la economía local. Además, podrás reportar situaciones que requieran atención municipal, promoviendo juntos una comunidad más segura, ordenada y participativa.

            Esta plataforma ha sido desarrollada con compromiso y dedicación por estudiantes de la Universidad Técnica Nacional (UTN), como parte de un esfuerzo conjunto por aplicar la tecnología en beneficio de nuestra gente.

            Gracias por ser parte de Tenori.
            ¡Entre todos construimos un mejor Cañas!
        """.trimIndent()

        btnCerrar.setOnClickListener {
            finish()
        }

        iconoComercio.setImageResource(R.drawable.ic_default)
    }
}