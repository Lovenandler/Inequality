package com.hello.myapplication.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "numbers_table")
data class InequalityRoomModel(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var firstNum: Double? = null,
    var secondNum: Double? = null,
    var result: Double? = null
) {
}