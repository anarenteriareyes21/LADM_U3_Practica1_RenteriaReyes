package mx.edu.ittepic.ladm_u3_practica1

import android.app.DatePickerDialog
import android.content.Intent
import android.database.sqlite.SQLiteException
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_agregar_actividad.*
import kotlinx.android.synthetic.main.activity_buscar.*
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList

class BuscarActivity : AppCompatActivity() {
    var listaID = ArrayList<String>()
    /*------------------------ BANDERAS PARA RADIOBUTTONS------------------ */
    var bCaptura = false
    var bEntrega = false
    var bID = false
    var bTodos = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buscar)
        cargarListaActividades()
        radioGroup.setOnCheckedChangeListener { group, checkedId ->

            if (checkedId == R.id.radioEntrega && bEntrega == false){
                editBuscarPor.isEnabled = true
                bEntrega = true
            }

            if(checkedId == R.id.radioCaptura && bCaptura == false){
                editBuscarPor.isEnabled = true
                bCaptura = true

            }
            if(checkedId == R.id.radioID && bID == false){
                editBuscarPor.isEnabled = true
                bID = true
            }
            if(checkedId == R.id.radioTodos && bTodos == false){
                bTodos = true
                editBuscarPor.isEnabled = false
            }
        }

        btnBuscar.setOnClickListener {
            if(bCaptura == true ){
                cargarPorFechaCaptura()
                bCaptura = false
            }
            if (bEntrega == true){
                cargarPorFechaEntrega()
                bEntrega = false
            }
            if (bID == true){
                cargarPorID()
                bID = false
            }
            if (bTodos == true){
                cargarListaActividades()
                bTodos = false
            }
        }


    }

    /*------------------------------------ CARGAR LISTA DE TODAS LAS ACTIVIDADES-----------------*/
    fun cargarListaActividades() {
        try {
            var conexion = Actividades("", "", "")
            conexion.asignarPuntero(this)
            var data = conexion.mostrarTodos()
            if (data.size == 0) {
                if (conexion.error == 3) {
                    dialogo("Error no se encontraron datos")
                }//if
                return
            }//if
            var total = data.size - 1
            var vector = Array<String>(data.size, { "" })
            listaID = ArrayList<String>()
            (0..total).forEach{
                var actividades = data[it]
                var item = "ID: " + actividades.idActividad + "\nDescripción: " + actividades.descripcion + "\nFecha Captura:" + actividades.fechaCaptura + "\nFecha entrega:" + actividades.fechaEntrega
                vector[it] = item
                listaID.add(actividades.idActividad.toString())
            }//forEach
            lista.adapter =
                ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, vector)
            lista.setOnItemClickListener { parent, view, position, id ->
                var con = Actividades("", "", "")
                con.asignarPuntero(this)
                var actiEncontrada = con.buscarPorID(listaID[position])
                if (con.error == 4) {
                    dialogo("ERROR NO SE ENCONTRO EL ID")
                    return@setOnItemClickListener
                }//if
                AlertDialog.Builder(this)
                    .setTitle("¿Qué deseas hacer?")
                    .setMessage("ID: ${actiEncontrada.idActividad}\nDESCRIPCIÓN:  ${actiEncontrada.descripcion}\nFECHA CAPTURA: ${actiEncontrada.fechaCaptura}\nFECHA ENTREGA: ${actiEncontrada.fechaEntrega}")
                    .setPositiveButton("Ver evidencias") { d, i ->
                        cargarEnOtroActivity(actiEncontrada)}

                    .show()
            }//setOnItem

        } catch (e: Exception) {
            dialogo(e.message.toString())
        }
    }//cargarLista

    /*--------------------------------- MOSTRAR ACTIVIDADES POR FECHA DE CAPTURA------------------------------------------*/
    fun cargarPorFechaCaptura() {
        try {
            var conexion = Actividades("", "", "")
            conexion.asignarPuntero(this)
            var data = conexion.mostrarFechaCaptura(editBuscarPor.text.toString())

            if (data.size == 0) {
                if (conexion.error == 3) {
                    dialogo("Error no se encontraron datos")
                }//if
                return
            }//if
            var total = data.size - 1
            var vector = Array<String>(data.size, { "" })
            listaID = java.util.ArrayList<String>()
            (0..total).forEach{
                var actividades = data[it]
                var item = "ID: " + actividades.idActividad + "\nDescripción: " + actividades.descripcion + "\nFecha Captura:" + actividades.fechaCaptura + "\nFecha entrega:" + actividades.fechaEntrega
                vector[it] = item
                listaID.add(actividades.idActividad.toString())
            }//forEach
            lista.adapter =
                ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, vector)

            lista.setOnItemClickListener { parent, view, position, id ->
                var con = Actividades("", "", "")
                con.asignarPuntero(this)
                var actiEncontrada = con.buscarPorID(listaID[position])
                if (con.error == 4) {
                    dialogo("ERROR NO SE ENCONTRO EL ID")
                    return@setOnItemClickListener
                }//if
                AlertDialog.Builder(this)
                    .setTitle("¿Qué deseas hacer?")
                    .setMessage("ID: ${actiEncontrada.idActividad}\nDESCRIPCIÓN:  ${actiEncontrada.descripcion}\nFECHA CAPTURA: ${actiEncontrada.fechaCaptura}\nFECHA ENTREGA: ${actiEncontrada.fechaEntrega}")
                    .setPositiveButton("Ver evidencias") { d, i ->
                        cargarEnOtroActivity(actiEncontrada)
                    }
                    .show()
            }//setOnItem

        } catch (e: Exception) {
            dialogo(e.message.toString())
        }
    }//cargarLista

    /*--------------------------------------- MOSTRAR ACTIVIDADES POR FECHA DE ENTREGA --------------------------------*/
    fun cargarPorFechaEntrega() {
        try {
            var conexion = Actividades("", "", "")
            conexion.asignarPuntero(this)
            var data = conexion.mostrarFechaEntrega(editBuscarPor.text.toString())

            if (data.size == 0) {
                if (conexion.error == 3) {
                    dialogo("Error no se encontraron datos")
                }//if
                return
            }//if
            var total = data.size - 1
            var vector = Array<String>(data.size, { "" })
            listaID = java.util.ArrayList<String>()
            (0..total).forEach{
                var actividades = data[it]
                var item = "ID: " + actividades.idActividad + "\nDescripción: " + actividades.descripcion + "\nFecha Captura:" + actividades.fechaCaptura + "\nFecha entrega:" + actividades.fechaEntrega
                vector[it] = item
                listaID.add(actividades.idActividad.toString())
            }//forEach
            lista.adapter =
                ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, vector)

            lista.setOnItemClickListener { parent, view, position, id ->
                var con = Actividades("", "", "")
                con.asignarPuntero(this)
                var actiEncontrada = con.buscarPorID(listaID[position])
                if (con.error == 4) {
                    dialogo("ERROR NO SE ENCONTRO EL ID")
                    return@setOnItemClickListener
                }//if
                AlertDialog.Builder(this)
                    .setTitle("¿Qué deseas hacer?")
                    .setMessage("ID: ${actiEncontrada.idActividad}\nDESCRIPCIÓN:  ${actiEncontrada.descripcion}\nFECHA CAPTURA: ${actiEncontrada.fechaCaptura}\nFECHA ENTREGA: ${actiEncontrada.fechaEntrega}")
                    .setPositiveButton("Ver evidencias") { d, i ->
                        cargarEnOtroActivity(actiEncontrada)
                    }
                    .show()
            }//setOnItem

        } catch (e: Exception) {
            dialogo(e.message.toString())
        }
    }//cargarLista

    /*--------------------------------------- MOSTRAR ACTIVIDADES POR ID --------------------------------*/
    fun cargarPorID() {
        try {
            var conexion = Actividades("", "", "")
            conexion.asignarPuntero(this)
            var data = conexion.mostrarID(editBuscarPor.text.toString())

            if (data.size == 0) {
                if (conexion.error == 3) {
                    dialogo("Error no se encontraron datos")
                }//if
                return
            }//if
            var total = data.size - 1
            var vector = Array<String>(data.size, { "" })
            listaID = java.util.ArrayList<String>()
            (0..total).forEach{
                var actividades = data[it]
                var item = "ID: " + actividades.idActividad + "\nDESCRIPCIÓN: " + actividades.descripcion + "\nFECHA DE CAPTURA: " + actividades.fechaCaptura + "\nFEHCA DE ENTREGA: " + actividades.fechaEntrega
                vector[it] = item
                listaID.add(actividades.idActividad.toString())
            }//forEach
            lista.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, vector)

            lista.setOnItemClickListener { parent, view, position, id ->
                var con = Actividades("", "", "")
                con.asignarPuntero(this)
                var actiEncontrada = con.buscarPorID(listaID[position])
                if (con.error == 4) {
                    dialogo("ERROR NO SE ENCONTRO EL ID")
                    return@setOnItemClickListener
                }//if
                AlertDialog.Builder(this)
                    .setTitle("¿Qué deseas hacer?")
                    .setMessage("Id: ${actiEncontrada.idActividad}\nDescripción: ${actiEncontrada.descripcion}\nFecha de captura:${actiEncontrada.fechaCaptura}\nFecha de entrega:${actiEncontrada.fechaEntrega}")
                    .setPositiveButton("Ver evidencias") { d, i -> cargarEnOtroActivity(actiEncontrada) }
                    .show()
            }//setOnItem

        } catch (e: Exception) {
            dialogo(e.message.toString())
        }
    }//cargarLista

    /*------------------------------------ CARGAR DATOS A OTRO ACTIVITY-------------------------------*/
    fun cargarEnOtroActivity(a: Actividades){
        var verEvidencias = Intent(this,VerEvidenciasActivity :: class.java)

        verEvidencias.putExtra("ID_ACTIVIDAD",a.idActividad)

        startActivityForResult(verEvidencias,0)
    }//cargarEnOtroActivity

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        cargarListaActividades()
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
}
