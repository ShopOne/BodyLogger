package com.support.bodylogger

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [BodyInfoEntity::class],version = 1)
abstract class BodyInfoDataBase: RoomDatabase(){
    abstract fun bodyInfoDao(): BodyInfoDao
}