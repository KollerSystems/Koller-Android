
import com.example.koller.ApiLoginData
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*


interface APIInterface {

    @POST("oauth/token")
    fun postLogin(@Body requestModel: ApiLoginData) : Call<ApiLoginTokensData>

    @GET("api/user/me")
    fun getCurrentUser(
        @HeaderMap headers: Map<String, String>
    ) : Call<UserData>
}