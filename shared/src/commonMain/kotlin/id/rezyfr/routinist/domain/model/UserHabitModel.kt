package id.rezyfr.routinist.domain.model

import id.rezyfr.routinist.data.remote.response.UserHabitResponse

data class UserHabitModel(
    val goal: Int,
    val goalFrequency: String,
    val icon: String,
    val id: Long,
    val name: String,
    val unit: UnitModel,
) {
    companion object {
        fun fromResponse(response: UserHabitResponse): UserHabitModel {
            return UserHabitModel(
                goal = response.goal ?: 0,
                goalFrequency = response.goalFrequency.orEmpty(),
                icon = response.icon.orEmpty(),
                id = response.id ?: 0,
                name = response.name.orEmpty(),
                unit = UnitModel.fromResponse(response.unit!!),
            )
        }

        fun generateDummy(): UserHabitModel {
            return UserHabitModel(
                goal = 10,
                goalFrequency = "daily",
                icon = "https://via.placeholder.com/150",
                id = 1,
                name = "Drink Water",
                unit = UnitModel(
                    id = 1,
                    name = "ml",
                    symbol = "ml",
                    measurement = "Milliliter"
                ),
            )
        }
    }
}