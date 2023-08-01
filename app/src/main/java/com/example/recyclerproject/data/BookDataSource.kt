package com.example.recyclerproject.data

import com.example.recyclerproject.model.Book

interface BookDataSource {
    suspend fun getBooks(): List<Book>
}
