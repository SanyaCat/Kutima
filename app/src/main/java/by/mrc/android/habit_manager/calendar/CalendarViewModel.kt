package by.mrc.android.habit_manager.calendar

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.mrc.android.habit_manager.MainActivity
import by.mrc.android.habit_manager.data.Habit
import by.mrc.android.habit_manager.data.HabitDatabase
import by.mrc.android.habit_manager.data.HabitRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CalendarViewModel : ViewModel() {

    private val repository: HabitRepository
    private val habits: LiveData<List<Habit>>

    init {
        val habitDao = HabitDatabase.getDatabase(MainActivity.context).habitDao()
        repository = HabitRepository(habitDao)
        habits = repository.allHabits
    }
}