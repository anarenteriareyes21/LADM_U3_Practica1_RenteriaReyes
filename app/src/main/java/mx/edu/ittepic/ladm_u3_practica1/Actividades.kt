package mx.edu.ittepic.ladm_u3_practica1

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteException

class Actividades(descri: String, fcaptura:String,fentrega:String){
    //-------- CLASE PARA MANIPULAR LA DATA -----//
    var descripcion = descri
    var fechaCaptura = fcaptura
    var fechaEntrega = fentrega
    var idActividad = 0
    var error = -1
    /*
    * VALORES DE ERROR
    * __________________________
    * 1-> Error en la conexion
    *
    * */
    val nombreBaseDatos = "bdtrabajos"
    var puntero : Context ?= null

    fun asignarPuntero(p:Context){
        puntero = p
    }

    /*--------------------------- INSERTAR DATA ----------------------------*/
    fun insertar():Boolean{
        error = -1
        try {
            var base =BaseDatos(puntero!!,nombreBaseDatos,null,1)
            var insertar = base.writableDatabase
            var datos = ContentValues()
            datos.put("DESCRIPCION",descripcion)
            datos.put("FECHACAPTURA",fechaCaptura)
            datos.put("FECHAENTREGA",fechaEntrega)
            var respuesta = insertar.insert("ACTIVIDADES","ID_ACTIVIDAD",datos)
            if(respuesta.toInt() == -1){
                //ERROR AL INSERTAR
                error = 2
                return false
            }
        }catch (e:SQLiteException){
            //Error en la conexion
            error = 1
            return false
        }

        return true
    }
    /*--------------------------- MOSTRAR TODAS LAS ACTIVIDADES-----------------------*/

    fun mostrarTodos(): ArrayList<Actividades> {
        var data = ArrayList<Actividades>()
        error = -1
        try {
            var base = BaseDatos(puntero!!, nombreBaseDatos, null, 1)
            var select = base.readableDatabase
            var columnas = arrayOf("*")

            var cursor = select.query("ACTIVIDADES", columnas, null, null, null, null, null)
            if (cursor.moveToFirst()) {
                do {
                    var actividadTemporal =
                        Actividades(cursor.getString(1), cursor.getString(2), cursor.getString(3)
                        )
                    actividadTemporal.idActividad = cursor.getInt(0)
                    data.add(actividadTemporal)
                } while (cursor.moveToNext())

            } else {
                error = 3
            }

        } catch (e: SQLiteException) {
            error = 1
        }//catch

        return data
    }//mostrarTodos

    /*------------------------------------ MOSTRAR POR FECHA DE CAPTURA--------------------------------------*/

    fun mostrarFechaCaptura(fechaC : String): ArrayList<Actividades> {
        var data = ArrayList<Actividades>()
        error = -1
        try {
            var base = BaseDatos(puntero!!, nombreBaseDatos, null, 1)
            var select = base.readableDatabase
            var columnas = arrayOf("*")
            var fechaBuscar = arrayOf(fechaC)
            var cursor = select.query("ACTIVIDADES", columnas, "FECHACAPTURA = ?", fechaBuscar, null, null, null)
            if (cursor.moveToFirst()) {
                do {
                    var actividadTemporal =
                        Actividades(cursor.getString(1), cursor.getString(2), cursor.getString(3)
                        )
                    actividadTemporal.idActividad = cursor.getInt(0)
                    data.add(actividadTemporal)
                } while (cursor.moveToNext())

            } else {
                error = 3
            }

        } catch (e: SQLiteException) {
            error = 1
        }//catch

        return data
    }//mostrarFechaCaptura


    /*------------------------------------ MOSTRAR POR FECHA DE ENTREGA--------------------------------------*/

    fun mostrarFechaEntrega(fechaE : String): ArrayList<Actividades> {
        var data = ArrayList<Actividades>()
        error = -1
        try {
            var base = BaseDatos(puntero!!, nombreBaseDatos, null, 1)
            var select = base.readableDatabase
            var columnas = arrayOf("*")
            var fechaBuscar = arrayOf(fechaE)
            var cursor = select.query("ACTIVIDADES", columnas, "FECHAENTREGA = ?", fechaBuscar, null, null, null)
            if (cursor.moveToFirst()) {
                do {
                    var actividadTemporal =
                        Actividades(cursor.getString(1), cursor.getString(2), cursor.getString(3)
                        )
                    actividadTemporal.idActividad = cursor.getInt(0)
                    data.add(actividadTemporal)
                } while (cursor.moveToNext())

            } else {
                error = 3
            }

        } catch (e: SQLiteException) {
            error = 1
        }//catch

        return data
    }//mostrarFechaEntrega

    /*------------------------------------ MOSTRAR POR ID--------------------------------------*/

    fun mostrarID(fechaE : String): ArrayList<Actividades> {
        var data = ArrayList<Actividades>()
        error = -1
        try {
            var base = BaseDatos(puntero!!, nombreBaseDatos, null, 1)
            var select = base.readableDatabase
            var columnas = arrayOf("*")
            var fechaBuscar = arrayOf(fechaE)
            var cursor = select.query("ACTIVIDADES", columnas, "ID_ACTIVIDAD = ?", fechaBuscar, null, null, null)
            if (cursor.moveToFirst()) {
                do {
                    var actividadTemporal =
                        Actividades(cursor.getString(1), cursor.getString(2), cursor.getString(3)
                        )
                    actividadTemporal.idActividad = cursor.getInt(0)
                    data.add(actividadTemporal)
                } while (cursor.moveToNext())

            } else {
                error = 3
            }

        } catch (e: SQLiteException) {
            error = 1
        }//catch

        return data
    }//mostrarPorID


    /*----------------------------------------------- MOSTRAR POR ID----------------------------------------*/
    fun buscarPorID(id:String):Actividades{
        var trabajaEncontrado = Actividades("-1","-1","")
        error= -1
        try {
            var base = BaseDatos(puntero!!,nombreBaseDatos,null,1)
            var select = base.readableDatabase
            var columnas = arrayOf("*")
            var idBuscar = arrayOf(id)
            var cursor = select.query("ACTIVIDADES",columnas,"ID_ACTIVIDAD = ?",idBuscar,null,null,null)
            if(cursor.moveToFirst()){
                trabajaEncontrado.idActividad = id.toInt()
                trabajaEncontrado.descripcion = cursor.getString(1)
                trabajaEncontrado.fechaCaptura = cursor.getString(2)
                trabajaEncontrado.fechaEntrega = cursor.getString(3)
            }else{
                //ERROR NO SE ENCONTRO EL ID (TRABAJADOR)
                error = 4
            }

        }catch (e: SQLiteException){
            //ERROR EN LA CONEXION
            error = 1
        }
        return trabajaEncontrado
    }//buscar


    /*--------------------------- ELIMINAR ACTIVIDAD---------------------------------------*/
    fun eliminarActividad(idA : String):Boolean{
        error = -1
        try {
            var base = BaseDatos(puntero!!,nombreBaseDatos,null,1)
            var eliminar = base.writableDatabase
            var idEliminar = arrayOf(idA.toString())

            //// delete = tabla, clausula where, variable que llena los signos de interrogacion
            var respuesta = eliminar.delete("ACTIVIDADES","ID_ACTIVIDAD = ?",idEliminar)
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