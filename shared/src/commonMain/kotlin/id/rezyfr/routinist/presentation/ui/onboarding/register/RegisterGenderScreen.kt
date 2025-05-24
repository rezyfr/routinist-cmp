package id.rezyfr.routinist.presentation.ui.onboarding.register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import id.rezyfr.routinist.presentation.component.core.Spacer_16dp
import id.rezyfr.routinist.presentation.component.ui.SelectableCard
import routinist.shared.generated.resources.Res
import routinist.shared.generated.resources.register_gender
import routinist.shared.generated.resources.male
import routinist.shared.generated.resources.female
import routinist.shared.generated.resources.unspecified

@Composable
fun RegisterGenderScreen(
    modifier: Modifier = Modifier,
    state: RegisterState,
    events: (RegisterEvent) -> Unit = {}
) {
    Column(modifier) {
        Text(
            stringResource(Res.string.register_gender),
            style = MaterialTheme.typography.titleMedium
        )
        Spacer_16dp()
        Column {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(24.dp)) {
                SelectableCard<Gender>(
                    modifier = Modifier.weight(1f),
                    emoji = "\uD83E\uDD26\u200D♂\uFE0F",
                    label = stringResource(Res.string.male),
                    id = Gender.MALE,
                    isSelected = state.gender == Gender.MALE,
                    onClick = { events(RegisterEvent.OnGenderChange(Gender.MALE)) }
                )
                SelectableCard<Gender>(
                    modifier = Modifier.weight(1f),
                    id = Gender.FEMALE,
                    emoji = "\uD83E\uDD26\u200D♀\uFE0F",
                    label = stringResource(Res.string.female),
                    isSelected = state.gender == Gender.FEMALE,
                    onClick = { events(RegisterEvent.OnGenderChange(Gender.FEMALE)) }
                )
            }
            Spacer_16dp()
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(24.dp)) {
                SelectableCard<Gender>(
                    modifier = Modifier.weight(0.5f),
                    id = Gender.UNSPECIFIED,
                    emoji = "✨",
                    label = stringResource(Res.string.unspecified),
                    isSelected = state.gender == Gender.UNSPECIFIED,
                    onClick = { events(RegisterEvent.OnGenderChange(Gender.UNSPECIFIED)) }
                )
                Box(Modifier.weight(0.5f))
            }
        }
    }
}

enum class Gender {
    MALE,
    FEMALE,
    UNSPECIFIED
}