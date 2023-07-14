package com.example.recyclerproject


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recyclerproject.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var bookAdapter: BookAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val books = createBookList()

        bookAdapter = BookAdapter(books)
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = bookAdapter
        }

    }

    private fun createBookList(): MutableList<Book>{
        val bookList = mutableListOf<Book>()

        val book1 = Book()
        book1.id = 1
        book1.name = "Finding Time Again"
        book1.author = "Marcel Proust"
        book1.type = "Classic"
        book1.isFavorite = false
        book1.isbn = "ISBN: 9780141180311"
        book1.bookImageUrl = "https://cdn4.libris.ro/img/pozeprod/28586/28585536-1.jpg"
        book1.authorImageUrl = "https://upload.wikimedia.org/wikipedia/commons/b/b8/Marcel_Proust_1895.jpg"
        bookList.add(book1)

        val book2 = Book()
        book2.id = 2
        book2.name = "The World's worst Assistant"
        book2.author = "Sona Movsesian"
        book2.type = "Comedy"
        book2.isFavorite = false
        book2.isbn = "ISBN: 5278965689027"
        book2.bookImageUrl = "https://images2.penguinrandomhouse.com/cover/9780593185513"
        book2.authorImageUrl = "https://images.squarespace-cdn.com/content/v1/5b47794f96d4553780daae3b/34db0a32-1c5c-41c3-ab83-f6c12916b348/Headshot.jpeg?format=750w"
        bookList.add(book2)

        val book3 = Book()
        book3.id = 3
        book3.name = "The Queen's Assassin"
        book3.author = "Melissa De La Cruz"
        book3.type = "Fantasy"
        book3.isFavorite = false
        book3.isbn = "ISBN: 246753234566"
        book3.bookImageUrl = "https://m.media-amazon.com/images/I/91RJSngUfJL._SL1500_.jpg"
        book3.authorImageUrl = "https://melissa-delacruz.com/wordpress/wp-content/uploads/Melissa-de-la-Cruz-2019-author-photo-credit-Maria-Cina-776x1035.jpg"
        bookList.add(book3)

        val book4 = Book()
        book4.id = 4
        book4.name = "Winnie-the-Pooh"
        book4.author = "Alan Alexander Milne"
        book4.type = "Children Literature"
        book4.isFavorite = false
        book4.isbn = "ISBN: 7654234554322"
        book4.bookImageUrl = "https://m.media-amazon.com/images/I/71XAese2ItL._SL1500_.jpg"
        book4.authorImageUrl = "https://cdn.britannica.com/22/66322-050-9A24E091/AA-Milne-1920.jpg"
        bookList.add(book4)

        val book5 = Book()
        book5.id = 5
        book5.name = "Frankenstein"
        book5.author = "Mary Shelley"
        book5.type = "Horror"
        book5.isFavorite = false
        book5.isbn = "ISBN: 9235675489027"
        book5.bookImageUrl = "https://cdn4.libris.ro/img/pozeprod/15548/15547065-1.jpg"
        book5.authorImageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/b/b4/Mary_Wollstonecraft_Shelley_Rothwell.tif/lossy-page1-1280px-Mary_Wollstonecraft_Shelley_Rothwell.tif.jpg"
        bookList.add(book5)

        val book6 = Book()
        book6.id = 6
        book6.name = "Born a Crime"
        book6.author = "Trevor Noah"
        book6.type = "Comedy"
        book6.isFavorite = false
        book6.isbn = "ISBN: 256976543324"
        book6.bookImageUrl = "https://cdn.dc5.ro/img-prod/1985828-0.jpeg"
        book6.authorImageUrl = "https://whca.press/wp-content/uploads/2022/02/Trevor-Noah-1-1300x1950.jpeg"
        bookList.add(book6)

        val book7 = Book()
        book7.id = 7
        book7.name = "The Five-Star Weekend"
        book7.author = "Ellin Hilderbrand"
        book7.type = "Fiction"
        book7.isFavorite = false
        book7.isbn = "ISBN: 267543259027"
        book7.bookImageUrl = "https://m.media-amazon.com/images/I/71OcyQo+XPL._SL1500_.jpg"
        book7.authorImageUrl = "https://cdn.thelocalmomsnetwork.net/wp-content/uploads/sites/24/2020/05/21164055/Hilderbrand_28SUMMERSapproved-authorphoto-scaled-1-scaled.jpg"
        bookList.add(book7)

        val book8 = Book()
        book8.id = 8
        book8.name = "The Lord of the Rings"
        book8.author = "John Ronald Tolkien"
        book8.type = "Fantasy"
        book8.isFavorite = false
        book8.isbn = "ISBN: 753791918263"
        book8.bookImageUrl = "https://m.media-amazon.com/images/I/71jLBXtWJWL._SL1500_.jpg"
        book8.authorImageUrl = "https://cdn.britannica.com/65/66765-050-63A945A7/JRR-Tolkien.jpg"
        bookList.add(book8)

        val book9 = Book()
        book9.id = 9
        book9.name = "Jurassic park"
        book9.author = "Michael Crichton"
        book9.type = "Adventure"
        book9.isFavorite = false
        book9.isbn = "ISBN: 12345698765"
        book9.bookImageUrl = "https://m.media-amazon.com/images/I/81rBVCDfrgL._SL1500_.jpg"
        book9.authorImageUrl = "https://upload.wikimedia.org/wikipedia/commons/a/af/MichaelCrichton_2.jpg"
        bookList.add(book9)

        return bookList
    }

}