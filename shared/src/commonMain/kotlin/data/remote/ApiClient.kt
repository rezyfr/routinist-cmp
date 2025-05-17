package data.remote

import com.russhwolf.settings.Settings
import constants.SettingsConstant
import data.util.setAuthHeader
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.contentType

class ApiClient internal constructor(
    val httpClient: HttpClient,
    private val settings: Settings,
    private val baseUrl: String,
) {
    internal suspend inline fun <reified T> get(
        endpoint: String,
        auth: Boolean = true,
        crossinline builder: HttpRequestBuilder.() -> Unit = {}
    ): T = httpClient.get {
        if (auth) {
            setAuthHeader(settings.getString(SettingsConstant.ACCESS_TOKEN, ""))
        }
        url("$baseUrl/${endpoint.trimStart('/')}")
        builder()
    }.body()

    internal suspend inline fun <reified T, reified R> post(
        endpoint: String,
        auth: Boolean = true,
        body: R,
        crossinline builder: HttpRequestBuilder.() -> Unit = {}
    ): T = httpClient.post {
        if (auth) {
            setAuthHeader(settings.getString(SettingsConstant.ACCESS_TOKEN, ""))
        }
        url("$baseUrl/${endpoint.trimStart('/')}")
        contentType(ContentType.Application.Json)
        setBody(body)
        builder()
    }.body()
}