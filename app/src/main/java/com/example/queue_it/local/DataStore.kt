package com.example.queue_it.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.Flow

object LocalStorage {

    val Context.tokens: DataStore<Preferences> by preferencesDataStore(name = "tokens")

    val USER_TOKEN = stringPreferencesKey("user_token")
    val BUSINESS_TOKEN = stringPreferencesKey("business_token")
    val CUSTOMER_TOKEN = stringPreferencesKey("customer_token")

    suspend fun saveUserToken(context: Context, token: String) {
        context.tokens.edit {
            it[USER_TOKEN] = token
        }
    }

    fun getUserToken(context: Context): Flow<String> {
        return context.tokens.data.map {
            it[USER_TOKEN] ?: "none"
        }
    }

    suspend fun saveBusinessToken(context: Context, token: String) {
        context.tokens.edit {
            it[BUSINESS_TOKEN] = token
        }
    }

    fun getBusinessToken(context: Context) : Flow<String> {
        return context.tokens.data.map {
            it[BUSINESS_TOKEN] ?: "none"
        }
    }

    suspend fun saveCustomerToken(context: Context, token: String) {
        context.tokens.edit {
            it[CUSTOMER_TOKEN] = token
        }
    }

    fun getCustomerToken(context: Context) : Flow<String> {
        return context.tokens.data.map {
            it[CUSTOMER_TOKEN] ?: "none"
        }
    }
}