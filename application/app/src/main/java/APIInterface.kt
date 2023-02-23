
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface APIInterface {

    @GET("/username")
    suspend fun getUsername() : Response<String>
}