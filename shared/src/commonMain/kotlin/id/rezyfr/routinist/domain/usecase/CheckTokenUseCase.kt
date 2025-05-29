package id.rezyfr.routinist.domain.usecase

import id.rezyfr.routinist.domain.UiResult
import id.rezyfr.routinist.domain.handleResult
import id.rezyfr.routinist.domain.repo.AuthRepository

class CheckTokenUseCase (
    private val authRepository: AuthRepository
) : UseCase<String, Unit> {
    override suspend fun execute(params: Unit): UiResult<String> {
        return handleResult(
            execute = { authRepository.checkToken() },
            onSuccess = {
                it.orEmpty()
            }
        )
    }
}