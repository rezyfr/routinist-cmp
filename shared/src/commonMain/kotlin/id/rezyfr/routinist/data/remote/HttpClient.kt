package id.rezyfr.routinist.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.observer.ResponseObserver
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json

object HttpClient {
    @OptIn(ExperimentalSerializationApi::class)
    fun httpClient() = HttpClient {
        expectSuccess = false
        install(HttpTimeout) {
            val timeout = 60000L
            connectTimeoutMillis = timeout
            requestTimeoutMillis = timeout
            socketTimeoutMillis = timeout
        }
        
        install(ResponseObserver) {
            onResponse { response ->
                println("AppDebug HTTP ResponseObserver status: ${response.status.value}")
            }
        }
        // TODO Use HTTPResponseValidator to validate the response status code

        install(Logging) {
            //  logger = Logger.DEFAULT
            level = LogLevel.ALL

            logger = object : Logger {
                override fun log(message: String) {
                    println("AppDebug KtorHttpClient message:$message")
                }
            }
        }
        // for refresh token
//        install(Auth) {
//
//            bearer {
//                loadTokens {
//                    BearerTokens(
//                        accessToken = settings.getString(SettingsConstant.ACCESS_TOKEN, ""),
//                        refreshToken = ""
//                    )
//                }
//            }
//        }
        install(ContentNegotiation) {
            json(Json {
                explicitNulls = false
                ignoreUnknownKeys = true
                isLenient = true
                prettyPrint = true
                encodeDefaults = true
                classDiscriminator = "#class"
            })
        }

    }

}