package di


import data.local.AppDataStore
import data.local.AppDataStoreManager
import common.Context
import constants.BASE_URL
import data.remote.HttpClient
import data.remote.service.AuthService
import data.remote.service.AuthServiceImpl
import data.remote.service.HabitService
import data.remote.service.HabitServiceImpl
import data.repo.AuthRepositoryImpl
import data.repo.HabitRepositoryImpl
import domain.repo.AuthRepository
import domain.repo.HabitRepository
import domain.usecase.GetRandomHabitUseCase
import domain.usecase.LoginUseCase
import domain.usecase.RegisterUseCase
import kotlinx.serialization.json.Json
import org.koin.dsl.module
import presentation.ui.onboarding.login.LoginViewModel
import presentation.ui.onboarding.register.RegisterViewModel

fun appModule(context: Context?) = module {
    single { Json { isLenient = true; ignoreUnknownKeys = true } }
    single {
        HttpClient.httpClient()
    }
    single<AppDataStore> { AppDataStoreManager(context) }
    single<AuthService> { AuthServiceImpl(get()) }
    single<HabitService> { HabitServiceImpl(get(), BASE_URL) }

    single<AuthRepository> { AuthRepositoryImpl(get(), get()) }
    single<HabitRepository> { HabitRepositoryImpl(get()) }

    factory { RegisterUseCase(get()) }
    factory { LoginUseCase(get()) }
    factory { GetRandomHabitUseCase(get()) }

    factory { LoginViewModel(get()) }
    factory { RegisterViewModel(get(),get())}
}