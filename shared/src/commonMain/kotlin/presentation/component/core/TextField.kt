package presentation.component.core

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import presentation.theme.AppTheme
import presentation.theme.Black20
import presentation.theme.Green100

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultTextField(
    modifier: Modifier = Modifier,
    value: String = "",
    label: String = "",
    hint: String = "",
    interactionSource: InteractionSource? = null,
    suffix: String? = null,
    showClearText: Boolean = true,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    onValueChange: (String) -> Unit = {},
) {
    val interactionSource = interactionSource ?: remember { MutableInteractionSource() }
    val focusRequester = remember { FocusRequester() }
    val isFocused = remember { mutableStateOf(false) }

    Column(modifier) {
        Text(label.uppercase(), style = MaterialTheme.typography.labelSmall)
        BasicTextField(
            modifier = modifier.focusRequester(focusRequester).onFocusChanged { focusState ->
                isFocused.value = focusState.isFocused
            },
            value = value,
            visualTransformation = visualTransformation,
            onValueChange = onValueChange,
            textStyle = MaterialTheme.typography.titleMedium,
            decorationBox = { innerTextField ->
                TextFieldDefaults.DecorationBox(
                    trailingIcon = {
                        if (showClearText && value.isNotEmpty() && isFocused.value) {
                            Icon(
                                Icons.Default.Cancel,
                                contentDescription = null,
                                modifier = Modifier.clickable(true){
                                    onValueChange("")
                                }
                            )
                        } else if (suffix != null) {
                            Text(suffix, style = MaterialTheme.typography.labelSmall)
                        }
                    },
                    placeholder = @Composable {
                        Text(
                            hint,
                            style = MaterialTheme.typography.titleMedium.copy(
                                color = Black20
                            )
                        )
                    },
                    value = value,
                    innerTextField = innerTextField,
                    enabled = true,
                    interactionSource = interactionSource,
                    singleLine = true,
                    visualTransformation = VisualTransformation.None,
                    contentPadding = PaddingValues(horizontal = 0.dp, vertical = 12.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        unfocusedIndicatorColor = if (isFocused.value) Green100 else Black20,
                        focusedIndicatorColor = Green100
                    )
                )
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordTextField(
    modifier: Modifier = Modifier,
    value: String = "",
    label: String = "",
    hint: String = "",
    interactionSource: InteractionSource? = null,
    onValueChange: (String) -> Unit = {},
) {
    val interactionSource = interactionSource ?: remember { MutableInteractionSource() }

    DefaultTextField(
        modifier = modifier,
        value = value,
        label = label,
        hint = hint,
        interactionSource = interactionSource,
        onValueChange = onValueChange,
        visualTransformation = PasswordVisualTransformation(),
        showClearText = false
    )
}