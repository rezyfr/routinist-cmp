package id.rezyfr.routinist.domain.usecase

import id.rezyfr.routinist.domain.UiResult
import id.rezyfr.routinist.domain.handleResult
import id.rezyfr.routinist.domain.model.UserHabitModel
import id.rezyfr.routinist.domain.repo.HabitRepository

class GetTodayHabitsUseCase(
    private val repository: HabitRepository
) : UseCase<List<UserHabitModel>, Unit> {
    override suspend fun execute(params: Unit): UiResult<List<UserHabitModel>> {
        return handleResult(
            execute = {
                repository.getTodayHabits()
            },
            onSuccess = {
                it.map {
                    UserHabitModel.Companion.fromResponse(it)
                }
            }
        )
    }
}