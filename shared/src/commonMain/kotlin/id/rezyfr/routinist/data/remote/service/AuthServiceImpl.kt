package id.rezyfr.routinist.data.remote.service

import id.rezyfr.routinist.data.remote.ApiClient
import id.rezyfr.routinist.data.remote.request.LoginRequest
import id.rezyfr.routinist.data.remote.request.RegisterRequest
import id.rezyfr.routinist.data.remote.response.BaseResponse
import id.rezyfr.routinist.data.remote.response.NetworkResponse
import id.rezyfr.routinist.data.remote.response.TokenResponse
import id.rezyfr.routinist.data.util.execute

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