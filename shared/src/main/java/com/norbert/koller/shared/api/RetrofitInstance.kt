package com.norbert.koller.shared.api

import androidx.lifecycle.LifecycleCoroutineScope
import com.google.gson.Gson
import com.norbert.koller.shared.data.BaseData
import com.norbert.koller.shared.data.ErrorData
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit


object RetrofitInstance {

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    val timeout : Long = 10

    private val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .connectTimeout(timeout, TimeUnit.SECONDS)
        .writeTimeout(timeout, TimeUnit.SECONDS)
        .readTimeout(timeout, TimeUnit.SECONDS)
        .addInterceptor(AuthInterceptor())
        .build()

    val dIP = "http://main.nzx.hu:48659/"
    val grazIP = ""

    val api : APIInterface by lazy {
        Retrofit.Builder()
            .baseUrl(dIP)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(APIInterface::class.java)
    }

    fun communicate(lifecycleCoroutineScope: LifecycleCoroutineScope, functionToCall: suspend () -> Response<*>, onSuccess: (data : BaseData) -> Unit, onError: (error : String?, errorBody : ErrorData?) -> Unit){

        lifecycleCoroutineScope.launch {
            val response : Response<*> = try{
                functionToCall()
            }
            catch (e : IOException){
                onError.invoke(e.localizedMessage, null)
                return@launch
            }
            catch (e : HttpException){
                onError.invoke(e.localizedMessage, getErrorBody(e.response()))
                return@launch
            }
            if(response.isSuccessful && response.body() != null){
                val baseData = response.body() as BaseData
                baseData.saveReceivedTime()
                onSuccess.invoke(baseData)
            }
            else{
                onError.invoke(response.code().toString(), getErrorBody(response))
            }
        }
    }

    suspend fun communicate(functionToCall: suspend () -> Response<*>, onSuccess: (data : Any) -> Unit, onError: (error : String?, errorBody : ErrorData?) -> Unit){


            val response : Response<*> = try{
                functionToCall()
            }
            catch (e : IOException){
                onError.invoke(e.localizedMessage, null)
                return
            }
            catch (e : HttpException){
                onError.invoke(e.localizedMessage, getErrorBody(e.response()))
                return
            }
            if(response.isSuccessful && response.body() != null){
                val baseData = response.body() as List<BaseData>
                if(baseData.isNotEmpty()){
                    baseData[0].saveReceivedTime()
                }
                onSuccess.invoke(baseData)
            }
            else{
                onError.invoke(response.code().toString(), getErrorBody(response))
            }

    }

    fun getErrorBody(response: Response<*>?) : ErrorData?{
        return Gson().fromJson(response?.errorBody()?.charStream(), ErrorData::class.java)
    }
}