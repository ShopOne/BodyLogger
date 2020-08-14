package com.support.bodylogger

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.custom_alert_layout.view.*
import java.lang.IllegalStateException

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