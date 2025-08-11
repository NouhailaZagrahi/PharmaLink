package com.example.pharmacyapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class orderitemsAdapter(
    val data: ArrayList<OrderItem>,
) : RecyclerView.Adapter<orderitemsAdapter.MyViewHolder>() {


    class MyViewHolder(val row: View) : RecyclerView.ViewHolder(row) {
        val image_item = row.findViewById<ImageView>(R.id.imageitem)
        var name_item = row.findViewById<TextView>(R.id.nameitem)
        //var categorie_item = row.findViewById<TextView>(R.id.categorieitem)
        var prix_item = row.findViewById<TextView>(R.id.prixitem)
        var quantity_item = row.findViewById<TextView>(R.id.quantityitem)
        var prix_total_item = row.findViewById<TextView>(R.id.prix_total_item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layout = LayoutInflater.from(parent.context)
            .inflate(R.layout.order_medicament_layout, parent, false)
        return MyViewHolder(layout)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val orderitem = data[position]

        holder.image_item.setImageResource(orderitem.pic)
        holder.name_item.text = orderitem.name
        //holder.categorie_item.text = orderitem.categorie
        holder.prix_item.text = orderitem.prix.toString()
        holder.quantity_item.text = orderitem.quantite.toString()
        var result = orderitem.prix * orderitem.quantite
        holder.prix_total_item.text = result.toString()

    }

    override fun getItemCount(): Int = data.size
}