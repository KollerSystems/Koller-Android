package com.norbert.koller.shared.api
import android.content.Context
import com.norbert.koller.shared.R
import com.norbert.koller.shared.data.LoginTokensResponseData
import com.norbert.koller.shared.data.RoomData
import com.norbert.koller.shared.data.UserData
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.norbert.koller.shared.data.ApiLoginRefreshData
import com.norbert.koller.shared.data.ApiLoginUsernameAndPasswordData
import com.norbert.koller.shared.data.BaseProgramData
import com.norbert.koller.shared.data.BaseProgramTypeData
import com.norbert.koller.shared.data.ClassData
import com.norbert.koller.shared.data.CrossingData
import com.norbert.koller.shared.data.GroupData
import com.norbert.koller.shared.data.StudyGroupData
import com.norbert.koller.shared.data.StudyGroupTypeData
import com.norbert.koller.shared.helpers.DateTimeHelper
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
        @Query(value = "sort") sort : String,
        @Query(value = "filter") filter : String? = null,
    ) : Response<List<UserData>>

    @Headers("Content-Type: application/json")
    @GET("api/rooms")
    suspend fun getRooms(
        @Query(value = "limit") limit : Int,
        @Query(value = "offset") offset : Int,
        @Query(value = "sort") sort : String,
        @Query(value = "filter") filter : String? = null,
    ) : Response<List<RoomData>>


    @Headers("Content-Type: application/json")
    @GET("api/timetable/mandatory")
    suspend fun getBasePrograms(
        @Query(value = "limit") limit : Int,
        @Query(value = "offset") offset : Int,
        @Query(value = "sort") sort : String,
        @Query(value = "filter") filter : String? = null,
    ) : Response<List<BaseProgramData>>

    @Headers("Content-Type: application/json")
    @GET("api/timetable/mandatory/{id}")
    suspend fun getBaseProgram(
        @Path("id") searchById:Int,
    ) : Response<BaseProgramData>

    @Headers("Content-Type: application/json")
    @GET("api/timetable/studygroup")
    suspend fun getStudyGroups(
        @Query(value = "limit") limit : Int,
        @Query(value = "offset") offset : Int,
        @Query(value = "sort") sort : String,
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
    @GET("api/crossings/{id}")
    suspend fun getCrossings(
        @Path("id") searchById:Int,
        @Query(value = "limit") limit : Int,
        @Query(value = "offset") offset : Int,
        @Query(value = "sort") sort : String,
        @Query(value = "filter") filter : String? = null,
    ) : Response<List<CrossingData>>

    @Headers("Content-Type: application/json")
    @GET("api/corssings/{uid}/{id}")
    suspend fun getCrossing(
        @Path("uid") searchByUid:Int,
        @Path("id") searchById:Int,
    ) : Response<RoomData>

    @Headers("Content-Type: application/json")
    @GET("api/institution/groups/{id}")
    suspend fun getGroup(
        @Path("id") searchById:Int,
    ) : Response<GroupData>

    @Headers("Content-Type: application/json")
    @GET("api/institution/groups")
    suspend fun getGroups() : Response<List<GroupData>>

    @Headers("Content-Type: application/json")
    @GET("api/institution/classes/{id}")
    suspend fun getClass(
        @Path("id") searchById:Int,
    ) : Response<ClassData>

    @Headers("Content-Type: application/json")
    @GET("api/institution/classes")
    suspend fun getClasses() : Response<List<ClassData>>

    @Headers("Content-Type: application/json")
    @GET("api/timetable/studygroup/types")
    suspend fun getStudyGroupTypes(
        @Query(value = "limit") limit : Int,
        @Query(value = "offset") offset : Int,
        @Query(value = "sort") sort : String,
        @Query(value = "filter") filter : String? = null,
    ) : Response<List<StudyGroupTypeData>>

    @Headers("Content-Type: application/json")
    @GET("api/timetable/mandatory/types/{pid}")
    suspend fun getBaseProgramType(
        @Path("pid") searchById:Int,
    ) : Response<RoomData>

    @Headers("Content-Type: application/json")
    @GET("api/timetable/mandatory/types")
    suspend fun getBaseProgramTypes(
        @Query(value = "limit") limit : Int,
        @Query(value = "offset") offset : Int,
        @Query(value = "sort") sort : String,
        @Query(value = "filter") filter : String? = null,
    ) : Response<List<BaseProgramTypeData>>

    @Headers("Content-Type: application/json")
    @GET("api/timetable/studygroup/types/{pid}")
    suspend fun getStudyGroupType(
        @Path("pid") searchById:Int,
    ) : Response<StudyGroupTypeData>
}

abstract class ApiDataObject{
    abstract fun getDataType(): Class<*>

    abstract suspend fun getApiResponse(limit : Int, offset : Int, sort : String?, filter: String): Response<*>

