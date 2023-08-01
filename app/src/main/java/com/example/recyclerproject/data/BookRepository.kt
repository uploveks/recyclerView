package com.example.recyclerproject.data

import com.example.recyclerproject.model.Book

class BookRepository(
    private val remoteDataSource: BookDataSource,
    private val localDataSource: BookLocalDataSource
) {
    private var books: List<Book> = emptyList()

    suspend fun getBooks(): List<Book> {
        if (books.isEmpty()) {
            books = fetchBooksFromServer()
            localDataSource.insertAll(books)
        }
        return books
    }

    private suspend fun fetchBooksFromServer(): List<Book> {
        return remoteDataSource.getBooks()
    }
}
