package id.rezyfr.routinist.data.repo

import com.russhwolf.settings.Settings
import id.rezyfr.routinist.constants.SettingsConstant
import id.rezyfr.routinist.data.remote.response.TokenResponse
import id.rezyfr.routinist.data.remote.response.handleResponse
import id.rezyfr.routinist.data.remote.service.AuthService
import id.rezyfr.routinist.domain.repo.AuthRepository

class AuthRepositoryImpl(
    private val authService: AuthService,
    private val settings: Settings
) : AuthRepository {
    override suspend fun login(email: String, password: String) : Result<TokenResponse?> {
        return authService.login(email, password).handleResponse()
    }
    override suspend fun saveToken(token: String) {
        settings.putString(SettingsConstant.ACCESS_TOKEN, token)
    }
    override suspend fun register(
        name: String,
        email: String,
        password: String,
        gender: String,
        habitId: Int
    ): Result<TokenResponse?> {
        return authService.register(name, email, password, gender, habitId).handleResponse()
    }
    override suspend fun saveEmail(email: String) {
        settings.putString(SettingsConstant.EMAIL, email)
    }

    override suspend fun checkToken(): Result<String?> {
        val response = authService.checkToken().handleResponse()
        if (response.isFailure) {
            settings.remove(SettingsConstant.ACCESS_TOKEN)
        }
        return response
    }
}