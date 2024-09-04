package com.norbert.koller.shared.api
import android.content.Context
import com.norbert.koller.shared.R
import com.norbert.koller.shared.data.LoginTokensResponseData
import com.norbert.koller.shared.data.BaseData
import com.norbert.koller.shared.data.RoomData
import com.norbert.koller.shared.data.UserData
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.norbert.koller.shared.data.ApiLoginRefreshData
import com.norbert.koller.shared.data.ApiLoginUsernameAndPasswordData
import com.norbert.koller.shared.recycleradapters.PagingSource
import com.norbert.koller.shared.data.BaseProgramData
import com.norbert.koller.shared.data.BaseProgramTypeData
import com.norbert.koller.shared.data.ClassData
import com.norbert.koller.shared.data.CrossingData
import com.norbert.koller.shared.data.GroupData
import com.norbert.koller.shared.data.ProgramData
import com.norbert.koller.shared.data.StudyGroupData
import com.norbert.koller.shared.data.StudyGroupTypeData
import com.norbert.koller.shared.viewmodels.ListViewModel
import retrofit2.Response
import retrofit2.http.*


interface APIInterface {

    companion object {

        var loadingDelayFrom : Float = 0f
        var loadingDelayTo : Float = 0f

        fun serverErrorPopup(context: Context?, errorCode: String? = "-", onDismiss : (() -> Unit)? = null){
            if(context == null) return
            MaterialAlertDialogBuilder(context)
                .setTitle("Sikertelen kapcsolódás!")
                .setMessage("Az applikáció és a szerver közötti kapcsolódás sikertlen.\n" +
                        "Kérlek própád újra.\nHiba: " +
                        errorCode)
                .setPositiveButton(
                    context.getString(R.string.ok)
                )
                { _, _ ->

                }
                .setOnDismissListener {
                    onDismiss?.invoke()
                }
                .show()
        }
    }




    @Headers("Content-Type: application/json")
    @POST("oauth/token")
    suspend fun postLoginTokens(
        @Body requestModel: ApiLoginRefreshData,
    ) : Response<LoginTokensResponseData>

    @Headers("Content-Type: application/json")
    @POST("oauth/token")
    suspend fun postLogin(
        @Body requestModel: ApiLoginUsernameAndPasswordData,
    ) : Response<LoginTokensResponseData>

    @Headers("Content-Type: application/json")
    @GET("api/users/me")
    suspend fun getCurrentUser(
    ) : Response<UserData>

    @Headers("Content-Type: application/json")
    @GET("api/users")
    suspend fun getUsers(
        @Query(value = "limit") limit : Int,
        @Query(value = "offset") offset : Int,
        @Query(value = "sort") sort : String = "Name:asc",
        @Query(value = "filter") filter : String? = null,
    ) : Response<List<UserData>>

    @Headers("Content-Type: application/json")
    @GET("api/rooms")
    suspend fun getRooms(
        @Query(value = "limit") limit : Int,
        @Query(value = "offset") offset : Int,
        @Query(value = "sort") sort : String = "RID:asc",
        @Query(value = "filter") filter : String? = null,
    ) : Response<List<RoomData>>


    @Headers("Content-Type: application/json")
    @GET("api/timetable/mandatory")
    suspend fun getBasePrograms(
        @Query(value = "limit") limit : Int,
        @Query(value = "offset") offset : Int,
        @Query(value = "sort") sort : String = "Date:asc,Lesson:asc",
        @Query(value = "filter") filter : String? = null,
    ) : Response<List<BaseProgramData>>

    @Headers("Content-Type: application/json")
    @GET("api/timetable/mandatory/{id}")
    suspend fun getMandatory(
        @Path("id") searchById:Int,
    ) : Response<BaseProgramData>

    @Headers("Content-Type: application/json")
    @GET("api/timetable/studygroup")
    suspend fun getStudyGroups(
        @Query(value = "limit") limit : Int,
        @Query(value = "offset") offset : Int,
        @Query(value = "sort") sort : String = "Date:asc,Lesson:asc",
        @Query(value = "filter") filter : String? = null,
    ) : Response<List<StudyGroupData>>

    @Headers("Content-Type: application/json")
    @GET("api/timetable/studygroup/{id}")
    suspend fun getStudyGroup(
        @Path("id") searchById:Int,
    ) : Response<StudyGroupData>

    @Headers("Content-Type: application/json")
    @GET("api/users/{id}")
    suspend fun getUser(
        @Path("id") searchById:Int,
    ) : Response<UserData>

    @Headers("Content-Type: application/json")
    @GET("api/rooms/{id}")
    suspend fun getRoom(
        @Path("id") searchById:Int,
    ) : Response<RoomData>

    @Headers("Content-Type: application/json")
    @GET("api/crossings/{id}}")
    suspend fun getMyCrossings(
        @Path("id") searchById:Int,
        @Query(value = "limit") limit : Int,
        @Query(value = "offset") offset : Int,
        @Query(value = "sort") sort : String = "Time:asc",
        @Query(value = "filter") filter : String? = null,
    ) : Response<List<CrossingData>>

