package com.norbert.koller.shared.api

import androidx.lifecycle.LifecycleCoroutineScope
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.norbert.koller.shared.data.ErrorData
import kotlinx.coroutines.CoroutineScope
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
        .connectTimeout(timeout, TimeUnit.SECONDS)
        .writeTimeout(timeout, TimeUnit.SECONDS)
        .readTimeout(timeout, TimeUnit.SECONDS)
        .addInterceptor(AuthInterceptor())
        .addInterceptor(loggingInterceptor)
        .authenticator(AuthenticationManager())
        .build()

    val ip = "https://koller.tmtk.hu/"
    val tempIP = ""

    val api : APIInterface by lazy {
        Retrofit.Builder()
            .baseUrl(ip)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(APIInterface::class.java)
    }

    fun communicate(lifecycleCoroutineScope: CoroutineScope, functionToCall: suspend () -> Response<*>, onSuccess: (data : Any) -> Unit, onError: (error : String?, errorBody : ErrorData?) -> Unit){

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
                onSuccess.invoke(response.body()!!)
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
                onSuccess.invoke(response.body()!!)
            }
            else{
                onError.invoke(response.code().toString(), getErrorBody(response))
            }

    }

    fun getErrorBody(response: Response<*>?) : ErrorData?{

        val errorString = response?.errorBody()?.charStream()

        return try {
            Gson().fromJson(errorString, ErrorData::class.java)
        } catch (e: IllegalStateException) {
            ErrorData("Not JSON", e.toString())
        } catch (e: JsonSyntaxException) {
            ErrorData("Not JSON", e.toString())
        }
    }
}