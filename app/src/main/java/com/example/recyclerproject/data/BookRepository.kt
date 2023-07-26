import com.example.recyclerproject.model.Book
import com.example.recyclerproject.network.ApiInterface
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class BookRepository {
    companion object {
        private const val JSON_KEEPER = "https://jsonkeeper.com/"
    }

    private val apiInterface: ApiInterface

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(JSON_KEEPER)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiInterface = retrofit.create(ApiInterface::class.java)
    }

    suspend fun getBooks(): Response<List<Book>> {
        return apiInterface.getBooks()
    }
}
