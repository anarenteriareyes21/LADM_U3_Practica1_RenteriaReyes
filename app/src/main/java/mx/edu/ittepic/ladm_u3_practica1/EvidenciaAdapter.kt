package mx.edu.ittepic.ladm_u3_practica1

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main2.*
import kotlinx.android.synthetic.main.activity_ver_evidencias.view.*
import kotlinx.android.synthetic.main.list_custom.view.*
import mx.edu.ittepic.ladm_u3_practica1.Utils.Utils

class EvidenciaAdapter(private val context: Activity, private val idEv : Array<String>,private val description : Array<String>, private val listimg: Array<Bitmap>)
    : ArrayAdapter<String>(context,R.layout.list_custom,idEv){
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val layout = inflater.inflate(R.layout.list_custom, null, true)

        val imageView = layout.findViewById(R.id.imagenEvidencia) as ImageView
        val evidenciaText = layout.findViewById(R.id.evidenciaID) as TextView
        val subtitleText = layout.findViewById(R.id.descri) as TextView

        evidenciaText.text = "ID de evidencia: "+idEv[position]
        subtitleText.text = "ID de actividad: "+description[position]
        imageView.setImageBitmap(listimg[position])

        return layout
    }





    fun mensaje(s:String){
        Toast.makeText(context,s, Toast.LENGTH_LONG).show()
    }

}//class