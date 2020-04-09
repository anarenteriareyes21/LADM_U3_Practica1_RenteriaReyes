package mx.edu.ittepic.ladm_u3_practica1

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteQueryBuilder
import android.graphics.Bitmap
import android.media.Image
import mx.edu.ittepic.ladm_u3_practica1.Utils.Utils

class Evidencias (idAct:Int,fot: ByteArray?){
    //-------------------- CLASE PARA MANIPULAR LA DATA -----//
    var foto = fot
    var idActividad = idAct
    var idEvidencia = 0
    var error = -1
    var listaID = mutableListOf<String>()


    /*
    * VALORES DE ERROR
    * ___________________________
    * 1 -> Error con la conexion
    *
    *
    * */
    val nombreBaseDatos = "bdactividades"
    var puntero : Context?= null

    fun asignarPuntero(p: Context){
        puntero = p
    }

    fun insertarImagen(): Boolean{
        try {
            var base =BaseDatos(puntero!!,nombreBaseDatos,null,1)
            var insertar = base.writableDatabase
            var datos = ContentValues()
            datos.put("ID_ACTIVIDAD",idActividad)
            datos.put("FOTO",foto)
            var respuesta = insertar.insert("EVIDENCIAS","IDEVIDENCIA",datos)
            if(respuesta.toInt() == -1){
                //ERROR AL INSERTAR
                error = 2
                return false
            }
        }catch (e: SQLiteException){
            //Error en la conexion
            error = 1
            return false
        }

        return true
    }

    fun buscarImagen(id:Int):ByteArray?{
        var base =BaseDatos(puntero!!,nombreBaseDatos,null,1)
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

    fun eliminarEvidencia(idE :String):Boolean{
        error = -1
        try {
            var base = BaseDatos(puntero!!,nombreBaseDatos,null,1)
            var eliminar = base.writableDatabase
            var idEliminar = arrayOf(idE.toString())

            //// delete = tabla, clausula where, variable que llena los signos de interrogacion
            var respuesta = eliminar.delete("EVIDENCIAS","IDEVIDENCIA = ?",idEliminar)
            if(respuesta.toInt() == 0){
                //ERROR AL ELIMINAR
                error = 6
                return false
            }
        }catch (e:SQLiteException){
            //ERROR EN LA CONEXION
            error = 1
            return false
        }

        return true
    }
}//class