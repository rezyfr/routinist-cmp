package presentation.util

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import presentation.component.core.UIComponent
import presentation.component.core.ViewEvent
import presentation.component.core.ViewSingleAction
import presentation.component.core.ViewState

abstract class BaseViewModel<Event : ViewEvent,
        UiState : ViewState,
        SingleAction : ViewSingleAction> : ViewModel() {
    abstract fun setInitialState(): UiState
    abstract fun onTriggerEvent(event: Event)
    private val initialState: UiState by lazy { setInitialState() }
    private val _state: MutableState<UiState> = mutableStateOf(initialState)
    val state = _state

    private val _event: MutableSharedFlow<Event> = MutableSharedFlow()
    private val _action: Channel<SingleAction> = Channel()
    val action = _action.receiveAsFlow()
    private val _errors: Channel<UIComponent> = Channel()
    val errors = _errors.receiveAsFlow()

    init {
        subscribeToEvents()
    }

    private fun subscribeToEvents() {
        viewModelScope.launch {
            _event.collect {
                onTriggerEvent(it)
            }
        }
    }

    fun setEvent(event: Event) {
        viewModelScope.launch { _event.emit(event) }
    }

    protected fun setState(reducer: UiState.() -> UiState) {
        val newState = state.value.reducer()
        _state.value = newState
    }

    protected fun setAction(builder: () -> SingleAction) {
        val effectValue = builder()
        viewModelScope.launch { _action.send(effectValue) }
    }

    protected fun setError(builder: () -> UIComponent) {
        val effectValue = builder()
        viewModelScope.launch {
            _errors.send(effectValue)
        }
    }
}