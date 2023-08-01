package com.example.recyclerproject.data

import com.example.recyclerproject.model.Book
import com.example.recyclerproject.network.ApiInterface

class BookDataSourceImplementation(
    private val localDataSource: BookLocalDataSource,
    private val remoteDataSource: ApiInterface
) : BookDataSource {

    override suspend fun getBooks(): List<Book> {
        val localBooks = localDataSource.getAllBooks()
        return localBooks.ifEmpty {
            val remoteBooks = remoteDataSource.getBooks().body() ?: emptyList()
            localDataSource.saveBooks(remoteBooks)
            remoteBooks
        }
    }
}
