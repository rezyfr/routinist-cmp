package id.rezyfr.routinist.presentation.component.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.stringResource
import routinist.shared.generated.resources.Res
import routinist.shared.generated.resources.view_all

@Composable
fun TextViewAll(
    text: String,
    modifier: Modifier = Modifier,
    onViewAllClicked: (() -> Unit)? = null
) {
    Row(modifier, horizontalArrangement = Arrangement.SpaceBetween) {
        Text(text, style = MaterialTheme.typography.bodyLarge)
        Text(
            stringResource(Res.string.view_all),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.clickable(true){
                onViewAllClicked?.invoke()
            }
        )
    }
}