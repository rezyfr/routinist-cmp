package presentation.ui.habit.create

import presentation.util.BaseViewModel

class CreateHabitViewModel(
) : BaseViewModel<CreateHabitEvent, CreateHabitState, CreateHabitAction>() {
    override fun setInitialState(): CreateHabitState {
        return CreateHabitState()
    }

    override fun onTriggerEvent(event: CreateHabitEvent) {
        when (event) {
            CreateHabitEvent.OnHabitChosen -> {
            }

            is CreateHabitEvent.OnInit -> {
                setState {
                    copy(
                        habit = event.habit,
                        selectedUnit = event.habit.units.first(),
                        goal = event.habit.defaultGoal.toFloat()
                    )
                }
            }
        }
    }
}