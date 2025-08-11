package com.example.pharmacyapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class adminarticlesAdapter(
    val data: ArrayList<Article>,
    val itemClickListener: OnItemClickListener,
    val getCommentsByArticleId: (Int) -> ArrayList<Comment>
) : RecyclerView.Adapter<adminarticlesAdapter.MyViewHolder>() {

    private var selectedPosition = -1 // Position de l'article actuellement sélectionné

    interface OnItemClickListener {
        fun onItemClick(article: Article)
    }

    class MyViewHolder(val row: View) : RecyclerView.ViewHolder(row) {
        val img = row.findViewById<ImageView>(R.id.art_image)
        val name = row.findViewById<TextView>(R.id.art_title)
        val commentsRv = row.findViewById<RecyclerView>(R.id.commentsRv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layout = LayoutInflater.from(parent.context)
            .inflate(R.layout.article_with_comment_layout, parent, false)
        return MyViewHolder(layout)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val article = data[position]

        // Afficher les informations de l'article
        holder.img.setImageResource(article.pic)
        holder.name.text = article.title

        // Vérifier si cet article est sélectionné
        if (position == selectedPosition) {
            // Récupérer les commentaires associés à l'article
            val comments = getCommentsByArticleId(article.id)
            if (comments.isNotEmpty()) {
                val commentAdapter = CommentAdapter(comments)
                holder.commentsRv.adapter = commentAdapter
                holder.commentsRv.layoutManager = LinearLayoutManager(holder.row.context)
                holder.commentsRv.visibility = View.VISIBLE
            } else {
                holder.commentsRv.visibility = View.GONE
            }
        } else {
            // Cacher la RecyclerView des commentaires pour les autres articles
            holder.commentsRv.visibility = View.GONE
        }

        // Gérer le clic sur un article
        holder.row.setOnClickListener {
            // Mettre à jour la position sélectionnée
            val previousPosition = selectedPosition
            selectedPosition = if (selectedPosition == position) -1 else position

            // Notifier les changements pour actualiser les vues
            notifyItemChanged(previousPosition)
            notifyItemChanged(selectedPosition)

            // Informer l'écouteur du clic
            itemClickListener.onItemClick(article)
        }
    }

    override fun getItemCount(): Int = data.size
}


