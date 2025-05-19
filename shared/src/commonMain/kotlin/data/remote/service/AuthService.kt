package data.remote.service

import data.remote.response.BaseResponse
import data.remote.response.NetworkResponse
import data.remote.response.TokenResponse

interface AuthService {
    suspend fun login(
        email: String,
        password: String
    ): NetworkResponse<BaseResponse<TokenResponse?>>

    suspend fun register(
        name: String,
        email: String,
        password: String,
        gender: String,
        habitId: Int
    ): NetworkResponse<BaseResponse<TokenResponse?>>

    suspend fun checkToken() : NetworkResponse<BaseResponse<String?>>
}