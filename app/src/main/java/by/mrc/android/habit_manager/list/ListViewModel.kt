package by.mrc.android.habit_manager.list

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import by.mrc.android.habit_manager.MainActivity
import by.mrc.android.habit_manager.data.Habit
import by.mrc.android.habit_manager.data.HabitDatabase
import by.mrc.android.habit_manager.data.HabitRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ListViewModel(application: Application) : AndroidViewModel(application) {

    companion object {
        var instance: ListViewModel? = null
    }

    private val repository: HabitRepository
    val habits: LiveData<List<Habit>>

    init {
        val habitDao = HabitDatabase.getDatabase(MainActivity.context).habitDao()
        repository = HabitRepository(habitDao)
        habits = repository.allHabits
    }

    fun insert(habit: Habit) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(habit)
    }

    fun update(habit: Habit) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(habit)
    }

    fun delete(habit: Habit) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(habit)
    }
}