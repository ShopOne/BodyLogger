package com.support.bodylogger

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ListView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.custom_alert_layout.view.*
import java.lang.IllegalStateException

class AddCommentDialogFragment(
    private var adapter: EditTextAdapter,
    private val firstList: List<String>): DialogFragment(){
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let{ it ->
            val view = it.layoutInflater.inflate(R.layout.custom_alert_layout, null)
            view.commentList.adapter = adapter
            adapter.list = firstList
            adapter.listView = view.commentList
            view.addImageButton.setOnClickListener{
                adapter.add("")
            }
            view.subImageButton.setOnClickListener {
                adapter.popBack()
            }
            view.completeButton.setOnClickListener {
                val copyActivity = activity
                if(copyActivity is AddNewInfoActivity){
                    copyActivity.changeCommentList(adapter.list)
                }
                this.dismiss()
            }
            val builder = AlertDialog.Builder(it)
            return builder.setView(view).show()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}