package domain.model

data class HabitProgressModel(
    val id: Int,
    val goal: Float,
    val icon: String,
    val unit: String,
    val name: String,
    val progress: Float,
)
