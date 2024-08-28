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
import kotlinx.coroutines.flow.first


class DataStoreManager {


    companion object {

        val LOGIN_DATA_NAME = "login_data"
        val TOKENS = "tokens"
        val ROOM_PRESENCE_KNOWS_THE_LAYOUT = "room_presence_knows_the_layout"
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = LOGIN_DATA_NAME)



        suspend fun saveCache(context: Context){
            for ((key, baseData) in CacheManager.savedValues){
                val gson = Gson()
                val json = gson.toJson(baseData)
                save(context, "${key.first}:${key.second}", json)
            }

            for((key, baseDataList) in CacheManager.savedListsOfValues){
                val gson = Gson()
                val json = gson.toJson(baseDataList)
                save(context, key, json)
            }
        }

        suspend fun save(context: Context, key: String, value: String) {
            val dataStoreKey = stringPreferencesKey(key)
            context.dataStore.edit { login_data ->
                login_data[dataStoreKey] = value

            }
        }

        suspend fun readTokens(context: Context): LoginTokensData? {
            val dataStoreKey = stringPreferencesKey(TOKENS)
            val preferences = context.dataStore.data.first()
            return Gson().fromJson(preferences[dataStoreKey], LoginTokensData::class.java)
        }

        suspend fun save(context: Context, tokensData: LoginTokensData) {
            val dataStoreKey = stringPreferencesKey(TOKENS)
            context.dataStore.edit { login_data ->
                login_data[dataStoreKey] = Gson().toJson(tokensData)

            }
        }

        suspend fun save(context: Context, key: String, value: Boolean) {
            val dataStoreKey = booleanPreferencesKey(key)
            context.dataStore.edit { login_data ->
                login_data[dataStoreKey] = value

            }
        }

        suspend fun save(context: Context, key: String, value: Int) {
            val dataStoreKey = intPreferencesKey(key)
            context.dataStore.edit { login_data ->
                login_data[dataStoreKey] = value

            }
        }

        suspend fun remove(context: Context, key: String) {
            val dataStoreKey = stringPreferencesKey(key)
            context.dataStore.edit { login_data ->
                login_data.remove(dataStoreKey)

            }
        }

        suspend fun read(context: Context, key: String): String? {
            val dataStoreKey = stringPreferencesKey(key)
            val preferences = context.dataStore.data.first()
            return preferences[dataStoreKey]
        }

        suspend fun readDetail(context: Context, key: String, id: Int, classOfT : Class<*>) : BaseData?{
            val json = read(context, "$key:$id")
            if(json == null) return null

            val gson = Gson()
            return gson.fromJson(json, classOfT) as BaseData
        }

        suspend fun readList(context: Context, key: String, classOfT : Class<*>) : Array<BaseData>?{
            val json = read(context, key)
            if(json == null) return null

            val gson = Gson()
            return gson.fromJson(json, classOfT) as Array<BaseData>
        }

        suspend fun readBoolean(context: Context, key: String): Boolean? {
            val dataStoreKey = booleanPreferencesKey(key)
            val preferences = context.dataStore.data.first()
            return preferences[dataStoreKey]
        }

        suspend fun readInt(context: Context, key: String): Int? {
            val dataStoreKey = intPreferencesKey(key)
            val preferences = context.dataStore.data.first()
            return preferences[dataStoreKey]
        }

    }
}

