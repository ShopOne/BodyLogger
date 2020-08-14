package com.support.bodylogger

import android.content.Intent
import android.os.AsyncTask
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.room.Room
import kotlinx.android.synthetic.main.activity_add_new_info.*
import java.lang.NumberFormatException
import java.util.Calendar.*

class AddNewInfoActivity : AppCompatActivity() {
    private lateinit var db: BodyInfoDataBase
    private lateinit var dao: BodyInfoDao
    private var nowCommentList: List<String> = listOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_info)
        initDataBase()
        val adapter = EditTextAdapter(layoutInflater, this)
        writeCommentButton.setOnClickListener {
            val dialog = AddCommentDialogFragment(adapter,nowCommentList)
            dialog.show(supportFragmentManager, "AddCommentDialog")
        }
        addNewInfoButton.setOnClickListener{
            try{
                val entity = makeEntity()
                AsyncTask.execute{
                    dao.insertBodyInfo(entity)
                }
                finish()
            }catch (e: NumberFormatException){
                Toast.makeText(applicationContext, "体重と体脂肪率を記入して下さい",
                    Toast.LENGTH_SHORT).show()
            }
        }
        bodyImageButton.setOnClickListener{

            /*
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            intent.type = "image/"*/
        }
    }
    fun changeCommentList(newList: List<String>){
        nowCommentList = newList
    }
    private fun initDataBase() {
        db = Room.databaseBuilder(
            applicationContext,
            BodyInfoDataBase::class.java, "com.support.bodylogger.BodyInfoDataBase"
        ).enableMultiInstanceInvalidation().build()
        dao = db.bodyInfoDao()
    }

    private fun makeEntity(): BodyInfoEntity{
        val weightInfoStr = inputWeight.text.toString()
        val fatPerInfoStr = inputBodyPerFat.text.toString()
        val weightInfo = weightInfoStr.toInt()
        val fatPerInfo = fatPerInfoStr.toInt()
        val todayCalender = getInstance()
        val date = todayCalender.get(DATE)
        val month = todayCalender.get(MONTH)
        val year = todayCalender.get(YEAR)
        val commentList:MutableList<String> = nowCommentList.toMutableList()
        commentList.remove("")
        val strBuilder = StringBuilder()
        commentList.forEachIndexed{index: Int, s: String ->
            strBuilder.append(s)
            if(index != commentList.size-1){
                strBuilder.append("\n")
            }
        }
        val commentText = strBuilder.toString()
        return BodyInfoEntity(
            dateStr = BodyInfoEntity.generateData(year,month,date),
            bodyWeight = weightInfo,
            bodyFatPercentage = fatPerInfo,
            infoMonth = month,
            infoYear = year,
            infoDate = date,
            commentText = commentText,
            imageId = null
        )
    }
}

