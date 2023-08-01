package com.example.recyclerproject.data

import android.content.Context
import androidx.databinding.adapters.Converters
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.recyclerproject.model.Book

@Database(entities = [BookEntity::class], version = 1, exportSchema = false)
abstract class BookDataBase : RoomDatabase() {
    abstract fun bookDao(): BookDao

    companion object {
        private var INSTANCE: BookDataBase ?= null
        fun getDataBase(context: Context): BookDataBase? {
            if (INSTANCE == null) {
                synchronized(BookDataBase::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext, BookDataBase::class.java, "book_database").allowMainThreadQueries().build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}