package com.example.pharmacyapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class admincategoriesAdapter(
    val data: ArrayList<Categorie>,
    val itemClickListener: OnItemClickListener
) : RecyclerView.Adapter<admincategoriesAdapter.MyViewHolder>() {

    // Interface for item clicks
    interface OnItemClickListener {
        fun onItemClick(categorie: Categorie)
    }

    class MyViewHolder(val row: View) : RecyclerView.ViewHolder(row) {
        var img = row.findViewById<ImageView>(R.id.icon_cat)
        val name = row.findViewById<TextView>(R.id.nom_cat)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layout = LayoutInflater.from(parent.context)
            .inflate(R.layout.category_admin_layout, parent, false)
        return MyViewHolder(layout)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val categorie = data[position]
        holder.img.setImageResource(categorie.pic)
        holder.name.text = categorie.title

        // Handle item click
        holder.row.setOnClickListener {
            itemClickListener.onItemClick(categorie)
        }
    }

    override fun getItemCount(): Int = data.size
}