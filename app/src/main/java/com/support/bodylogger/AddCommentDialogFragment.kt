package com.support.bodylogger

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.custom_alert_layout.*
import kotlinx.android.synthetic.main.custom_alert_layout.view.*
import java.lang.IllegalStateException

class AddCommentDialogFragment(
    private val firstList: List<String>): DialogFragment(){
    private lateinit var inflatedView: View
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let{ it ->
            val view = it.layoutInflater.inflate(R.layout.custom_alert_layout, null)
            Log.d("first List",firstList.toString())
            inflatedView = view
            firstList.forEachIndexed{idx, value->
                when(idx){
                    0 -> inflatedView.editTextComment0.setText(value, TextView.BufferType.EDITABLE)
                    1 -> inflatedView.editTextComment1.setText(value, TextView.BufferType.EDITABLE)
                    2 -> inflatedView.editTextComment2.setText(value, TextView.BufferType.EDITABLE)
                    3 -> inflatedView.editTextComment3.setText(value, TextView.BufferType.EDITABLE)
                }
            }
            view.completeButton.setOnClickListener {
                val copyActivity = activity
                if(copyActivity is AddNewInfoActivity){
                    copyActivity.changeCommentList(makeList())
                }
                this.dismiss()
            }
            val builder = AlertDialog.Builder(it)
            return builder.setView(view).show()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
    private fun makeList(): List<String>{
        val resList = mutableListOf<String>()
        for(i in 0..3){
            when(i){
                0 -> resList.add(inflatedView.editTextComment0.text.toString())
                1 -> resList.add(inflatedView.editTextComment1.text.toString())
                2 -> resList.add(inflatedView.editTextComment2.text.toString())
                3 -> resList.add(inflatedView.editTextComment3.text.toString())
            }
        }
        Log.d("ret list",resList.toString())
        return resList.toList()
    }
}