package com.example.pharmacyapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pharmacyapp.categorieAdapter.OnItemClickListener

class medicamentAdapter(
    val originalData: ArrayList<Medicament>,
    val itemClickListener: OnItemClickListener
) : RecyclerView.Adapter<medicamentAdapter.MyViewHolder>() {

    private var filteredData: ArrayList<Medicament> = ArrayList(originalData)

    // Interface for item clicks
    interface OnItemClickListener {
        fun onItemClick(medicament: Medicament)
    }

    class MyViewHolder(val row: View) : RecyclerView.ViewHolder(row) {
        var img = row.findViewById<ImageView>(R.id.medicament_pic)
        val name = row.findViewById<TextView>(R.id.medicament_title)
        val desc = row.findViewById<TextView>(R.id.medicament_desc)
        val price = row.findViewById<TextView>(R.id.medicament_price)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layout =
            LayoutInflater.from(parent.context).inflate(R.layout.medicamentslist_layout, parent, false)
        return MyViewHolder(layout)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.itemView.clearAnimation() // Nettoyer toute animation précédente
        val animation = AnimationUtils.loadAnimation(holder.itemView.context, R.anim.animation1)
        holder.itemView.startAnimation(animation)

        val medicament = filteredData[position]
        holder.img.setImageResource(medicament.pic)
        holder.name.text = medicament.title
        holder.desc.text = medicament.mini_desc
        holder.price.text = "${medicament.price} DH"


        // Handle item click
        holder.row.setOnClickListener {
            itemClickListener.onItemClick(medicament)
        }


    }

    override fun getItemCount(): Int = filteredData.size


    // Méthode pour filtrer les données
    fun filter(query: String) {
        filteredData = if (query.isEmpty()) {
            ArrayList(originalData)
        } else {
            originalData.filter { medicament ->
                medicament.title.contains(query, ignoreCase = true) ||
                        medicament.desc.contains(query, ignoreCase = true)
            } as ArrayList<Medicament>
        }
        notifyDataSetChanged()
    }
}
