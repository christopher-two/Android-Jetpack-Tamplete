package org.override.tamplete.feature.auth.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import org.override.tamplete.core.ui.theme.AppTheme

@Composable
fun AuthRoot(
    viewModel: AuthViewModel
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    AuthScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

@Composable
fun AuthScreen(
    state: AuthState,
    onAction: (AuthAction) -> Unit,
) {

}

@Preview
@Composable
private fun Preview() {
    AppTheme {
        AuthScreen(
            state = AuthState(),
            onAction = {}
        )
    }
}