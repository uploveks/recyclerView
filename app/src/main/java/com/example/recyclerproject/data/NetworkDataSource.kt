package com.example.recyclerproject.data

import com.example.recyclerproject.model.Book

interface NetworkDataSource {
    suspend fun getBooks(): List<Book>
}
