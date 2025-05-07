package presentation.component.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import domain.model.HabitProgressModel
import presentation.component.core.IconButton
import presentation.component.core.Spacer_12dp
import presentation.theme.Black10
import presentation.theme.Black40
import presentation.util.noDecimal

@Composable
fun HabitProgressItem(
    modifier: Modifier = Modifier,
    data: HabitProgressModel,
    onClickAdd: (HabitProgressModel) -> Unit
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, Black10)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
        ) {
            Box(Modifier.size(36.dp), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(
                    gapSize = 0.dp,
                    progress = {
                        data.progress / data.goal
                    },
                    trackColor = Black10,
                    color = MaterialTheme.colorScheme.primary,
                    strokeWidth = 2.dp
                )
                Text(data.icon, style = MaterialTheme.typography.bodyMedium)
            }
            Spacer_12dp()
            Column(Modifier.weight(1f)) {
                Text(
                    text = data.name,
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = "${data.progress.noDecimal()}/${data.goal.noDecimal()} ${data.unit}",
                    style = MaterialTheme.typography.labelMedium,
                    color = Black40
                )
            }

            IconButton(
                modifier = Modifier.size(36.dp),
                imageVector = Icons.Default.Add
            ) {
                onClickAdd.invoke(data)
            }
        }
    }
}