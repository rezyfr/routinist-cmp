package id.rezyfr.routinist.data.remote.service

import id.rezyfr.routinist.data.remote.response.BaseResponse
import id.rezyfr.routinist.data.remote.response.NetworkResponse
import id.rezyfr.routinist.data.remote.response.TokenResponse

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