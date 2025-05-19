package domain.usecase

import domain.UiResult
import domain.handleResult
import domain.repo.AuthRepository

class CheckTokenUseCase (
    private val authRepository: AuthRepository
) : UseCase<UiResult<String>, Unit> {
    override suspend fun execute(params: Unit): UiResult<String> {
        return handleResult(
            execute = { authRepository.checkToken() },
            onSuccess = {
                it.orEmpty()
            }
        )
    }
}