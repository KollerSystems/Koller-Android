package com.norbert.koller.shared.managers

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import com.norbert.koller.shared.data.BaseData
import com.norbert.koller.shared.data.ExpiringListData
import com.norbert.koller.shared.data.LoginTokensData
import com.norbert.koller.shared.data.StudyGroupTypeData
import com.norbert.koller.shared.data.UserData
import com.squareup.picasso.Cache
import kotlinx.coroutines.flow.first


class DataStoreManager {


    companion object {

        val LOGIN_DATA_NAME = "login_data"
        val TIP_DATA_NAME = "tip_data"
        val USER = stringPreferencesKey("current_user")
        val TOKENS = stringPreferencesKey("tokens")
        val ROOM_PRESENCE_KNOWS_THE_LAYOUT = intPreferencesKey("room_presence_knows_the_layout")
        val Context.loginDataStore: DataStore<Preferences> by preferencesDataStore(name = LOGIN_DATA_NAME)
        val Context.tipDataStore: DataStore<Preferences> by preferencesDataStore(name = TIP_DATA_NAME)
        val Context.userDataStore: DataStore<Preferences> by preferencesDataStore(name = "User")


        suspend fun saveCache(context: Context){
            val gson = Gson()

            context.userDataStore.edit {
                Log.d("MENTÉS START","")
                for ((key, baseData) in CacheManager.detailsDataMap){
                    val json = gson.toJson(baseData)

                        it[stringPreferencesKey("${key.first}:${key.second}")] = json


                }


                for((key, baseDataList) in CacheManager.listDataMap){

                    val json = gson.toJson(baseDataList)

                        it[stringPreferencesKey(key)] = json


                }


                if(CacheManager.userData.uid != -1){
                    val json = gson.toJson(CacheManager.userData)

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
            if(CacheManager.userData.uid != -1) {
                context.userDataStore.edit {
                    it[USER] = gson.toJson(CacheManager.userData)
                }
            }
        }

        suspend fun readDetail(context: Context, id: Int, classOfT : Class<*>) : BaseData?{
            val json = context.userDataStore.data.first()[stringPreferencesKey("${classOfT.simpleName}:$id")]
            Log.d("DETZAIL REQUERST", "Context: $context, ID: $id, ClassOfT: $classOfT, Json: $json")
            if(json == null) return null

            val gson = Gson()
            return gson.fromJson(json, classOfT) as BaseData
        }

        suspend fun readList(context: Context, classOfT : Class<*>) : ExpiringListData?{
            val json = context.userDataStore.data.first()[stringPreferencesKey(classOfT.simpleName)]
            if(json == null) return null

            val gson = Gson()
            return gson.fromJson(json, ExpiringListData::class.java)
        }
    }
}

