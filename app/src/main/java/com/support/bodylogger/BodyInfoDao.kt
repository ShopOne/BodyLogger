package com.support.bodylogger

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface BodyInfoDao{
    @Query("SELECT * FROM BodyInfoEntity WHERE infoMonth == :month AND infoYear == :year")
    fun searchByMonthAndYear(month: Int,year: Int): List<BodyInfoEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBodyInfo(bodyInfo: BodyInfoEntity)
}