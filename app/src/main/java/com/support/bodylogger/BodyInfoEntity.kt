package com.support.bodylogger

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
class BodyInfoEntity constructor(
    @PrimaryKey val dateStr: String,
    val bodyWeight: Int,
    val bodyFatPercentage:Int,
    val commentText: String,
    val infoYear: Int,
    val infoMonth: Int,
    val infoDate: Int,
    val imageName: String?): Serializable{
    companion object{
        fun generateData(year: Int,month: Int,date :Int): String{
            return "$year/$month/$date"
        }
    }
}
