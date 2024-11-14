package com.example.queue_it.network

import android.content.Context
import android.util.Log
import com.example.queue_it.local.LocalStorage
import com.example.queue_it.model.Queue
import com.example.queue_it.model.User
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode.Companion.BadRequest
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object NetworkClient {

    const val BASE_URL = "http://192.168.214.17:9500"

    val client = HttpClient(CIO) {
        expectSuccess = true
        install(ContentNegotiation) {
            json(
                Json {
                    isLenient = true
                    prettyPrint = true
                }
            )
        }
    }

    suspend fun signUpRequest(context: Context, userDetails: User) {
        val url = makePath("/user/create")
        val response = client.post(url) {
            contentType(ContentType.Application.Json)
            setBody(userDetails)
        }

        if (response.status == BadRequest) throw RuntimeException("Bad request")

        LocalStorage.saveUserToken(context, response.bodyAsText())
        Log.d("check", response.bodyAsText())
    }

    suspend fun getAllCurrentEventsForCustomer(context: Context): List<Queue> {
        val url = makePath("/events/current")
        val response = client.get(url) {
            headers {
                append(HttpHeaders.Authorization, "Bearer ${LocalStorage.getCustomerToken(context)}")
            }
        }
        val queues: List<Queue> = response.body()
        return queues
    }

    private fun makePath(url: String) = "$BASE_URL$url"
}