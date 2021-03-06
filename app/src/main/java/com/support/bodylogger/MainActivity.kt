package com.support.bodylogger

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.icu.text.DateTimePatternGenerator.PatternInfo.OK
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import java.util.Calendar.*

class MainActivity : AppCompatActivity() {

    companion object{
        private const val LAST_MONTH = 11
        private const val FIRST_MONTH = 0
        private const val IS_FIRST_MAIN_ACTIVITY = "IS_FIRST_MAIN_ACTIVITY"
    }
    private lateinit var db: BodyInfoDataBase
    private lateinit var dao: BodyInfoDao
    private lateinit var mainHandler: Handler
    private val todayCalender = getInstance()
    private var nowYear = todayCalender.get(YEAR)
    private var nowMonth = todayCalender.get(MONTH)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        db = Room.databaseBuilder(
            applicationContext,
            BodyInfoDataBase::class.java,"com.support.bodylogger.BodyInfoDataBase"
        ).enableMultiInstanceInvalidation().build()
        dao = db.bodyInfoDao()
        mainHandler = Handler()
        changeNowDayView()
        setDataToListFromDataBase()
        setSupportActionBar(my_toolbar)
        dateLeftButton.setOnClickListener(ChangeDateButtonListener(this))
        dateRightButton.setOnClickListener(ChangeDateButtonListener(this))

        if(isFirstJudge()){
            val dialog = SimpleAlertDialog("初回メッセージ",
                resources.getString(R.string.first_main_activity_desc))
            dialog.show(supportFragmentManager, null)
            val edit = getSharedPreferences(IS_FIRST_MAIN_ACTIVITY,
                Context.MODE_PRIVATE).edit()
            edit.putBoolean(IS_FIRST_MAIN_ACTIVITY,false)
            edit.apply()
        }
    }

    private fun isFirstJudge(): Boolean{
        return getSharedPreferences(
            IS_FIRST_MAIN_ACTIVITY,
            Context.MODE_PRIVATE).getBoolean(IS_FIRST_MAIN_ACTIVITY,true)
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
            val displayList = dao.searchByMonthAndYear(nowMonth,nowYear)
            mainHandler.post {
                my_recyclerview.layoutManager = LinearLayoutManager(this)

                my_recyclerview.adapter = CardAdapter(displayList.sortedWith(
                    kotlin.Comparator { a,b -> a.infoDate.compareTo(b.infoDate)
                }),resources,this)
            }
        }
    }
    private fun changeNowDayView(){
        val todayStr = "${nowYear}/${nowMonth+1}"
        dateTextView.text = todayStr
    }
    fun nextMonth(){
        nowMonth++
        if(nowMonth>LAST_MONTH){
            nowYear++
            nowMonth = FIRST_MONTH
        }
        changeNowDayView()
        setDataToListFromDataBase()
    }
    fun prevMonth(){
        nowMonth--
        if(nowMonth<FIRST_MONTH){
            nowMonth = LAST_MONTH
            nowYear--
        }
        changeNowDayView()
        setDataToListFromDataBase()
    }

}
class ChangeDateButtonListener(private val nowActivity: Activity): View.OnClickListener{
    override fun onClick(v: View?) {
        if(nowActivity is MainActivity){
            when(v?.id){
                R.id.dateLeftButton -> nowActivity.prevMonth()
                R.id.dateRightButton -> nowActivity.nextMonth()
            }
        }
    }
}
