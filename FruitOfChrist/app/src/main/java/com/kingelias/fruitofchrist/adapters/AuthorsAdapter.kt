package com.kingelias.fruitofchrist.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kingelias.fruitofchrist.R
import com.kingelias.fruitofchrist.data.Author
import com.kingelias.fruitofchrist.fragments.AuthorsFragment

class AuthorsAdapter(private val context: AuthorsFragment): RecyclerView.Adapter<AuthorsAdapter.ViewHolder>() {
    private var authors = mutableListOf<Author>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.author_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val author = authors[position]
        holder.nameTV.text = author.name

        holder.editBn.setOnClickListener{
            context.editAuthor(author)
        }

        holder.deleteBn.setOnClickListener{
            context.deleteAuthor(author)
        }
    }

    override fun getItemCount(): Int {
        return authors.size
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val nameTV: TextView = itemView.findViewById(R.id.nameTV)
        val editBn: ImageButton = itemView.findViewById(R.id.editIBn)
        val deleteBn: ImageButton = itemView.findViewById(R.id.deleteIBn)
    }

    fun setAuthors(authors: List<Author>){
        this.authors = authors as MutableList<Author>
        notifyDataSetChanged()
    }

    fun addAuthor(author: Author){
        if(!authors.contains(author)){
            this.authors.add(author)
        }else if(author.isDeleted){
            val index = authors.indexOf(author)
            authors.removeAt(index)
        }else{
            val index = authors.indexOf(author)
            authors[index] = author
        }
        notifyDataSetChanged()
    }
}