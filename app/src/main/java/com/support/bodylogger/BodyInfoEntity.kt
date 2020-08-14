package com.support.bodylogger

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.*
import java.util.Calendar.DAY_OF_MONTH
import java.util.Calendar.DAY_OF_YEAR

@Entity
class BodyInfoEntity constructor(
    @PrimaryKey val dateStr: String,
    val bodyWeight: Int,
    val bodyFatPercentage:Int,
    val commentText: String,
    val infoYear: Int,
    val infoMonth: Int,
    val infoDate: Int,
    val imageId: Int?): Serializable{
    companion object{
        fun generateData(year: Int,month: Int,date :Int): String{
            return "$year/$month/$date"
        }
    }
}
