package domain.usecase

import domain.UiResult
import domain.handleResult
import domain.repo.AuthRepository
import presentation.ui.onboarding.register.Gender

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