package com.example.recyclerproject

import java.io.Serializable

class Book : Serializable {
    var id: Int = 0
    var bookImage: String?=null
    var bookName: String?=null
    var authorImage: String?=null
    var authorName: String?=null
    var isbn: String?=null
    var bookTypeImage: String?=null
    var bookType: String?=null
    var favorite: Boolean = false
    var description: String?=null
}