    abstract suspend fun getSingleApiResponse(id : Int) : Response<*>

    fun getTimeLimit() : Int{
        return DateTimeHelper.TIME_NOT_IMPORTANT
    }
}

class ApiDataObjectBaseProgramType : ApiDataObject() {
    override fun getDataType(): Class<*> {
        return BaseProgramTypeData::class.java
    }

    override suspend fun getApiResponse(limit : Int, offset : Int, sort : String?, filter: String): Response<*> {
        return RetrofitInstance.api.getBaseProgramTypes(limit, offset, sort?:"Topic:asc", filter)
    }

    override suspend fun getSingleApiResponse(id: Int) : Response<*> {
        return RetrofitInstance.api.getBaseProgramType(id)
    }
}

class ApiDataObjectStudyGroupType : ApiDataObject() {
    override fun getDataType(): Class<*> {
        return StudyGroupTypeData::class.java
    }

    override suspend fun getApiResponse(limit : Int, offset : Int, sort : String?, filter: String): Response<*> {
        return RetrofitInstance.api.getStudyGroupTypes(limit, offset, sort?:"Topic:asc", filter)
    }

    override suspend fun getSingleApiResponse(id: Int) : Response<*> {
        return RetrofitInstance.api.getStudyGroupType(id)
    }
}

class ApiDataObjectCrossing(val uid : Int) : ApiDataObject() {
    override fun getDataType(): Class<*> {
        return CrossingData::class.java
    }

    override suspend fun getApiResponse(limit : Int, offset : Int, sort : String?, filter: String): Response<*> {
        return RetrofitInstance.api.getCrossings(uid, limit, offset, sort?:"Time:asc", filter)
    }

    override suspend fun getSingleApiResponse(id: Int) : Response<*> {
        return RetrofitInstance.api.getCrossing(uid, id)
    }
}

class ApiDataObjectUser : ApiDataObject() {

    override fun getDataType(): Class<*> {
        return UserData::class.java
    }

    override suspend fun getApiResponse(limit : Int, offset : Int, sort : String?, filter: String): Response<*> {
        return RetrofitInstance.api.getUsers(limit, offset, sort?:"Name:asc", filter)
    }

    override suspend fun getSingleApiResponse(id: Int) : Response<*> {
        return RetrofitInstance.api.getUser(id)
    }
}

class ApiDataObjectRoom() : ApiDataObject() {
    override fun getDataType(): Class<*> {
        return RoomData::class.java
    }

    override suspend fun getApiResponse(limit : Int, offset : Int, sort : String?, filter: String): Response<*> {
        return RetrofitInstance.api.getRooms(limit, offset, sort?:"RID:asc", filter)
    }

    override suspend fun getSingleApiResponse(id: Int) : Response<*> {
        return RetrofitInstance.api.getRoom(id)
    }
}

class ApiDataObjectBaseProgram() : ApiDataObject() {
    override fun getDataType(): Class<*> {
        return BaseProgramData::class.java
    }

    override suspend fun getApiResponse(limit : Int, offset : Int, sort : String?, filter: String): Response<*> {
        return RetrofitInstance.api.getBasePrograms(limit, offset, sort?:"Date:asc,Lesson:asc", filter)
    }

    override suspend fun getSingleApiResponse(id: Int) : Response<*> {
        return RetrofitInstance.api.getBaseProgram(id)
    }
}

class ApiDataObjectStudyGroup() : ApiDataObject() {
    override fun getDataType(): Class<*> {
        return StudyGroupData::class.java
    }

    override suspend fun getApiResponse(limit : Int, offset : Int, sort : String?, filter: String): Response<*> {
        return RetrofitInstance.api.getStudyGroups(limit, offset, sort?:"Date:asc,Lesson:asc", filter)
    }

    override suspend fun getSingleApiResponse(id: Int) : Response<*> {
        return RetrofitInstance.api.getStudyGroup(id)
    }
}

class ApiDataObjectClass() : ApiDataObject() {
    override fun getDataType(): Class<*> {
        return ClassData::class.java
    }

    override suspend fun getApiResponse(limit : Int, offset : Int, sort : String?, filter: String): Response<*> {
        return RetrofitInstance.api.getClasses()
    }

    override suspend fun getSingleApiResponse(id: Int) : Response<*> {
        return RetrofitInstance.api.getClass(id)
    }
}

class ApiDataObjectGroup() : ApiDataObject() {
    override fun getDataType(): Class<*> {
        return GroupData::class.java
    }

    override suspend fun getApiResponse(limit : Int, offset : Int, sort : String?, filter: String): Response<*> {
        return RetrofitInstance.api.getGroups()
    }

    override suspend fun getSingleApiResponse(id: Int) : Response<*> {
        return RetrofitInstance.api.getGroup(id)
    }
}