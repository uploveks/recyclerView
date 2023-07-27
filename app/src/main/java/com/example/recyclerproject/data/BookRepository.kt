import com.example.recyclerproject.model.Book
import com.example.recyclerproject.network.ApiInterface
import retrofit2.Response

class BookRepository {
    private val apiInterface = RetrofitInstance.retrofit.create(ApiInterface::class.java)

    suspend fun getBooks(): Response<List<Book>> {
        return apiInterface.getBooks()
    }
}
