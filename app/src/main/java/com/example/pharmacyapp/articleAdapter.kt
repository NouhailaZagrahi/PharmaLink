package com.example.pharmacyapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class articleAdapter(
    val data: ArrayList<Article>,
    val listener: OnReadMoreClickListener
) : RecyclerView.Adapter<articleAdapter.MyViewHolder>() {

    interface OnReadMoreClickListener {
        fun onReadMoreClick(article: Article)
    }

    class MyViewHolder(val row: View) : RecyclerView.ViewHolder(row) {
        var img = row.findViewById<ImageView>(R.id.artPic)
        val name = row.findViewById<TextView>(R.id.artTitle)
        val readMore = row.findViewById<TextView>(R.id.read_more) // Add this
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.article_layout, parent, false)
        return MyViewHolder(layout)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val article = data[position]
        holder.img.setImageResource(article.pic)
        holder.name.text = article.title

        // Set click listener for "Read More"
        holder.readMore.setOnClickListener {
            listener.onReadMoreClick(article)
        }
    }

    override fun getItemCount(): Int = data.size
}
