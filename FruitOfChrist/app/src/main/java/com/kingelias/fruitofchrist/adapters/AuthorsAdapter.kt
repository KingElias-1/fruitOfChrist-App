package com.kingelias.fruitofchrist.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.recyclerview.widget.RecyclerView
import com.kingelias.fruitofchrist.R
import com.kingelias.fruitofchrist.data.Author
import com.kingelias.fruitofchrist.fragments.AuthorsFragment

class AuthorsAdapter: RecyclerView.Adapter<AuthorsAdapter.ViewHolder>() {
    private var authors = mutableListOf<Author>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.author_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val author = authors[position]
        holder.nameTV.text = author.name
    }

    override fun getItemCount(): Int {
        return authors.size
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val nameTV: TextView = itemView.findViewById(R.id.nameTV)
    }

    fun setAuthors(authors: List<Author>){
        this.authors = authors as MutableList<Author>
        notifyDataSetChanged()
    }

    fun addAuthor(author: Author){
        if(authors.contains(author) && authors[authors.indexOf(author)].name != author.name) {
            val index = authors.indexOf(author)
            this.authors.removeAt(index)
            this.authors.add(index, author)
            notifyDataSetChanged()
        }else if(!authors.contains(author)){
            this.authors.add(author)
            notifyDataSetChanged()
        }
    }
}