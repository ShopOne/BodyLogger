package com.support.bodylogger

import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        db = Room.databaseBuilder(
            applicationContext,
            BodyInfoDataBase::class.java,"com.support.bodylogger.BodyInfoDataBase"
        ).enableMultiInstanceInvalidation().build()
        dao = db.bodyInfoDao()
        mainHandler = Handler()
        val now:Calendar = getInstance()
        val dummy = List(5){ i ->
            BodyInfoEntity(
                id = 0,
                bodyWeight = i,
                bodyFatPercentage = i,
                infoMonth = now.get(MONTH),
                infoYear = now.get(YEAR),
                infoDate = now.get(DATE),
                commentText = "this is test.\nyes yes.",
                imageId = null
            )
        }
        my_recyclerview.layoutManager = LinearLayoutManager(this)

        my_recyclerview.adapter = CardAdapter(dummy,resources,this)
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
    private fun setDataToListFromDataBase(){
        AsyncTask.execute{
            val now = getInstance()
            val displayList = dao.searchByMonthAndYear(now.get(DAY_OF_YEAR),now.get(DAY_OF_MONTH))
            mainHandler.post {
                my_recyclerview.layoutManager = LinearLayoutManager(this)

                my_recyclerview.adapter = CardAdapter(displayList,resources,this)
            }
        }
    }
}
