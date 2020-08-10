package com.support.bodylogger

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.*
import java.util.Calendar.DAY_OF_MONTH
import java.util.Calendar.DAY_OF_YEAR

@Entity
data class BodyInfoEntity constructor(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val bodyWeight: Int,
    val bodyFatPercentage:Int,
    val commentText: String,
    val infoMonth: Int,
    val infoDate: Int,
    val infoYear: Int,
    val imageId: Int?): Serializable{
}