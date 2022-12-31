package com.kingelias.fruitofchrist.data

import com.google.firebase.database.Exclude

data class Author(
    @get:Exclude //to exclude the id variable when getting saved to firebase
    var id: String? = null,
    var name: String? = null
)
