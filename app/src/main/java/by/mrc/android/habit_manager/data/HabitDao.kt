package by.mrc.android.habit_manager.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface HabitDao {
    @Query("SELECT * FROM habit_table")
    fun getAll(): LiveData<List<Habit>>

    @Query("SELECT * FROM habit_table WHERE id IN (:ids)")
    fun getAllById(ids: IntArray): LiveData<List<Habit>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(habit: Habit)

    @Update
    fun update(habit: Habit)

    @Delete
    fun delete(habit: Habit)

    @Query("DELETE FROM habit_table")
    suspend fun deleteAll()
}