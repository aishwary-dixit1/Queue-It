package com.example.queue_it.network

import android.content.Context
import android.util.Log
import com.example.queue_it.local.LocalStorage
import com.example.queue_it.model.Business
import com.example.queue_it.model.Event
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
import io.ktor.http.HttpStatusCode.Companion.Forbidden
import io.ktor.http.HttpStatusCode.Companion.NotFound
import io.ktor.http.HttpStatusCode.Companion.Unauthorized
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.first
import kotlinx.serialization.json.Json

object NetworkClient {

    const val BASE_URL = "http://10.0.2.2:9500"

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

    suspend fun loginRequest(context: Context, userDetails: User) {
        val url = makePath("/user/login")
        val response = client.post(url) {
            contentType(ContentType.Application.Json)
            setBody(userDetails)
        }

        if (response == BadRequest) throw RuntimeException("Bad request")
        if (response == NotFound) throw RuntimeException("Not Found")
        if (response == Forbidden) throw RuntimeException("Forbidden")
        LocalStorage.saveUserToken(context, response.bodyAsText())
        Log.d("check", response.bodyAsText())
    }

    suspend fun getAllRunningEvents(): List<Event> {
        val url = makePath("/event/current/all")
        val response = client.get(url)
        val queues: List<Event> = response.body()
        return queues
    }

    suspend fun getAllCurrentEventsForCustomer(context: Context): List<Queue> {
        val url = makePath("/events/current")
        val customerToken = LocalStorage.getCustomerToken(context).first()
        val response = client.get(url) {
            headers {
                append(HttpHeaders.Authorization, "Bearer $customerToken")
            }
        }
        val queues: List<Queue> = response.body()
        return queues
    }

    suspend fun registerBusiness(context: Context, business: Business): String {
        val url = makePath("/business/register")
        val userToken = LocalStorage.getUserToken(context).first()
        val response = client.post(url) {
            contentType(ContentType.Application.Json)
            setBody(business)
            headers {
                append(HttpHeaders.Authorization, "Bearer $userToken")
            }
        }

        if (response == Unauthorized) throw IllegalStateException("Unauthorized")
        else if (response == BadRequest) throw RuntimeException("Bad request")

        LocalStorage.saveBusinessToken(context, response.bodyAsText())
        return response.bodyAsText()
    }

    suspend fun loadEventsForBusiness(context: Context): List<Event> {
        val businessToken = LocalStorage.getBusinessToken(context).first()
        val url = makePath("/event/current/business")
        val response = client.get(url) {
            headers {
                append(
                    HttpHeaders.Authorization,
                    "Bearer $businessToken"
                )
            }
        }

        if (response == Unauthorized) throw IllegalStateException("Unauthorized")
        else if (response == BadRequest) throw RuntimeException("Bad request")

        return response.body()
    }

    suspend fun createEvent(context: Context, event: Event) {
        val businessToken = LocalStorage.getBusinessToken(context).first()
        val url = makePath("/event/create")
        val response = client.post(url) {
            headers {
                append(
                    HttpHeaders.Authorization,
                    "Bearer $businessToken"
                )
            }

            contentType(ContentType.Application.Json)
            setBody(event)
        }

        if (response == Unauthorized) throw IllegalStateException("Unauthorized")
        else if (response == Forbidden) throw RuntimeException("Bad request")


    }

    suspend fun getEventById(eventId: Int): Event {
        val url = makePath("/event/$eventId")
        val response = client.get(url)

        if (response == Unauthorized) throw IllegalStateException("Unauthorized")
        else if (response == BadRequest) throw RuntimeException("Bad request")

        return response.body()
    }

    suspend fun getQueuesForEvent(eventId: Int): List<Queue> {
        val url = makePath("/queue/$eventId/all")
        val response = client.get(url)

        if (response == Unauthorized) throw IllegalStateException("Unauthorized")
        else if (response == BadRequest) throw RuntimeException("Bad request")

        return response.body()
    }

    suspend fun addQueueToEvent(queue: Queue, eventId: Int) {
        val url = makePath("/queue/create/$eventId")
        val response = client.post(url) {
            contentType(ContentType.Application.Json)
            setBody(queue)
        }

    }

    private fun makePath(url: String) = "$BASE_URL$url"
}