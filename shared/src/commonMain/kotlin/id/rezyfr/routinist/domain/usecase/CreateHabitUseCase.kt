package id.rezyfr.routinist.domain.usecase

import id.rezyfr.routinist.domain.UiResult
import id.rezyfr.routinist.domain.handleResult
import id.rezyfr.routinist.domain.repo.HabitRepository

class CreateHabitUseCase(
    private val habitRepository: HabitRepository
) : UseCase<UiResult<String>, CreateHabitUseCase.Params>  {

    override suspend fun execute(params: Params): UiResult<String> {
        return handleResult(
            execute = {
                habitRepository.createHabit(params.habitId, params.unitId, params.goal)
            },
            onSuccess = {
                it
            }
        )
    }

    data class Params(
        val habitId: Long,
        val unitId: Long,
        val goal: Float
    )
}