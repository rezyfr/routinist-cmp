package data.remote.service

import data.remote.ApiClient
import data.remote.request.LoginRequest
import data.remote.request.RegisterRequest
import data.remote.response.BaseResponse
import data.remote.response.NetworkResponse
import data.remote.response.TokenResponse
import data.util.execute

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
                auth = false,
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
                auth = false,
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

    override suspend fun checkToken(): NetworkResponse<BaseResponse<String?>> {
        return execute {
            apiClient.get(
                endpoint = "api/v1/auth/protected/check"
            )
        }
    }
}