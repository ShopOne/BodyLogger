package com.support.bodylogger

import BodyInfoDao
import BodyInfoDataBase
import BodyInfoEntity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var db: BodyInfoDataBase
    private lateinit var dao: BodyInfoDao
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dummy = List<BodyInfoEntity>(5){i -> BodyInfoEntity(i,i,Calendar.getInstance(),List(2){j->"$j $i"},null)}
        my_recyclerview.layoutManager = LinearLayoutManager(this)

        my_recyclerview.adapter = CardAdapter(dummy,resources,this)

        db = Room.databaseBuilder(
            applicationContext,
            BodyInfoDataBase::class.java,"BodyInfoDataBase"
        ).build()
        dao = db.bodyInfoDao()
    }
}
