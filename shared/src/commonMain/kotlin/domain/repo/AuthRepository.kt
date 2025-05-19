package domain.repo

import data.remote.response.TokenResponse

interface AuthRepository {
    suspend fun login(email: String, password: String) : Result<TokenResponse?>
    suspend fun saveToken(token: String)
    suspend fun register(name: String, email: String, password: String, gender: String, habitId: Int) : Result<TokenResponse?>
    suspend fun saveEmail(email: String)
    suspend fun checkToken() : Result<String?>
}