    @Headers("Content-Type: application/json")
    @GET("api/institution/groups")
    suspend fun getGroups() : Response<List<GroupData>>

    @Headers("Content-Type: application/json")
    @GET("api/institution/classes")
    suspend fun getClasses() : Response<List<ClassData>>

    @Headers("Content-Type: application/json")
    @GET("api/timetable/studygroup/types")
    suspend fun getStudyGroupTypes(
        @Query(value = "limit") limit : Int,
        @Query(value = "offset") offset : Int,
        @Query(value = "sort") sort : String = "Topic:asc",
        @Query(value = "filter") filter : String? = null,
    ) : Response<List<StudyGroupTypeData>>

    @Headers("Content-Type: application/json")
    @GET("api/timetable/mandatory/types")
    suspend fun getBaseProgramTypes(
        @Query(value = "limit") limit : Int,
        @Query(value = "offset") offset : Int,
        @Query(value = "sort") sort : String = "Topic:asc",
        @Query(value = "filter") filter : String? = null,
    ) : Response<List<BaseProgramTypeData>>

    @Headers("Content-Type: application/json")
    @GET("api/timetable/studygroup/types/{pid}")
    suspend fun getStudyGroupType(
        @Path("pid") searchById:Int,
    ) : Response<StudyGroupTypeData>
}

class ProgramTypePagingSource(context : Context, viewModel: ListViewModel) : PagingSource(context, viewModel) {
    override fun getDataType(): Class<*> {
        return Array<BaseProgramTypeData>::class.java
    }

    override fun getDataTag(): String {
        return "base_program_type"
    }

    override suspend fun getApiResponse(apiResponse : APIInterface, limit : Int, offset : Int): Response<List<BaseData>> {
        return apiResponse.getBaseProgramTypes(limit, offset, getSort(), getFilters()) as Response<List<BaseData>>
    }
}

class StudyGroupTypePagingSource(context : Context, viewModel: ListViewModel) : PagingSource(context, viewModel) {

    override fun getDataType(): Class<*> {
        return Array<StudyGroupTypeData>::class.java
    }

    override fun getDataTag(): String {
        return "study_group_type"
    }

    override suspend fun getApiResponse(apiResponse : APIInterface, limit : Int, offset : Int): Response<List<BaseData>> {
        return apiResponse.getStudyGroupTypes(limit, offset, getSort(), getFilters()) as Response<List<BaseData>>
    }
}

class CrossingPagingSource(context: Context, val uid : Int, viewModel: ListViewModel) : PagingSource(context, viewModel) {

    override fun getDataType(): Class<*> {
        return Array<CrossingData>::class.java
    }

    override fun getDataTag(): String {
        return "crossing"
    }

    override suspend fun getApiResponse(apiResponse : APIInterface, limit : Int, offset : Int): Response<List<BaseData>> {
        return apiResponse.getMyCrossings(uid, limit, offset, getSort(), getFilters()) as Response<List<BaseData>>
    }
}

class UserPagingSource(context: Context, viewModel: ListViewModel) : PagingSource(context, viewModel) {

    override fun getDataType(): Class<*> {
        return Array<UserData>::class.java
    }

    override fun getDataTag(): String {
        return "user"
    }

    override suspend fun getApiResponse(apiResponse : APIInterface, limit : Int, offset : Int): Response<List<BaseData>> {
        return apiResponse.getUsers(limit, offset, getSort(), getFilters()) as Response<List<BaseData>>
    }
}

class RoomPagingSource(context : Context, viewModel: ListViewModel) : PagingSource(context, viewModel) {

    override fun getDataType(): Class<*> {
        return Array<RoomData>::class.java
    }

    override fun getDataTag(): String {
        return "room"
    }

    override suspend fun getApiResponse(apiResponse : APIInterface, limit : Int, offset : Int): Response<List<BaseData>> {
        return apiResponse.getRooms(limit, offset, getSort(), getFilters()) as Response<List<BaseData>>
    }
}

class ProgramPagingSource(context : Context, viewModel: ListViewModel) : PagingSource(context, viewModel) {

    override fun getDataType(): Class<*> {
        return Array<BaseProgramData>::class.java
    }

    override fun getDataTag(): String {
        return "base_program"
    }

    override suspend fun getApiResponse(apiResponse : APIInterface, limit : Int, offset : Int): Response<List<BaseData>> {
        return apiResponse.getBasePrograms(limit, offset, getSort(), getFilters()) as Response<List<BaseData>>
    }
}

class StudyGroupPagingSource(context : Context, viewModel: ListViewModel) : PagingSource(context, viewModel) {

    override fun getDataType(): Class<*> {
        return Array<StudyGroupData>::class.java
    }

    override fun getDataTag(): String {
        return "study_group"
    }

    override suspend fun getApiResponse(apiResponse : APIInterface, limit : Int, offset : Int): Response<List<BaseData>> {
        return apiResponse.getStudyGroups(limit, offset, getSort(), getFilters()) as Response<List<BaseData>>
    }
}