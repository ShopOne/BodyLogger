package com.support.bodylogger

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_add_new_info.*
import kotlinx.android.synthetic.main.custom_alert_layout.view.*

class AddNewInfoActivity : AppCompatActivity() {
    lateinit var adapter: ArrayAdapter<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_info)
        writeCommentButton.setOnClickListener {
            val view = this.layoutInflater.inflate(R.layout.custom_alert_layout, null)
            adapter = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1)
            view.commentList.adapter = adapter
            view.addImageButton.setOnClickListener{
                adapter.add("hoge")
            }
            AlertDialog.Builder(this).setView(view).show()
        }
    }
}