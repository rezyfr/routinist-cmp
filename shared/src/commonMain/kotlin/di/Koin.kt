package di


import data.local.AppDataStore
import data.local.AppDataStoreManager
import common.Context
import data.remote.HttpClient
import data.remote.service.AuthService
import data.remote.service.AuthServiceImpl
import data.repo.AuthRepositoryImpl
import domain.repo.AuthRepository
import domain.usecase.LoginUseCase
import kotlinx.serialization.json.Json
import org.koin.dsl.module
import presentation.ui.onboarding.login.LoginViewModel

fun appModule(context: Context?) = module {
    single { Json { isLenient = true; ignoreUnknownKeys = true } }
    single {
        HttpClient.httpClient()
    }
    single<AppDataStore> { AppDataStoreManager(context) }
    single<AuthService> { AuthServiceImpl(get()) }

    single<AuthRepository> { AuthRepositoryImpl(get(), get()) }

    single { LoginUseCase(get()) }

    factory { LoginViewModel(get()) }
}