package com.example.recyclerproject.data

import com.example.recyclerproject.model.Book
import com.example.recyclerproject.network.ApiInterface

class NetworkDataSourceImplementation(
    private val apiInterface: ApiInterface
) : NetworkDataSource {

    override suspend fun getBooks(): List<Book> {
        return apiInterface.getBooks().body() ?: emptyList()
    }
}
