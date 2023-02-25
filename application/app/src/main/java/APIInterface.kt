
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface APIInterface {

    @GET("username")
    fun getUsername() : Call<String>
}