package com.example.recyclerproject

import java.io.Serializable

class Book : Serializable {
    var id: Int = 0
    var name: String = ""
    var author: String = ""
    var type: String = ""
    var isFavorite: Boolean = false
    var isbn: String = ""
    var bookImageUrl: String = ""
    var authorImageUrl: String = ""
    var details: String = ""
}