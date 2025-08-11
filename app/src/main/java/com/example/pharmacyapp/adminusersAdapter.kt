package com.example.pharmacyapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.constraintlayout.widget.ConstraintLayout

class adminusersAdapter(private val userList: ArrayList<User>) :
    RecyclerView.Adapter<adminusersAdapter.UserViewHolder>() {

    // ViewHolder pour contenir les éléments du layout
    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val userName: TextView = itemView.findViewById(R.id.userName)
        val consInfos: ConstraintLayout = itemView.findViewById(R.id.ConsInfos)
        val nameTxt: TextView = itemView.findViewById(R.id.nameTxt)
        val emailTxt: TextView = itemView.findViewById(R.id.emailTxt)
        val locationTxt: TextView = itemView.findViewById(R.id.locationTxt)
        val phoneTxt: TextView = itemView.findViewById(R.id.phoneTxt)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.user_with_information_layout, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = userList[position]

        // Affiche uniquement le nom dans la liste principale
        holder.userName.text = "${user.first_name} ${user.last_name}"

        // Définit les informations supplémentaires
        holder.nameTxt.text = "${user.first_name} ${user.last_name}"
        holder.emailTxt.text = user.email
        holder.locationTxt.text = user.location
        holder.phoneTxt.text = user.phone.toString()

        // Ajout de l'événement de clic pour afficher/masquer les informations
        holder.itemView.setOnClickListener {
            if (holder.consInfos.visibility == View.GONE) {
                holder.consInfos.visibility = View.VISIBLE
            } else {
                holder.consInfos.visibility = View.GONE
            }
        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }
}


