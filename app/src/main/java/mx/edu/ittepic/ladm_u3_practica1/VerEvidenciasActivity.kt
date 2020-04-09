package mx.edu.ittepic.ladm_u3_practica1

import android.content.Intent
import android.database.sqlite.SQLiteException
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_buscar.*
import kotlinx.android.synthetic.main.activity_ver_evidencias.*
import mx.edu.ittepic.ladm_u3_practica1.Utils.Utils
import java.lang.Exception

class VerEvidenciasActivity : AppCompatActivity() {
    val nombreBaseDatos = "bdactividades"
    var listaIDEvidencias = ArrayList<String>()
    var id = 0
    var idActActual = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_evidencias)
        /*--------------- RECUPERAR DATA----------------*/
        var extras = intent.extras
        id = extras?.getInt("ID_ACTIVIDAD")!!.toInt()
        idActActual = id
        //mensaje("IDCARGADO:${id}")
        /*----------------------------------------------*/
        cargarEvidencias(id.toString())
        btnEliminarActividad.setOnClickListener {
            if(listaIDEvidencias.size != 0){
                dialogo("Error, no se puede eliminar la actividad, elimine sus evidencias primero")
            }else{
                eliminarActividadV(id.toString())
                finish()

            }
        }
    }

    /*------------------------------------- OBETENER DATA DE LA BD------------------------*/
    fun cargarEvidencias(id: String) {
        listaIDEvidencias = ArrayList<String>()
        try {
            var base = BaseDatos(this, nombreBaseDatos, null, 1)
            var buscar = base.readableDatabase
            var SQL = "SELECT * FROM EVIDENCIAS WHERE ID_ACTIVIDAD = ?"
            var parametros = arrayOf(id)
            var cursor = buscar.rawQuery(SQL, parametros)
            /*----------------- REINICIAR VARIABLES--------------*/
            if(cursor.count == 0){
                var idEv= ArrayList<String>()
                var idAct= ArrayList<String>()
                var arrayImg = ArrayList<Bitmap>()
                val array = arrayOfNulls<Bitmap>(arrayImg.size)
                var arrayid= arrayOfNulls<String>(idEv.size)
                var arrayidAct= arrayOfNulls<String>(idAct.size)
                val adapter =  EvidenciaAdapter(this, idEv.toArray(arrayid),
                    idAct.toArray(arrayidAct),
                    arrayImg.toArray(array))
                listaEvidencias.adapter = adapter
            }
            /*-----------------------------------------------------*/
            if (cursor.count > 0) {
                var arrayImg = ArrayList<Bitmap>()
                this.listaIDEvidencias = ArrayList<String>()
                var idEv= ArrayList<String>()
                var idAct= ArrayList<String>()
                cursor.moveToFirst()
                var cantidad = cursor.count - 1
                (0..cantidad).forEach {
                    arrayImg.add(Utils.getImage(buscarImagen(cursor.getString(0).toInt())!!))
                    listaIDEvidencias.add(cursor.getString(0  ))
                    idEv.add(cursor.getString(0))
                    idAct.add(cursor.getString(1))
                    cursor.moveToNext()
                }
                val array = arrayOfNulls<Bitmap>(arrayImg.size)
                var arrayid= arrayOfNulls<String>(idEv.size)
                var arrayidAct= arrayOfNulls<String>(idAct.size)
                mensaje("ENTRO AQUI")
                val adapter =  EvidenciaAdapter(this, idEv.toArray(arrayid),
                    idAct.toArray(arrayidAct),
                    arrayImg.toArray(array))
                    listaEvidencias.adapter = adapter
                /*------------------------CLIC EN ITEM-------------------------------*/
                listaEvidencias.setOnItemClickListener { parent, view, position, id ->
                    AlertDialog.Builder(this)
                        .setTitle("¿Deseas eliminar la evidencia?")
                        .setPositiveButton("Eliminar") { d, i ->
                            eliminarEvidenciaV(listaIDEvidencias[position])
                            cargarEvidencias(idActActual.toString())
                        }
                        .setNeutralButton("Cancelar"){d,i->}
                        .show()
                }
            } else {
                if(cursor.count == 0){
                    mensaje("NO HAY  EVIDENCIAS")
                }

            }
            buscar.close()
            base.close()
        } catch (error: SQLiteException) {
            mensaje(error.message.toString())
        }
    }


    fun buscarImagen(id:Int):ByteArray?{
        var base =BaseDatos(this,nombreBaseDatos,null,1)
        var buscar = base.readableDatabase
        var columnas = arrayOf("FOTO") // * = todas las columnas
        var cursor = buscar.query("EVIDENCIAS",columnas,"IDEVIDENCIA = ?",arrayOf(id.toString()),null,null,null)
        var result:ByteArray?=null
        if (cursor.moveToFirst()){
            do{
                result=cursor.getBlob(cursor.getColumnIndex("FOTO"))
            }while (cursor.moveToNext())
        }//if
        return result
    }

    /*---------------------------------- ELIMINAR ACTIVIDAD-------------------------------*/
    fun eliminarActividadV(idAc : String){
       var actividad = Actividades("","","")
        actividad.idActividad = idActActual.toInt()
        actividad.asignarPuntero(this)
        if (actividad.eliminarActividad(idAc.toString())){
            dialogo("SE ELIMINO CORRECTAMENTE")
        }else{
            dialogo("NO SE PUDO ELIMINAR")
        }

    }
    /*---------------------------------- ELIMINAR EVIDENCIA-------------------------------*/
    fun eliminarEvidenciaV(idEvi : String){

        var result:ByteArray?=null
        var x = 0
        var evidencia = Evidencias(x,result)
        evidencia!!.asignarPuntero(this)
        evidencia.idEvidencia = idActActual.toInt()
        if (evidencia.eliminarEvidencia(idEvi.toString())){
            dialogo("SE ELIMINO CORRECTAMENTE")

        }else{
            dialogo("NO SE PUDO ELIMINAR")
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
