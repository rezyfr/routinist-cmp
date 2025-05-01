package presentation.ui.onboarding.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.jetbrains.compose.resources.stringResource
import presentation.component.BasePreview
import presentation.component.ButtonSize
import presentation.component.DefaultButton
import presentation.component.DefaultScreenUI
import presentation.component.DefaultTextField
import presentation.component.PasswordTextField
import presentation.component.Spacer_16dp
import presentation.component.Spacer_24dp
import presentation.component.UIComponent
import presentation.theme.AppTheme
import presentation.theme.Black60
import presentation.theme.BorderColor
import routinist.shared.generated.resources.Res
import routinist.shared.generated.resources.onboarding_continue
import routinist.shared.generated.resources.login_email
import routinist.shared.generated.resources.email
import routinist.shared.generated.resources.login_password
import routinist.shared.generated.resources.login_forgot_password
import routinist.shared.generated.resources.password
import routinist.shared.generated.resources.login_create_account
import routinist.shared.generated.resources.next
@Composable
fun LoginScreen(
    state: LoginState,
    events: (LoginEvent) -> Unit = {},
    errors: Flow<UIComponent>,
    navigateToRegister: () -> Unit = {},
    onBackClick: () -> Unit = {}
) {
    DefaultScreenUI(
        errors = errors,
        progressBarState = state.progressBarState,
        titleToolbar = stringResource(Res.string.onboarding_continue),
        startIconToolbar = Icons.Default.ChevronLeft,
        onClickStartIconToolbar = onBackClick
    ) {
        Column(
            Modifier.fillMaxSize().padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.Top
        ) {
            Spacer_16dp()
            DefaultTextField(
                modifier = Modifier.fillMaxWidth(),
                label = stringResource(Res.string.email),
                hint = stringResource(Res.string.login_email),
                value = state.username,
                onValueChange = {
                    events(LoginEvent.OnUsernameChange(it))
                }
            )
            Spacer_16dp()
            PasswordTextField(
                modifier = Modifier.fillMaxWidth(),
                label = stringResource(Res.string.password),
                hint = stringResource(Res.string.login_password),
                value = state.password,
                onValueChange = {
                    events.invoke(LoginEvent.OnPasswordChange(it))
                }
            )
            Spacer_16dp()
            Text(
                text = stringResource(Res.string.login_forgot_password),
                style = MaterialTheme.typography.bodyLarge,
                color = Black60,
            )
            Spacer(Modifier.weight(1f))
            Text(
                modifier = Modifier.fillMaxWidth()
                    .clickable(true) {
                        navigateToRegister()
                    },
                textAlign = TextAlign.Center,
                text = stringResource(Res.string.login_create_account),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.primary,
            )
            Spacer_24dp()
            DefaultButton(
                progressBarState = state.progressBarState,
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(Res.string.next),
                size = ButtonSize.Large,
                onClick = { events(LoginEvent.Login) }
            )
            Spacer_16dp()
        }
    }
}

@BasePreview
@Composable
fun PreviewLogin() {
    AppTheme() {
        LoginScreen(
            state = LoginState(),
            errors = flow {  }
        )
    }
}