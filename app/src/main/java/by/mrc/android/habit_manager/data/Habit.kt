package by.mrc.android.habit_manager.data

import androidx.room.*
import com.google.gson.Gson

@Entity(tableName = "habit_table")
data class Habit(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "description") var description: String?,  // description (optional)
    @ColumnInfo(name = "color") var color: Int,
    @ColumnInfo(name = "targetTime") var targetTime: Int, // days or weeks
    @ColumnInfo(name = "progress") var progress: String    // habit progress (mutable map: date-int)
)