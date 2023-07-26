package com.example.recyclerproject.network

import com.example.recyclerproject.model.Book
import retrofit2.Response
import retrofit2.http.GET

interface ApiInterface {
    @GET("b/SDLA")
    suspend fun getBooks(): Response<List<Book>>
}
