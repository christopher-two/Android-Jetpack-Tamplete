package org.override.tamplete.feature.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import org.override.tamplete.feature.navigation.navigator.HomeNavigator

class HomeViewModel(
    private val homeNavigator: HomeNavigator
) : ViewModel() {

    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(HomeState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                /** Load initial data here **/
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = HomeState()
        )

    fun onAction(action: HomeAction) {
        when (action) {
            is HomeAction.OnTabSelected -> {
                homeNavigator.switchTab(action.tab)
                _state.update { it.copy(selectedTab = action.tab) }
            }
            is HomeAction.OnFabClick -> {
                _state.update { it.copy(snackbarMessage = "FAB clicked!") }
            }
            is HomeAction.OnSnackbarDismissed -> {
                _state.update { it.copy(snackbarMessage = null) }
            }
        }
    }

}