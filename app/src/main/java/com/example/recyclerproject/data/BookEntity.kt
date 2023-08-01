package com.example.recyclerproject.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.recyclerproject.model.Book

@Entity(tableName = "books")
data class BookEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "book_image")
    val bookImage: String?,

    @ColumnInfo(name = "book_name")
    val bookName: String?,

    @ColumnInfo(name = "author_image")
    val authorImage: String?,

    @ColumnInfo(name = "author_name")
    val authorName: String?,

    @ColumnInfo(name = "isbn")
    val isbn: String?,

    @ColumnInfo(name = "book_type_image")
    val bookTypeImage: String?,

    @ColumnInfo(name = "book_type")
    val bookType: String?,

    @ColumnInfo(name = "favorite")
    val favorite: Boolean = false,

    @ColumnInfo(name = "description")
    val description: String?
) {
    companion object {
        fun fromBook(book: Book): BookEntity {
            return BookEntity(
                bookImage = book.bookImage,
                bookName = book.bookName,
                authorImage = book.authorImage,
                authorName = book.authorName,
                isbn = book.isbn,
                bookTypeImage = book.bookTypeImage,
                bookType = book.bookType,
                favorite = book.favorite,
                description = book.description
            )
        }
    }

    fun toBook(): Book {
        return Book(
            id = id,
            bookImage = bookImage,
            bookName = bookName,
            authorImage = authorImage,
            authorName = authorName,
            isbn = isbn,
            bookTypeImage = bookTypeImage,
            bookType = bookType,
            favorite = favorite,
            description = description
        )
    }
}
