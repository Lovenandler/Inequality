package com.hello.myapplication.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [InequalityRoomModel::class], version = 1)
abstract class NumbersDatabase: RoomDatabase() {
    abstract fun getNumbersDao(): INumbers
    companion object{
        @Volatile
        private var INSTANCE: NumbersDatabase? = null
        private val LOCK = Any()
        operator fun invoke(context: Context) = INSTANCE?: synchronized(LOCK){
            INSTANCE?: buildDatabase(context).also {
                INSTANCE = it
            }
        }
        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            NumbersDatabase::class.java,
            "numbers-database"
        ).allowMainThreadQueries().build()
    }
}