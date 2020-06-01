package by.mrc.android.habit_manager.data

import androidx.lifecycle.LiveData

class HabitRepository(private val habitDao: HabitDao) {
    val allHabits: LiveData<List<Habit>> = habitDao.getAll()

    fun insert(habit: Habit) {
        habitDao.insert(habit)
    }

    fun update(habit: Habit) {
        habitDao.update(habit)
    }

    fun delete(habit: Habit) {
        habitDao.delete(habit)
    }
}