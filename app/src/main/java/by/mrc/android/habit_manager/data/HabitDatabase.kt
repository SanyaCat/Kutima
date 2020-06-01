package by.mrc.android.habit_manager.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = arrayOf(Habit::class), version = 1) //exportSchema = false
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
//            return INSTANCE ?: synchronized(this) {
//                val instance = Room.databaseBuilder(
//                    context.applicationContext,
//                    HabitDatabase::class.java,
//                    "habits"
//                )
//                    .addCallback(HabitDatabaseCallback(coroutineScope))   // шоб было
//                    .build()
//                INSTANCE = instance
//                // returns instance
//                instance
//            }
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

//    private class HabitDatabaseCallback(
//        private val coroutineScope: CoroutineScope
//    ) : RoomDatabase.Callback() {
//        override fun onOpen(db: SupportSQLiteDatabase) {
//            super.onOpen(db)
//            INSTANCE?.let { database ->
//                coroutineScope.launch {
//                    populateDatabase(database.habitDao())     // хз зачем (шоб было)
//                }
//            }
//        }
//
//        suspend fun populateDatabase(habitDao: HabitDao) {
//            //habitDao.deleteAll()
//
//            var habit = Habit(id = 0, name = "New habit!", timeToComplete = 66)
//            habitDao.insert(habit)
//
//        }
//    }
}