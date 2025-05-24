package id.rezyfr.routinist.domain.usecase

import id.rezyfr.routinist.domain.UiResult
import id.rezyfr.routinist.domain.handleResult
import id.rezyfr.routinist.domain.repo.AuthRepository

class LoginUseCase(
    private val authRepository: AuthRepository
) : UseCase<UiResult<Unit>, Pair<String, String>> {
    override suspend fun execute(params: Pair<String, String>): UiResult<Unit> {
        return handleResult(
            execute = { authRepository.login(params.first, params.second) },
            onSuccess = {
                if (it?.token != null) {
                    authRepository.saveToken(it.token)
                    authRepository.saveEmail(params.first)
                }
            }
        )
    }
}