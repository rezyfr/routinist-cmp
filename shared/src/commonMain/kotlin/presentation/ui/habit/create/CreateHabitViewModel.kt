package presentation.ui.habit.create

import androidx.lifecycle.viewModelScope
import domain.handleResult
import domain.usecase.GetRandomHabitUseCase
import kotlinx.coroutines.launch
import presentation.component.core.ProgressBarState
import presentation.component.core.UIComponent
import presentation.util.BaseViewModel

class CreateHabitViewModel(
    private val getRandomHabitUseCase: GetRandomHabitUseCase
) : BaseViewModel<CreateHabitEvent, CreateHabitState, CreateHabitAction>() {

    init {
        getRandomHabit()
    }

    override fun setInitialState(): CreateHabitState {
        return CreateHabitState()
    }

    override fun onTriggerEvent(event: CreateHabitEvent) {
        when (event) {
            CreateHabitEvent.OnHabitChosen -> {
            }
        }
    }

    private fun getRandomHabit() {
        viewModelScope.launch {
            setState {
                copy(
                    progressBarState = ProgressBarState.ScreenLoading
                )
            }
            getRandomHabitUseCase.execute(Unit).handleResult(
                ifError = {
                    setError { UIComponent.ToastSimple(it.message.orEmpty()) }
                },
                ifSuccess = {
                    setState {
                        copy(
                            popularHabits = it,
                            progressBarState = ProgressBarState.Idle
                        )
                    }
                }
            )
        }
    }
}