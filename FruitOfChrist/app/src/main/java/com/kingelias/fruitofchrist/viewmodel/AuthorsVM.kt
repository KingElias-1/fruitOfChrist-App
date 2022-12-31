package com.kingelias.fruitofchrist.viewmodel

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.kingelias.fruitofchrist.data.Author
import com.kingelias.fruitofchrist.data.NODE_AUTHORS

class AuthorsVM(): ViewModel(){

    private val dbAuthors = FirebaseDatabase.getInstance().getReference(NODE_AUTHORS)

    private val _authors = MutableLiveData<List<Author>>()
    val authors: LiveData<List<Author>>
        get() = _authors

    private val _result = MutableLiveData<Exception?>()
    val result: LiveData<Exception?>
        get() = _result

    fun addAuthor(author: Author, fragment: Fragment){
        author.id = dbAuthors.push().key

        dbAuthors.child(author.id!!).setValue(author)
            .addOnCompleteListener {
                if (it.isSuccessful){
                    _result.value = null
                }
                else{
                    _result.value = it.exception
                }
            }
    }

    fun fetchAuthors(){
        dbAuthors.addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    //making a mutable list to collect the authors since the fi=unction us run asynchronously
                    val authors = mutableListOf<Author>()

                    for (authorSnapshot in snapshot.children){
                        //snapshot gets the value in the individual "authors" node
                        val author = authorSnapshot.getValue(Author::class.java)
                        author?.id = authorSnapshot.key

                        //placing th value of author in the list
                        author?.let {authors.add(it)}
                    }
                    _authors.value = authors
                }
            }

            override fun onCancelled(error: DatabaseError) {}

        })
    }
}