package com.hello.myapplication.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
@Dao
interface INumbers {
    @Insert
    fun addNumbers(number: InequalityRoomModel)

    @Query("SELECT * FROM numbers_table")
    fun getAllData(): List<InequalityRoomModel>

    @Query("DELETE FROM numbers_table")
    fun deleteNumbers(): Int
}