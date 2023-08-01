package com.example.recyclerproject.model

import java.io.Serializable

data class Book(
    var id: Int? = null,
    var bookImage: String? = null,
    var bookName: String? = null,
    var authorImage: String? = null,
    var authorName: String? = null,
    var isbn: String? = null,
    var bookTypeImage: String? = null,
    var bookType: String? = null,
    var favorite: Boolean = false,
    var description: String? = null
) : Serializable
