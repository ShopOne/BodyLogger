package com.support.bodylogger

import android.app.Activity
import android.app.Dialog
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.room.Room
import kotlinx.android.synthetic.main.activity_add_new_info.*
import kotlinx.android.synthetic.main.custom_alert_layout.view.*
import kotlinx.android.synthetic.main.list_item.view.*
import java.lang.IllegalStateException
import java.util.*
import java.util.Calendar.*

class AddNewInfoActivity : AppCompatActivity() {
    private lateinit var db: BodyInfoDataBase
    private lateinit var dao: BodyInfoDao
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_info)
        val editTextAdapter = EditTextAdapter(layoutInflater, this)
        val dialog = AddCommentDialogFragment(editTextAdapter)

        db = Room.databaseBuilder(
            applicationContext,
            BodyInfoDataBase::class.java,"com.support.bodylogger.BodyInfoDataBase"
        ).enableMultiInstanceInvalidation().build()
        dao = db.bodyInfoDao()
        writeCommentButton.setOnClickListener {
            dialog.show(supportFragmentManager,"AddCommentDialog")
        }
        addNewInfoButton.setOnClickListener{
            val weightInfo = inputWeight.toString().toInt()
            val fatPerInfo = inputBodyPerFat.toString().toInt()
            val todayCalender = getInstance()
            val date = todayCalender.get(DATE)
            val month = todayCalender.get(MONTH)
            val year = todayCalender.get(YEAR)
            val commentList:MutableList<String> = editTextAdapter.list.toMutableList()
            commentList.remove("")
            val strBuilder = StringBuilder()
            commentList.forEachIndexed{index: Int, s: String ->
                strBuilder.append(s)
                if(index != commentList.size-1){
                    strBuilder.append("\n")
                }
            }
            val commentText = strBuilder.toString()
            dao.insertBodyInfo(
                BodyInfoEntity(
                    id = 0,
                    bodyWeight = weightInfo,
                    bodyFatPercentage = fatPerInfo,
                    infoMonth = month,
                    infoYear = year,
                    infoDate = date,
                    commentText = commentText,
                    imageId = null
                )
            )
        }
    }


}
class AddCommentDialogFragment(private val adapter: EditTextAdapter): DialogFragment(){
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let{
            val view = it.layoutInflater.inflate(R.layout.custom_alert_layout, null)
            view.commentList.adapter = adapter
            view.addImageButton.setOnClickListener{
                adapter.add("")
            }
            view.completeButton.setOnClickListener {
                this.dismiss()
            }
            val builder = AlertDialog.Builder(it)
            return builder.setView(view).show()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}

class EditTextAdapter(private val layoutInflater: LayoutInflater, context: Context)
    : ArrayAdapter<String>(context, R.layout.list_item){
    var list = listOf<String>()
    override fun add(obj: String?) {
        val newList = list.toMutableList()
        if(obj is String){
            newList.add(obj)
            list = newList.toList()
            this.notifyDataSetChanged()
        }
    }
    override fun getCount(): Int {
        return list.size
    }
    override fun getItem(position: Int): String? {
        return list[position]
    }
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val item = getItem(position)
        val newView = convertView ?: layoutInflater.inflate(R.layout.list_item, parent, false)
        newView.listItemEditText.setText(item, TextView.BufferType.NORMAL)
        return newView
    }
}
