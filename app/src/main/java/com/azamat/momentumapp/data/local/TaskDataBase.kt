package com.azamat.momentumapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Task::class], version = 1, exportSchema = false)
abstract class TaskDataBase : RoomDatabase() {

    abstract fun taskDao(): TaskDao

    companion object {
        @Volatile
        private var INSTANCE: TaskDataBase? = null

        fun getTaskDataBase(context: Context): TaskDataBase {
            val taskInstance = INSTANCE
            if (taskInstance != null) {
                return taskInstance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TaskDataBase::class.java,
                    "TaskData"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}