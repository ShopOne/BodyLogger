package com.support.bodylogger

import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import java.util.Calendar.*

class MainActivity : AppCompatActivity() {

    private lateinit var db: BodyInfoDataBase
    private lateinit var dao: BodyInfoDao
    private lateinit var mainHandler: Handler
    private val nowCalender = getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        db = Room.databaseBuilder(
            applicationContext,
            BodyInfoDataBase::class.java,"com.support.bodylogger.BodyInfoDataBase"
        ).enableMultiInstanceInvalidation().build()
        dao = db.bodyInfoDao()
        mainHandler = Handler()
        setDataToListFromDataBase()
        setSupportActionBar(my_toolbar)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_item, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_favorite -> {
                val intent = Intent(this,AddNewInfoActivity::class.java)
                this.startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onRestart() {
        super.onRestart()
        setDataToListFromDataBase()
    }
    private fun setDataToListFromDataBase(){
        AsyncTask.execute{
            val now = getInstance()
            val displayList = dao.searchByMonthAndYear(now.get(MONTH),now.get(YEAR))
            mainHandler.post {
                my_recyclerview.layoutManager = LinearLayoutManager(this)

                my_recyclerview.adapter = CardAdapter(displayList.sortedWith(
                    kotlin.Comparator { a,b -> a.infoDate.compareTo(b.infoDate)
                }),resources,this)
            }
        }
    }
}
