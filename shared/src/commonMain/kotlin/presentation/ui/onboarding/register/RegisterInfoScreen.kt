package presentation.ui.onboarding.register

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource

import presentation.component.core.DefaultTextField
import presentation.component.core.PasswordTextField
import presentation.component.core.Spacer_16dp
import routinist.shared.generated.resources.Res
import routinist.shared.generated.resources.confirm_password
import routinist.shared.generated.resources.email
import routinist.shared.generated.resources.login_email
import routinist.shared.generated.resources.login_password
import routinist.shared.generated.resources.name
import routinist.shared.generated.resources.next
import routinist.shared.generated.resources.password
import routinist.shared.generated.resources.register_confirm_password
import routinist.shared.generated.resources.register_name

@Composable
fun RegisterInfoScreen(
    modifier: Modifier = Modifier,
    state: RegisterState,
    events: (RegisterEvent) -> Unit = {},
) {
    Column(
        modifier
    ) {
        DefaultTextField(
            modifier = Modifier.fillMaxWidth(),
            label = stringResource(Res.string.name),
            hint = stringResource(Res.string.register_name),
            value = state.name,
            onValueChange = {
                events(RegisterEvent.OnNameChange(it))
            }
        )
        Spacer_16dp()
        DefaultTextField(
            modifier = Modifier.fillMaxWidth(),
            label = stringResource(Res.string.email),
            hint = stringResource(Res.string.login_email),
            value = state.email,
            onValueChange = {
                events(RegisterEvent.OnEmailChange(it))
            }
        )
        Spacer_16dp()
        PasswordTextField(
            modifier = Modifier.fillMaxWidth(),
            label = stringResource(Res.string.password),
            hint = stringResource(Res.string.login_password),
            value = state.password,
            onValueChange = {
                events(RegisterEvent.OnPasswordChange(it))
            }
        )
        Spacer_16dp()
        PasswordTextField(
            modifier = Modifier.fillMaxWidth(),
            label = stringResource(Res.string.confirm_password),
            hint = stringResource(Res.string.register_confirm_password),
            value = state.confirmPassword,
            onValueChange = {
                events(RegisterEvent.OnConfirmPasswordChange(it))
            }
        )
    }
}