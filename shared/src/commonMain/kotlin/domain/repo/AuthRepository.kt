package domain.repo

import data.remote.response.TokenResponse

interface AuthRepository {
    suspend fun login(email: String, password: String) : Result<TokenResponse?>
    suspend fun saveToken(token: String)
}