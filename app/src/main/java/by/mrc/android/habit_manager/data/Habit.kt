package by.mrc.android.habit_manager.data

import androidx.room.*
import java.util.*

@Entity(tableName = "habit_table")
data class Habit(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "description") var description: String? = null,
    @ColumnInfo(name = "color") var color: String? = null,
    @ColumnInfo(name = "timeToComplete") var timeToComplete: Int
    //@ColumnInfo(name = "progress") val timeProgress: List<Date>? = List<Date>(0) {Date()}
)