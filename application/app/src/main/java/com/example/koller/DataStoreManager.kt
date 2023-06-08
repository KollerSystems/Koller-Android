package com.example.koller

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first


class DataStoreManager {


    companion object {

        val LOGIN_DATA_NAME = "login_data"
        val REFRESH_TOKEN_NAME = "refresh_token"
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = LOGIN_DATA_NAME)

        suspend fun save(context: Context, key: String, value: String) {
            val dataStoreKey = stringPreferencesKey(key)
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

    }
}

