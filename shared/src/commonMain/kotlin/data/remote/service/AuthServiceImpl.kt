package data.remote.service

import constants.BASE_URL
import data.remote.ApiClient
import data.remote.HttpClient.httpClient
import data.remote.request.LoginRequest
import data.remote.request.RegisterRequest
import data.remote.response.BaseResponse
import data.remote.response.NetworkResponse
import data.remote.response.TokenResponse
import data.util.execute
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.path
import io.ktor.http.takeFrom

class AuthServiceImpl(
    private val apiClient: ApiClient
) : AuthService {
    override suspend fun login(
        email: String,
        password: String
    ): NetworkResponse<BaseResponse<TokenResponse?>> {
        return execute {
            apiClient.post(
                endpoint = "api/v1/auth/login",
                body = LoginRequest(email = email, password = password)
            )
        }
    }

    override suspend fun register(
        name: String,
        email: String,
        password: String,
        gender: String,
        habitId: Int
    ): NetworkResponse<BaseResponse<TokenResponse?>> {
        return execute {
            apiClient.post(
                endpoint = "api/v1/auth/register",
                body = RegisterRequest(
                    email = email,
                    name = name,
                    password = password,
                    gender = gender,
                    habitId = habitId
                )
            )
        }
    }
}