package com.example.pharmacyapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class cartAdapter(
    private val data: ArrayList<Cart>,
    private val onCartUpdated: (List<Cart>) -> Unit
) : RecyclerView.Adapter<cartAdapter.MyViewHolder>() {

    class MyViewHolder(val row: View) : RecyclerView.ViewHolder(row) {
        var img: ImageView = row.findViewById(R.id.cart_pic)
        val name: TextView = row.findViewById(R.id.cart_title)
        val total: TextView = row.findViewById(R.id.cart_total)
        val quantite: TextView = row.findViewById(R.id.cart_quantite)
        val trashBtn: View = row.findViewById(R.id.trash_btn)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.cartlist_layout, parent, false)
        return MyViewHolder(layout)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = data[holder.bindingAdapterPosition]
        val dbHelper = DatabaseHelper(holder.row.context)

        holder.img.setImageResource(item.medicImage)
        holder.name.text = item.medicTitle
        holder.total.text = "${item.quantity * item.priceUnite} DH"
        holder.quantite.text = item.quantity.toString()

        // Augmenter la quantité
        holder.row.findViewById<TextView>(R.id.plus_btn).setOnClickListener {
            item.quantity += 1
            dbHelper.updateCartQuantity(item.idCart, item.quantity)
            notifyItemChanged(holder.bindingAdapterPosition)
            onCartUpdated(data) // Mettre à jour les totaux
        }

        // Diminuer la quantité
        holder.row.findViewById<TextView>(R.id.minus_btn).setOnClickListener {
            if (item.quantity > 1) {
                item.quantity -= 1
                dbHelper.updateCartQuantity(item.idCart, item.quantity)
                notifyItemChanged(holder.bindingAdapterPosition)
                onCartUpdated(data)
            }
        }

        // Supprimer l'élément
        holder.trashBtn.setOnClickListener {
            val positionToRemove = holder.bindingAdapterPosition
            dbHelper.deleteCartItem(item.idCart)
            data.removeAt(positionToRemove)
            notifyItemRemoved(positionToRemove)
            notifyItemRangeChanged(positionToRemove, data.size)

            onCartUpdated(data)

            if (data.isEmpty()) {
                onCartUpdated(data) // Signaler que la liste est vide
            }
        }
    }

    override fun getItemCount(): Int = data.size
}
