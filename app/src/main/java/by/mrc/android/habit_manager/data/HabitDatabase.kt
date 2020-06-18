package by.mrc.android.habit_manager.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = arrayOf(Habit::class), version = 2, exportSchema = false)
abstract class HabitDatabase : RoomDatabase() {
    abstract fun habitDao(): HabitDao

    //prevents multiple instances of database
    companion object {
        @Volatile // do not cache value of this
        // variable and always read it from main memory
        private var INSTANCE: HabitDatabase? = null

        fun getDatabase(
            context: Context,
            coroutineScope: CoroutineScope
        ): HabitDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        HabitDatabase::class.java,
                        "habits"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}