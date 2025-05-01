package data.repo

import data.local.AppDataStore
import data.remote.response.TokenResponse
import data.remote.response.handleResponse
import data.remote.service.AuthService
import domain.repo.AuthRepository

class AuthRepositoryImpl(
    private val authService: AuthService,
    private val appDataStore: AppDataStore
) : AuthRepository {
    override suspend fun login(email: String, password: String) : Result<TokenResponse?> {
        return authService.login(email, password).handleResponse()
    }
    override suspend fun saveToken(token: String) {
        appDataStore.setValue("token", token)
    }
}