package id.rezyfr.routinist.domain.model

data class ActivitySummaryModel(
    val name: String,
    val icon: String,
    val successRate: Float,
    val completed: Long,
    val failed: Long,
    val pointsEarned: Long
)
