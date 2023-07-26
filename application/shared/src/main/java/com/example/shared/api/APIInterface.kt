package com.example.shared.api
import android.content.Context
import android.renderscript.Float2
import android.util.Log
import com.example.shared.R
import com.example.shared.data.ApiLoginData
import com.example.shared.data.ApiLoginRefreshData
import com.example.shared.data.ApiLoginTokensData
import com.example.shared.data.UserData
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*
import java.util.Vector


interface APIInterface {

    companion object {

        var loadingDelayFrom : Float = 0f
        var loadingDelayTo : Float = 0f


        fun getHeaderMap(): Map<String, String> {
            val headerMap = mutableMapOf<String, String>()
            headerMap["Content-Type"] = "application/json"
            headerMap["Authorization"] = "Bearer ${ApiLoginTokensData.instance!!.access_token}"
            Log.d("INFO", ApiLoginTokensData.instance!!.access_token)
            return headerMap
        }

        fun ServerUnableToConnectPopup(context: Context){
            MaterialAlertDialogBuilder(context)
                .setTitle("Szerver hiba!")
                .setMessage("Az applikáció nem tudott kapcsolatot létesíteni a szerverrel.\n" +
                        "Kérlek própád újra.")
                .setPositiveButton(
                    context.getString(R.string.ok)
                )
                { _, _ ->

                }
                .show()
        }

        fun ServerErrorPopup(context: Context?, errorCode: String? = "No error message"){
            if(context == null) return
            MaterialAlertDialogBuilder(context)
                .setTitle("Szerver hiba!")
                .setMessage("Az applikáció és a szerver között hiba lépett fel.\n" +
                        "Kérlek própád újra.\n" +
                        errorCode)
                .setPositiveButton(
                    context.getString(R.string.ok)
                )
                { _, _ ->

                }
                .show()
        }
    }



    @Headers(
        "Content-Type: application/json"
    )
    @POST("oauth/token")
    fun postLogin(
        @Body requestModel: ApiLoginData,
    ) : Call<ApiLoginTokensData>

    @Headers(
        "Content-Type: application/json"
    )
    @POST("oauth/token")
    fun postLogin(
        @Body requestModel: ApiLoginRefreshData,
    ) : Call<ApiLoginTokensData>

    @GET("api/users/me")
    fun getCurrentUser(
        @HeaderMap headers: Map<String, String>
    ) : Call<UserData>

    @GET("api/users")
    suspend fun getUsers(
        @Query(value = "limit") limit : Int,
        @Query(value = "offset") offset : Int,
        @HeaderMap headers: Map<String, String>
    ) : Response<List<UserData>>

    @GET("api/users/{id}")
    fun getUser(
        @Path("id") searchById:Int,
        @HeaderMap headers: Map<String, String>
    ) : Call<UserData>

    @GET("api/crossings/me")
    fun getMyCrossings(
        @Query(value = "limit") limit : Int,
        @Query(value = "offset") offset : Int,
        @HeaderMap headers: Map<String, String>
    ) : Call<List<UserData>>
}