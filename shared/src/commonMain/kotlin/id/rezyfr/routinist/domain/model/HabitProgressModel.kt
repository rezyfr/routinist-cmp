package id.rezyfr.routinist.domain.model

import id.rezyfr.routinist.data.remote.response.HabitProgressResponse

data class HabitProgressModel(
    val id: Long,
    val goal: Long,
    val goalFrequency: String,
    val icon: String,
    val unit: String,
    val name: String,
    val progress: Float,
) {
    companion object {
        fun fromResponse(response: HabitProgressResponse): HabitProgressModel {
            return HabitProgressModel(
                goal = response.goal ?: 0,
                goalFrequency = response.goalFrequency.orEmpty(),
                icon = response.icon.orEmpty(),
                id = response.id ?: 0,
                name = response.name.orEmpty(),
                progress = response.progress ?: 0f,
                unit = response.unit?.name.orEmpty(),
            )
        }
    }
}
