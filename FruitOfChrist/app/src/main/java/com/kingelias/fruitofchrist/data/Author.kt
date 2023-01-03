package com.kingelias.fruitofchrist.data

import com.google.firebase.database.Exclude

data class Author(
    @get:Exclude //to exclude the id variable when getting saved to firebase
    var id: String? = null,
    var name: String? = null
){
    override fun equals(other: Any?): Boolean {
        return if(other is Author){
            other.id == id
        }else false
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + (name?.hashCode() ?: 0)
        return result
    }
}
