package com.example.pharmacyapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class adminmedicinesAdapter(
    val data: ArrayList<Medicament>,
    val listener: OnChangeClickListener
) : RecyclerView.Adapter<adminmedicinesAdapter.MyViewHolder>() {

    interface OnChangeClickListener {
        fun onChangeClick(medicament: Medicament)
    }

    class MyViewHolder(val row: View) : RecyclerView.ViewHolder(row) {
        var img = row.findViewById<ImageView>(R.id.medicament_pic)
        val name = row.findViewById<TextView>(R.id.medicament_title)
        val prix = row.findViewById<EditText>(R.id.editPrix)
        val change = row.findViewById<TextView>(R.id.change_btn) // Add this

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layout =
            LayoutInflater.from(parent.context).inflate(R.layout.change_price_layout, parent, false)
        return MyViewHolder(layout)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val medicament = data[position]
        holder.img.setImageResource(medicament.pic)
        holder.name.text = medicament.title
        holder.prix.setText(medicament.price.toString())


        holder.change.setOnClickListener {
            val newPrice = holder.prix.text.toString().toInt()
            if (newPrice != null) {
                medicament.price = newPrice
                listener.onChangeClick(medicament)
            } else {
                holder.prix.error = "Prix invalide"
            }
        }

    }

    override fun getItemCount(): Int = data.size

}