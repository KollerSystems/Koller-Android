package com.norbert.koller.shared.api
import android.content.Context
import android.util.Log
import com.norbert.koller.shared.R
import com.norbert.koller.shared.data.ApiLoginTokensData
import com.norbert.koller.shared.data.BaseData
import com.norbert.koller.shared.data.RoomData
import com.norbert.koller.shared.data.UserData
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.norbert.koller.shared.data.ApiLoginRefreshData
import com.norbert.koller.shared.data.ApiLoginUsernameAndPasswordData
import com.norbert.koller.shared.recycleradapters.BasePagingSource
import com.norbert.koller.shared.data.BaseProgramData
import com.norbert.koller.shared.data.CrossingData
import com.norbert.koller.shared.viewmodels.BaseViewModel
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*


interface APIInterface {

    companion object {

        var loadingDelayFrom : Float = 0f
        var loadingDelayTo : Float = 0f


        fun getHeaderMap(): Map<String, String> {
            val headerMap = mutableMapOf<String, String>()
            headerMap["Content-Type"] = "application/json"
            headerMap["Authorization"] = "Bearer ${ApiLoginTokensData.instance.access_token}"
            Log.d("INFO", ApiLoginTokensData.instance.access_token)
            return headerMap
        }

        fun serverUnableToConnectPopup(context: Context){
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

        fun serverErrorPopup(context: Context?, errorCode: String? = "No error message"){
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
        @Body requestModel: ApiLoginRefreshData,
    ) : Call<ApiLoginTokensData>

    @POST("oauth/token")
    fun postLogin(
        @Body requestModel: ApiLoginUsernameAndPasswordData,
    ) : Call<ApiLoginTokensData>

    @Headers(
        "Content-Type: application/json"
    )

    @GET("api/users/me")
    fun getCurrentUser(
        @HeaderMap headers: Map<String, String>
    ) : Call<UserData>

    @GET("api/users")
    suspend fun getUsers(
        @Query(value = "limit") limit : Int,
        @Query(value = "offset") offset : Int,
        @Query(value = "sort") sort : String = "Name:asc",
        @Query(value = "filter") filter : String? = null,
        @HeaderMap headers: Map<String, String>
    ) : Response<List<UserData>>

    @GET("api/rooms")
    suspend fun getRooms(
        @Query(value = "limit") limit : Int,
        @Query(value = "offset") offset : Int,
        @Query(value = "sort") sort : String = "RID:asc",
        @Query(value = "filter") filter : String? = null,
        @HeaderMap headers: Map<String, String>
    ) : Response<List<RoomData>>

    @GET("api/timetable/mandatory")
    suspend fun getBasePrograms(
        @Query(value = "limit") limit : Int,
        @Query(value = "offset") offset : Int,
        @Query(value = "sort") sort : String = "Date:asc,Lesson:asc",
        @Query(value = "filter") filter : String? = null,
        @HeaderMap headers: Map<String, String>
    ) : Response<List<BaseProgramData>>

    @GET("api/users/{id}")
    fun getUser(
        @Path("id") searchById:Int,
        @HeaderMap headers: Map<String, String>
    ) : Call<UserData>

    @GET("api/rooms/{id}")
    fun getRoom(
        @Path("id") searchById:Int,
        @HeaderMap headers: Map<String, String>
    ) : Call<RoomData>

    @GET("api/crossings/{id}}")
    suspend fun getMyCrossings(
        @Path("id") searchById:Int,
        @Query(value = "limit") limit : Int,
        @Query(value = "offset") offset : Int,
        @Query(value = "sort") sort : String = "Time:asc",
        @Query(value = "filter") filter : String? = null,
        @HeaderMap headers: Map<String, String>
    ) : Response<List<CrossingData>>
}

class CrossingPagingSource(context: Context, val UID : Int, viewModel: BaseViewModel) : BasePagingSource(context, viewModel) {

    override suspend fun getApiResponse(apiResponse : APIInterface, limit : Int, offset : Int): Response<List<BaseData>> {

        return apiResponse.getMyCrossings(UID, limit, offset, getSort(), getFilters(), APIInterface.getHeaderMap()) as Response<List<BaseData>>

    }

}

class UserPagingSource(context: Context, viewModel: BaseViewModel) : BasePagingSource(context, viewModel) {

    override suspend fun getApiResponse(apiResponse : APIInterface, limit : Int, offset : Int): Response<List<BaseData>> {

        return apiResponse.getUsers(limit, offset, getSort(), getFilters(), APIInterface.getHeaderMap()) as Response<List<BaseData>>

    }

}

class RoomPagingSource(context : Context, viewModel: BaseViewModel) : BasePagingSource(context, viewModel) {

    override suspend fun getApiResponse(apiResponse : APIInterface, limit : Int, offset : Int): Response<List<BaseData>> {

        return apiResponse.getRooms(limit, offset, getSort(), getFilters(), APIInterface.getHeaderMap()) as Response<List<BaseData>>
    }

}

class BaseProgramPagingSource(context : Context, viewModel: BaseViewModel) : BasePagingSource(context, viewModel) {

    override suspend fun getApiResponse(apiResponse : APIInterface, limit : Int, offset : Int): Response<List<BaseData>> {

        return apiResponse.getBasePrograms(limit, offset, getSort(), getFilters(), APIInterface.getHeaderMap()) as Response<List<BaseData>>

    }

}