package com.example.recyclerproject

import java.io.Serializable

class Book : Serializable {
    var id: Int = 0
    var bookImage: String = ""
    var bookName: String = ""
    var authorImage: String = ""
    var authorName: String = ""
    var isbn: String = ""
    var bookTypeImage: String = ""
    var bookType: String = ""
    var favorite: Boolean = false
    var description: String = ""
}