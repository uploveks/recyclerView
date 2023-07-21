import com.example.recyclerproject.Book
import retrofit2.Response
import retrofit2.http.GET

interface ApiInterface {
    @GET("b/SDLA")
    suspend fun getBooks(): Response<List<Book>>
}
