package id.rezyfr.routinist.domain.usecase

import id.rezyfr.routinist.domain.UiResult
import id.rezyfr.routinist.domain.handleResult
import id.rezyfr.routinist.domain.repo.AuthRepository
import id.rezyfr.routinist.presentation.ui.onboarding.register.Gender

class RegisterUseCase(
    private val authRepository: AuthRepository
) : UseCase<UiResult<Unit>, RegisterUseCase.Params> {
    override suspend fun execute(params: Params): UiResult<Unit> {
        return handleResult(
            execute = {
                authRepository.register(
                    params.name,
                    params.email,
                    params.password,
                    params.gender.name.lowercase(),
                    params.habitId
                )
            },
            onSuccess = {
                if (it?.token != null) {
                    authRepository.saveToken(it.token)
                }
            }
        )
    }

    data class Params(
        val email: String,
        val password: String,
        val name: String,
        val habitId: Int,
        val gender: Gender
    )
}