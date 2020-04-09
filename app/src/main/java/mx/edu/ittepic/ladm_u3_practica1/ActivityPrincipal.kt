package mx.edu.ittepic.ladm_u3_practica1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_principal.*

class ActivityPrincipal : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_principal)

        btnMandarAgAct.setOnClickListener {
            //Madar al activity de Agregar Actividad
            startActivity(Intent(this,AgregarActividadActivity::class.java))
        }
        btnMandarBusAct.setOnClickListener{
            //Mandar al activity de Buscar para mostrar las actividades
            startActivity(Intent(this,BuscarActivity::class.java))
        }
    }
}
