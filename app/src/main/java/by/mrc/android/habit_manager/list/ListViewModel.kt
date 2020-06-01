package by.mrc.android.habit_manager.list

import android.app.Application
import androidx.lifecycle.*
import by.mrc.android.habit_manager.MainActivity
import by.mrc.android.habit_manager.data.Habit
import by.mrc.android.habit_manager.data.HabitDao
import by.mrc.android.habit_manager.data.HabitDatabase
import by.mrc.android.habit_manager.data.HabitRepository
import kotlinx.coroutines.*

// or just ViewModel (without Android_)
class ListViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: HabitRepository
    // Using LiveData and caching what getAlphabetizedWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    val habits: LiveData<List<Habit>>

//    private var viewModelJob = Job()
//    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
//    private var habit = MutableLiveData<Habit?>()

    init {
        val habitDao = HabitDatabase.getDatabase(MainActivity.context, viewModelScope).habitDao()
        repository = HabitRepository(habitDao)
        habits = repository.allHabits

//        initializeHabit()
    }

//    private fun initializeHabit() {
//        uiScope.launch {
//            habit.value = getHabitFromDatabase()
//        }
//    }

//    private suspend fun getHabitFromDatabase(): Habit? {
//        return withContext(Dispatchers.IO) {
//            var habit = database.getAll()
//            if (habit?.endTimeMilli != habit?.startTimeMilli) {
//                habit = null
//            }
//            habit
//        }
//    }

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(habit: Habit) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(habit)
    }

    fun update(habit: Habit) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(habit)
    }

    fun delete(habit: Habit) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(habit)
    }

//    override fun onCleared() {
//        super.onCleared()
//        viewModelJob.cancel() // to end coroutine
//    }
}