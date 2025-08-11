package com.example.pharmacyapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class orderAdapter(
    val data: ArrayList<Order>,
    val itemClickListener: OnItemClickListener
) : RecyclerView.Adapter<orderAdapter.MyViewHolder>() {

    // Interface for item clicks
    interface OnItemClickListener {
        fun onItemClick(order: Order)
    }

    class MyViewHolder(val row: View) : RecyclerView.ViewHolder(row) {
        val numOrder = row.findViewById<TextView>(R.id.num_order)
        var prixtot = row.findViewById<TextView>(R.id.prixtot)
        var date = row.findViewById<TextView>(R.id.date_order)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layout = LayoutInflater.from(parent.context)
            .inflate(R.layout.order_layout1, parent, false)
        return MyViewHolder(layout)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val order = data[position]

        holder.numOrder.text = order.idOrder.toString()
        holder.prixtot.text = "${order.prix_total} DH"
        holder.date.text = convertirTimestampEnDate(order.dateOrder)

        // Handle item click
        holder.row.setOnClickListener {
            itemClickListener.onItemClick(order)
        }
    }

    override fun getItemCount(): Int = data.size

    // Fonction de conversion du timestamp en date lisible
    private fun convertirTimestampEnDate(timestamp: Long): String {
        val date = Date(timestamp)
        val format = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
        return format.format(date)
    }
}