package di


import com.russhwolf.settings.Settings
import common.Context
import constants.BASE_URL
import data.remote.ApiClient
import data.remote.HttpClient
import data.remote.service.AuthService
import data.remote.service.AuthServiceImpl
import data.remote.service.HabitService
import data.remote.service.HabitServiceImpl
import data.repo.AuthRepositoryImpl
import data.repo.HabitRepositoryImpl
import domain.repo.AuthRepository
import domain.repo.HabitRepository
import domain.usecase.CheckTokenUseCase
import domain.usecase.CreateProgressUseCase
import domain.usecase.GetHabitSummaryUseCase
import domain.usecase.GetRandomHabitUseCase
import domain.usecase.GetTodayHabitsUseCase
import domain.usecase.LoginUseCase
import domain.usecase.RegisterUseCase
import kotlinx.serialization.json.Json
import org.koin.dsl.module
import presentation.ui.habit.create.CreateHabitViewModel
import presentation.ui.main.MainViewModel
import presentation.ui.main.home.HomeViewModel
import presentation.ui.onboarding.OnBoardingViewModel
import presentation.ui.onboarding.login.LoginViewModel
import presentation.ui.onboarding.register.RegisterViewModel

fun appModule() = module {
    single { Json { isLenient = true; ignoreUnknownKeys = true } }
    single<Settings> { Settings() }
    single { HttpClient.httpClient() }
    single { ApiClient(get(), get(), BASE_URL) }
    single<AuthService> { AuthServiceImpl(get()) }
    single<HabitService> { HabitServiceImpl(get()) }

    single<AuthRepository> { AuthRepositoryImpl(get(), get()) }
    single<HabitRepository> { HabitRepositoryImpl(get()) }

    factory { RegisterUseCase(get()) }
    factory { LoginUseCase(get()) }
    factory { GetRandomHabitUseCase(get()) }
    factory { GetHabitSummaryUseCase(get()) }
    factory { GetTodayHabitsUseCase(get()) }
    factory { CreateProgressUseCase(get())}
    factory { CheckTokenUseCase(get())}

    factory { LoginViewModel(get(),get()) }
    factory { RegisterViewModel(get(),get())}
    factory { HomeViewModel(get(),get()) }
    factory { MainViewModel(get(),get()) }
    factory { OnBoardingViewModel(get()) }
    factory { CreateHabitViewModel(get()) }
}