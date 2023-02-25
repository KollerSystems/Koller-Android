
import com.example.koller.ApiLoginData
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query


interface APIInterface {

    @POST("oauth/token")
    fun postLogin(@Body requestModel: ApiLoginData) : Call<ApiLoginTokensData>

    @GET("api/user/me")
    fun getCurrentUser() : Call<UserData>
}