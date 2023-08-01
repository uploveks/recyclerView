package com.example.recyclerproject.data

import com.example.recyclerproject.model.Book

interface LocalDataSource {
    suspend fun getAllBooks(): List<Book>
    suspend fun insertAll(books: List<Book>)
    suspend fun saveBooks(books: List<Book>)
}

