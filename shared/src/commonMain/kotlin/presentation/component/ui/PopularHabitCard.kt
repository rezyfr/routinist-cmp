package presentation.component.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import domain.model.HabitModel
import domain.model.UnitModel
import org.jetbrains.compose.ui.tooling.preview.Preview
import presentation.component.core.Spacer_8dp
import presentation.theme.AppTheme
import presentation.theme.Black10
import presentation.theme.Black100
import presentation.theme.Black60

@Composable
fun PopularHabitCard(
    modifier: Modifier = Modifier,
    isSelected: Boolean,
    habit: HabitModel,
    onClick: (Int) -> Unit,
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = modifier,
        onClick = {
            onClick.invoke(habit.id)
        },
        colors = CardDefaults.cardColors(
            containerColor = Color(habit.color),
            contentColor = Black100
        ),
        border = if (isSelected) BorderStroke(
            2.dp,
            MaterialTheme.colorScheme.primary
        ) else BorderStroke(1.dp, Black10)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(24.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Box(
                Modifier.background(Color.White, RoundedCornerShape(12.dp))
                    .padding(vertical = 4.dp, horizontal = 6.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(text = habit.icon, style = MaterialTheme.typography.headlineSmall)
            }
            Spacer_8dp()
            Text(text = habit.name, style = MaterialTheme.typography.bodyLarge)
            Text(
                text = "${habit.defaultGoal} ${habit.units.first().symbol}",
                style = MaterialTheme.typography.labelMedium.copy(color = Black60)
            )
        }
    }
}


@Preview()
@Composable
fun PreviewPopularCard() {
    AppTheme {
        PopularHabitCard(
            isSelected = false,
            habit = HabitModel(
                id = 1,
                name = "Sleep",
                icon = "🛌",
                units = listOf(
                    UnitModel(
                        id = 1,
                        measurement = "distance",
                        symbol = "km",
                        name = "metre"
                    )
                ),
                defaultGoal = 8,
                measurement = "distance",
                color = 0xFFE8D3FF
            ),
            onClick = {
            },
        )
    }
}