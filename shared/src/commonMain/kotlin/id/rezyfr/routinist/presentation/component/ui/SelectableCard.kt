package id.rezyfr.routinist.presentation.component.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
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
import id.rezyfr.routinist.presentation.theme.Black10
import id.rezyfr.routinist.presentation.theme.Black100

@Composable
fun <T> SelectableCard(
    modifier: Modifier = Modifier,
    id: T,
    emoji: String,
    label: String,
    isSelected: Boolean,
    onClick: (T) -> Unit = {}
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = modifier,
        onClick = {
            onClick.invoke(id)
        },
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
            contentColor = Black100
        ),
        border = if (isSelected) BorderStroke(
            2.dp,
            MaterialTheme.colorScheme.primary
        ) else BorderStroke(1.dp, Black10)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = emoji, style = MaterialTheme.typography.displayMedium)
            Text(text = label, style = MaterialTheme.typography.bodyLarge)
        }
    }
}
@Composable
fun <T> SelectableText(
    modifier: Modifier = Modifier,
    id: T,
    label: String,
    isSelected: Boolean,
    onClick: (T) -> Unit = {}
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = modifier,
        onClick = {
            onClick.invoke(id)
        },
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
            contentColor = Black100
        ),
        border = if (isSelected) BorderStroke(
            2.dp,
            MaterialTheme.colorScheme.primary
        ) else BorderStroke(1.dp, Black10)
    ) {
        Box(Modifier.padding(horizontal = 16.dp, vertical = 6.dp)) {
            Text(text = label, style = MaterialTheme.typography.bodyLarge)
        }
    }
}