package id.rezyfr.routinist.di

import com.russhwolf.settings.Settings
import id.rezyfr.routinist.common.sqlDriverFactory
import id.rezyfr.routinist.constants.BASE_URL
import id.rezyfr.routinist.data.local.createDatabase
import id.rezyfr.routinist.data.remote.ApiClient
import id.rezyfr.routinist.data.remote.HttpClient
import id.rezyfr.routinist.data.remote.service.AuthService
import id.rezyfr.routinist.data.remote.service.AuthServiceImpl
import id.rezyfr.routinist.data.remote.service.HabitService
import id.rezyfr.routinist.data.remote.service.HabitServiceImpl
import id.rezyfr.routinist.data.repo.AuthRepositoryImpl
import id.rezyfr.routinist.data.repo.HabitRepositoryImpl
import id.rezyfr.routinist.domain.repo.AuthRepository
import id.rezyfr.routinist.domain.repo.HabitRepository
import id.rezyfr.routinist.domain.usecase.CheckTokenUseCase
import id.rezyfr.routinist.domain.usecase.CreateHabitUseCase
import id.rezyfr.routinist.domain.usecase.CreateProgressUseCase
import id.rezyfr.routinist.domain.usecase.GetActivitySummaryUseCase
import id.rezyfr.routinist.domain.usecase.GetHabitStatsUseCase
import id.rezyfr.routinist.domain.usecase.GetHabitSummaryUseCase
import id.rezyfr.routinist.domain.usecase.GetRandomHabitUseCase
import id.rezyfr.routinist.domain.usecase.GetTodayHabitProgressesUseCase
import id.rezyfr.routinist.domain.usecase.GetUserHabitsUseCase
import id.rezyfr.routinist.domain.usecase.LoginUseCase
import id.rezyfr.routinist.domain.usecase.RegisterUseCase
import id.rezyfr.routinist.presentation.ui.create.CreateHabitViewModel
import id.rezyfr.routinist.presentation.ui.main.MainViewModel
import id.rezyfr.routinist.presentation.ui.main.activity.ActivityViewModel
import id.rezyfr.routinist.presentation.ui.main.home.HomeViewModel
import id.rezyfr.routinist.presentation.ui.onboarding.OnBoardingViewModel
import id.rezyfr.routinist.presentation.ui.onboarding.login.LoginViewModel
import id.rezyfr.routinist.presentation.ui.onboarding.register.RegisterViewModel
import kotlinx.serialization.json.Json
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

fun initKoin(
    enableNetworkLogs: Boolean = false,
    appDeclaration: KoinAppDeclaration = {},
) = startKoin {
    appDeclaration()
    modules(appModule())
}

fun appModule() = module {
    factory { sqlDriverFactory() }
    single { createDatabase(get()) }
    single { Json { isLenient = true; ignoreUnknownKeys = true } }
    single<Settings> { Settings() }
    single { HttpClient.httpClient() }
    single { ApiClient(get(), get(), BASE_URL) }
    single<AuthService> { AuthServiceImpl(get()) }
    single<HabitService> { HabitServiceImpl(get()) }

    single<AuthRepository> { AuthRepositoryImpl(get(), get()) }
    single<HabitRepository> { HabitRepositoryImpl(get(), get()) }

    factory { RegisterUseCase(get()) }
    factory { LoginUseCase(get()) }
    factory { GetRandomHabitUseCase(get()) }
    factory { GetHabitSummaryUseCase(get()) }
    factory { GetTodayHabitProgressesUseCase(get()) }
    factory { CreateProgressUseCase(get()) }
    factory { CheckTokenUseCase(get()) }
    factory { CreateHabitUseCase(get()) }
    factory { GetActivitySummaryUseCase(get()) }
    factory { GetUserHabitsUseCase(get()) }
    factory { GetHabitStatsUseCase(get()) }

    factory { LoginViewModel(get(), get()) }
    factory { RegisterViewModel(get(), get()) }
    factory { HomeViewModel(get(), get(), get()) }
    factory { MainViewModel(get(), get(), get()) }
    factory { OnBoardingViewModel(get()) }
    factory { CreateHabitViewModel() }
    factory { ActivityViewModel(get(), get(), get()) }
}