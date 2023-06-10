package com.example.koller.api
import android.content.Context
import android.util.Log
import com.example.koller.data.ApiLoginData
import com.example.koller.R
import com.example.koller.data.ApiLoginRefreshData
import com.example.koller.data.ApiLoginTokensData
import com.example.koller.data.UserData
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import retrofit2.Call
import retrofit2.http.*


interface APIInterface {

    companion object {
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




    @POST("oauth/token")
    fun postLogin(
        @Body requestModel: ApiLoginData,
    ) : Call<ApiLoginTokensData>

    @POST("oauth/token")
    fun postLogin(
        @Body requestModel: ApiLoginRefreshData,
    ) : Call<ApiLoginTokensData>

    @GET("api/users/me")
    fun getCurrentUser(
        @HeaderMap headers: Map<String, String>
    ) : Call<UserData>

    @GET("api/users")
    fun getUsers(
        @Query(value = "limit") limit : Int,
        @Query(value = "offset") offset : Int,
        @HeaderMap headers: Map<String, String>
    ) : Call<List<UserData>>

    @GET("api/users/{id}")
    fun getUser(
        @Path("id") searchById:String,
        @HeaderMap headers: Map<String, String>
    ) : Call<UserData>

    @GET("api/crossings/me")
    fun getMyCrossings(
        @Query(value = "limit") limit : Int,
        @Query(value = "offset") offset : Int,
        @HeaderMap headers: Map<String, String>
    ) : Call<List<UserData>>
}