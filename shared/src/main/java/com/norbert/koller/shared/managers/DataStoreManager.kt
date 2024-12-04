package com.norbert.koller.shared.managers

import android.content.Context
import android.preference.PreferenceDataStore
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.core.Serializer
import androidx.datastore.dataStoreFile
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.datastore.preferences.preferencesDataStoreFile
import com.google.gson.Gson
import com.norbert.koller.shared.data.BaseData
import com.norbert.koller.shared.data.ExpiringListData
import com.norbert.koller.shared.data.LoginTokensData
import com.norbert.koller.shared.data.StudyGroupTypeData
import com.norbert.koller.shared.data.UserData
import com.squareup.picasso.Cache
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.first
import java.io.File


class DataStoreManager {


    companion object {

        val LOGIN_DATA_NAME = "login_data"
        val TIP_DATA_NAME = "tip_data"
        val USER = stringPreferencesKey("current_user")
        val TOKENS = stringPreferencesKey("tokens")
        val ROOM_PRESENCE_KNOWS_THE_LAYOUT = intPreferencesKey("room_presence_knows_the_layout")
        val Context.loginDataStore: DataStore<Preferences> by preferencesDataStore(name = LOGIN_DATA_NAME)
        val Context.tipDataStore: DataStore<Preferences> by preferencesDataStore(name = TIP_DATA_NAME)

        fun Context.createDynamicUserDataStore(userID: Int) {
            userDataStore[userID] =  PreferenceDataStoreFactory.create(
                produceFile = { this.preferencesDataStoreFile("user_$userID") },
                scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
            )
        }
        var userDataStore : MutableMap<Int, DataStore<Preferences>?> = mutableMapOf()

        fun getCurrentUserDataStore() : DataStore<Preferences>?{
            return userDataStore[CacheManager.loginData!!.uid]
        }

        fun Context.deleteEveryUserSaveData() {

            val dataStoreDirectory = File(this.filesDir, "datastore")

            dataStoreDirectory.listFiles()?.forEach { file ->

                if (file.name.contains("user")) {
                    val deleted = file.delete()
                    if (deleted) {
                        println("Törölve: ${file.name}")
                    } else {
                        println("Nem sikerült törölni: ${file.name}")
                    }
                }
            }
        }

        suspend fun saveCache(context: Context){
            val gson = Gson()

            getCurrentUserDataStore()?.edit {
                Log.d("MENTÉS START","")
                for ((key, baseData) in CacheManager.detailsDataMap){
                    val json = gson.toJson(baseData)

                        it[stringPreferencesKey("${key.first}:${key.second}")] = json


                }


                for((key, baseDataList) in CacheManager.listDataMap){

                    val json = gson.toJson(baseDataList)

                        it[stringPreferencesKey(key)] = json


                }


                if(CacheManager.getCurrentUserData() != null){
                    val json = gson.toJson(CacheManager.getCurrentUserData())

                    it[USER] = json


                }
                Log.d("MENTÉS END","")

            }



        }

        suspend fun readTokens(context: Context): LoginTokensData? {
            return Gson().fromJson(context.loginDataStore.data.first()[TOKENS], LoginTokensData::class.java)
        }

        suspend fun saveTokens(context: Context) {
            val gson = Gson()
            context.loginDataStore.edit { login_data ->
                login_data[TOKENS] = gson.toJson(CacheManager.loginData!!)
            }
            if(CacheManager.getCurrentUserData() != null) {
                getCurrentUserDataStore()!!.edit {
                    it[USER] = gson.toJson(CacheManager.getCurrentUserData())
                }
            }
        }

        suspend fun readDetail(context: Context, id: Int, classOfT : Class<*>) : BaseData?{
            val json = getCurrentUserDataStore()!!.data.first()[stringPreferencesKey("${classOfT.simpleName}:$id")]
            Log.d("DETZAIL REQUERST", "Context: $context, ID: $id, ClassOfT: $classOfT, Json: $json")
            if(json == null) return null

            val gson = Gson()
            return gson.fromJson(json, classOfT) as BaseData
        }

        suspend fun readList(context: Context, classOfT : Class<*>) : ExpiringListData?{
            val json = getCurrentUserDataStore()!!.data.first()[stringPreferencesKey(classOfT.simpleName)]
            if(json == null) return null

            val gson = Gson()
            return gson.fromJson(json, ExpiringListData::class.java)
        }
    }
}

