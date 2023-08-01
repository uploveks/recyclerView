package com.example.recyclerproject

import android.app.Application
import android.content.Context
import android.util.Log

class BookApplication : Application() {

    init {
        Log.d("text_context", "before init instance")
        instance = this
        Log.d("text_context", "after init instance")
    }

    companion object {
        private var instance: BookApplication? = null


        fun getApplicationContext(): Context? {
            Log.d("text_context", "get app context")
            return instance?.applicationContext
        }
    }

    override fun onCreate() {
        Log.d("text_context", "on create")
        super.onCreate()
        Log.d("text_context", "after on create")
    }
}