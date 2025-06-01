package id.rezyfr.routinist.domain.usecase

import id.rezyfr.routinist.domain.UiResult
import id.rezyfr.routinist.domain.handleResult
import id.rezyfr.routinist.domain.model.UserHabitModel
import id.rezyfr.routinist.domain.repo.HabitRepository

class GetUserHabitsUseCase(
    private val repository: HabitRepository
) : UseCase<List<UserHabitModel>, Unit> {
    override suspend fun execute(params: Unit): UiResult<List<UserHabitModel>> {
        return handleResult(
            execute = {
                repository.getUserHabits()
            },
            onSuccess = {
                it.map {
                    UserHabitModel.fromResponse(it)
                }
            }
        )
    }
}