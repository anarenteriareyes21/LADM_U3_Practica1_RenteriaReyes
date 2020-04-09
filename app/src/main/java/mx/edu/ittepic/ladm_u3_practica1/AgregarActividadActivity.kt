package mx.edu.ittepic.ladm_u3_practica1

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_agregar_actividad.*
import java.util.*

class AgregarActividadActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_actividad)
        /*-------------------------- SELECCIONAR FECHAS ---------------------*/
        editFechaCaptura.setOnClickListener{
            var calendario = Calendar.getInstance()
            var year = calendario.get(Calendar.YEAR)
            var month = calendario.get(Calendar.MONTH)
            var day = calendario.get(Calendar.DAY_OF_MONTH)

            var datePicker = DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener { view, myear, mmonth, mdayOfMonth ->
                    //setToTextView
                    var fecha = "${myear}/${mmonth+1}/${mdayOfMonth}"
                    editFechaCaptura.setText(fecha)
                    fecha=""
                },
                year,
                month,
                day
            )
            //showDialog
            datePicker.show()
        }
        editFechaEntrega.setOnClickListener {
            var calendario = Calendar.getInstance()
            var year = calendario.get(Calendar.YEAR)
            var month = calendario.get(Calendar.MONTH)
            var day = calendario.get(Calendar.DAY_OF_MONTH)

            var datePicker = DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener { view, myear, mmonth, mdayOfMonth ->
                    //setToTextView
                    var fecha = "${myear}/${mmonth+1}/${mdayOfMonth}"
                    editFechaEntrega.setText(fecha)
                    fecha=""
                },
                year,
                month,
                day
            )
            datePicker.show()
        }

        /*----------------------- INSERTAR DATA -----------------------------*/
        btnInsertarActividad.setOnClickListener {
            if(editDescri.text.isEmpty() || editFechaCaptura.text.isEmpty() || editFechaEntrega.text.isEmpty()){
                dialogo("Los campos no pueden estar vacios")
            }else{
                var actividad = Actividades(editDescri.text.toString(),
                    editFechaCaptura.text.toString(),
                    editFechaEntrega.text.toString())
                actividad.asignarPuntero(this)
                //insertar datos:
                var resultado = actividad.insertar()
                if(resultado == true){
                    mensaje("SE CAPTURÓ ACTIVIDAD")
                    editDescri.setText("")
                    editFechaCaptura.setText("")
                    editFechaEntrega.setText("")
                }else{
                    when (actividad.error){
                        1->{dialogo("error en tabla, no se creo o no se conecto a la base de datos")}
                        2->{dialogo("error no se pudo insertar en la tabla")}
                    }
                }
            }
        }
        /*------------------------ AGREGAR EVIDENCIA ----------------------*/
        btnAgregarEvidencia.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
        }

    }
    /*-------------------------------- METODOS EXTRAS----------------------------------*/
    fun mensaje(s:String){
        Toast.makeText(this,s, Toast.LENGTH_LONG).show()
    }
    fun dialogo(s:String){
        AlertDialog.Builder(this).setTitle("ATENCION").setMessage(s)
            .setPositiveButton("OK"){d,i->}
            .show()
    }
}//class
