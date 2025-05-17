package domain.model

import data.remote.response.UserHabitResponse

data class UserHabitModel(
    val goal: Int,
    val goalFrequency: String,
    val icon: String,
    val id: Int,
    val name: String,
    val progress: Int,
    val unit: UnitModel,
    val createdAt: String
) {
    companion object {
        fun fromResponse(response: UserHabitResponse): UserHabitModel {
            return UserHabitModel(
                goal = response.goal ?: 0,
                goalFrequency = response.goalFrequency.orEmpty(),
                icon = response.icon.orEmpty(),
                id = response.id ?: 0,
                name = response.name.orEmpty(),
                progress = response.progress ?: 0,
                unit = UnitModel.fromResponse(response.unit!!),
                createdAt = response.createdAt.orEmpty()
            )
        }
    }
}