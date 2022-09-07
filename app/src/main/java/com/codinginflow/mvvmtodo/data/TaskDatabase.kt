package com.codinginflow.mvvmtodo.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.codinginflow.mvvmtodo.di.ApplicationScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

@Database(entities = [Task::class], version = 1)
abstract class TaskDatabase : RoomDatabase() {

    abstract fun taskDao(): TaskDao

    class Callback @Inject constructor(
        private val database: Provider<TaskDatabase>,
        @ApplicationScope private val applicationScope: CoroutineScope
    ): RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            //db operations
            val dao = database.get().taskDao()



            applicationScope.launch {
                dao.insert(Task("Wash the dishes"))
                dao.insert(Task("Buy groceries", isImportant = true))
                dao.insert(Task("Task #3"))
                dao.insert(Task("Call grandma", isCompleted = true))
                dao.insert(Task("Find money for college", isImportant = true))
                dao.insert(Task("Wait till monday"))

            }
        }
    }

}