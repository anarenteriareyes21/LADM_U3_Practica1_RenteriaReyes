package mx.edu.ittepic.ladm_u3_practica1

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*
import mx.edu.ittepic.ladm_u3_practica1.Utils.Utils

class MainActivity : AppCompatActivity() {
    val SELECT_PHOTO=2222
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*-------------------------- ELEGIR IMAGEN DE GALERIA DEL TELEFONO ------------------------*/
        btnElegirImagen.setOnClickListener {
            val photoPicker = Intent(Intent.ACTION_PICK)
            photoPicker.type = "image/*"
            startActivityForResult(photoPicker, SELECT_PHOTO)
        }

        /*---------------------- INSERTAR DATA (ACTIVIDAD)----------------------------------------*/
        btnInsertarEvidencia.setOnClickListener {
         if(editIdAct.text.isEmpty()) {
            dialogo("ERROR, escriba el ID de la actividad")
         }else {
             val bitmap = (imagen_select.drawable as BitmapDrawable).bitmap
             var evidencia = Evidencias(editIdAct.text.toString().toInt(), Utils.getBytes(bitmap))
             evidencia.asignarPuntero(this)
             var resultado = evidencia.insertarImagen()
             if (resultado == true) {
                 mensaje("SE GUARDÓ LA EVIDENCIA")
             } else {
                 when (evidencia.error){
                     1->{dialogo("error en tabla, no se creó o no se conectó a la base de datos")}
                     2->{dialogo("error no se pudo insertar en la tabla")}
                 }
             }
         }

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
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
            super.onActivityResult(requestCode, resultCode, data)
            if(requestCode==SELECT_PHOTO && resultCode==Activity.RESULT_OK && data!=null){
                val picketImage=data.data
                imagen_select.setImageURI(picketImage)// asignar imagen a imageView
            }//if
        }//onActivity



}
