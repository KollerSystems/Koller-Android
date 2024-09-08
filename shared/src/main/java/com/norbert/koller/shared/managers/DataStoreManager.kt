package com.norbert.koller.shared.managers

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import com.norbert.koller.shared.data.BaseData
import com.norbert.koller.shared.data.LoginTokensData
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

            for ((key, baseData) in CacheManager.savedValues){
                val json = gson.toJson(baseData)
                context.userDataStore.edit {
                    it[stringPreferencesKey("${key.first}:${key.second}")] = json

                }
            }

            for((key, baseDataList) in CacheManager.savedListsOfValues){
                val json = gson.toJson(baseDataList)
                context.userDataStore.edit {
                    it[stringPreferencesKey(key)] = json

                }
            }

            val json = gson.toJson(CacheManager.userData)
            context.userDataStore.edit {
                it[USER] = json

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
            context.userDataStore.edit {
                it[USER] = gson.toJson(CacheManager.userData)
            }
        }

        suspend fun readDetail(context: Context, key: String, id: Int, classOfT : Class<*>) : BaseData?{
            val json = context.userDataStore.data.first()[stringPreferencesKey("$key:$id")]
            if(json == null) return null

            val gson = Gson()
            return gson.fromJson(json, classOfT) as BaseData
        }

        suspend fun readList(context: Context, key: String, classOfT : Class<*>) : Array<BaseData>?{
            val json = context.userDataStore.data.first()[stringPreferencesKey(key)]
            if(json == null) return null

            val gson = Gson()
            return gson.fromJson(json, classOfT) as Array<BaseData>
        }
    }
}

