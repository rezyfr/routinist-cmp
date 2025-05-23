package domain.model

data class HabitProgressModel(
    val id: Long,
    val goal: Float,
    val icon: String,
    val unit: String,
    val name: String,
    val progress: Float,
)
