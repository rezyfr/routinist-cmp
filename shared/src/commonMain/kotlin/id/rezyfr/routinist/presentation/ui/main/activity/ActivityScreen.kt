package id.rezyfr.routinist.presentation.ui.main.activity

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import id.rezyfr.routinist.domain.model.ActivitySummaryModel
import id.rezyfr.routinist.presentation.component.core.DefaultScreenUI
import id.rezyfr.routinist.presentation.component.core.IconButton
import id.rezyfr.routinist.presentation.component.core.Spacer_12dp
import id.rezyfr.routinist.presentation.component.core.Spacer_8dp
import id.rezyfr.routinist.presentation.component.ui.TextLabelVertical
import id.rezyfr.routinist.presentation.theme.AppTheme
import id.rezyfr.routinist.presentation.theme.Black40
import id.rezyfr.routinist.presentation.theme.Black60
import id.rezyfr.routinist.presentation.theme.BorderColor
import id.rezyfr.routinist.presentation.theme.DarkBlue10
import id.rezyfr.routinist.presentation.theme.Green100
import id.rezyfr.routinist.presentation.theme.Red100
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import routinist.shared.generated.resources.Res
import routinist.shared.generated.resources.activity_success_rate
import routinist.shared.generated.resources.activity_completed
import routinist.shared.generated.resources.activity_failed
import routinist.shared.generated.resources.activity_points_earned
import routinist.shared.generated.resources.activity
import routinist.shared.generated.resources.date
import routinist.shared.generated.resources.summary

@Composable
fun ActivityScreen() {
    DefaultScreenUI(
        titleToolbar = stringResource(Res.string.activity),
        toolbarContent = {
            DateRangeSelector(
                Modifier.background(Color.White).padding(top = 12.dp, start = 24.dp, end = 24.dp, bottom = 16.dp)
            )
        },
    ) {
        Column(Modifier.fillMaxSize().padding(horizontal = 24.dp, vertical = 12.dp)) {
            ActivitySummaryCard(
                Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun DateRangeSelector(
    modifier: Modifier = Modifier,
    date: String = "May 28 - Jun 3"
) {
    Row(
        modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(stringResource(Res.string.date), style = MaterialTheme.typography.bodyLarge)
            Text(date, style = MaterialTheme.typography.bodyMedium, color = Black60)
        }
        Row() {
        }
    }
}

@Composable
fun ActivitySummaryCard(
    modifier: Modifier = Modifier,
    data: ActivitySummaryModel = ActivitySummaryModel(
        successRate = 98f,
        completed = 244,
        failed = 2,
        pointsEarned = 312,
        name = "All Summary",
        icon = "\uD83D\uDC40"
    )
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, BorderColor),
        colors = CardDefaults.cardColors(containerColor = Color.White),
    ) {
        Column(Modifier.fillMaxWidth().padding(16.dp)) {
            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Box(
                    Modifier.size(36.dp).background(DarkBlue10, RoundedCornerShape(8.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(data.icon, style = MaterialTheme.typography.titleMedium)
                }
                Spacer_12dp()
                Column(Modifier.weight(1f)) {
                    Text(data.name, style = MaterialTheme.typography.bodyLarge)
                    Text(
                        stringResource(Res.string.summary),
                        style = MaterialTheme.typography.labelMedium,
                        color = Black40
                    )
                }
                Spacer_12dp()
                IconButton(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    shape = MaterialTheme.shapes.small,
                    size = 36.dp
                ) {
                }
            }
            Spacer_12dp()
            Row(Modifier.fillMaxWidth()) {
                TextLabelVertical(
                    modifier = Modifier.weight(1f),
                    label = stringResource(Res.string.activity_success_rate),
                    data = "${data.successRate}%",
                    dataColor = Green100
                )
                TextLabelVertical(
                    modifier = Modifier.weight(1f),
                    label = stringResource(Res.string.activity_points_earned),
                    data = data.pointsEarned.toString(),
                )
            }
            Spacer_8dp()
            Row(Modifier.fillMaxWidth()) {
                TextLabelVertical(
                    modifier = Modifier.weight(1f),
                    label = stringResource(Res.string.activity_completed),
                    data = data.completed.toString(),
                )
                TextLabelVertical(
                    modifier = Modifier.weight(1f),
                    label = stringResource(Res.string.activity_failed),
                    data = data.failed.toString(),
                    dataColor = Red100
                )
            }
        }
    }
}
@Preview
@Composable
fun ActivityScreenPreview() {
    AppTheme {
        ActivityScreen()
    }
}