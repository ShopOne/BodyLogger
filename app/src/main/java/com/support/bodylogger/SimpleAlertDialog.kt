package com.support.bodylogger

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class SimpleAlertDialog(
    private val title: String,
    private val message: String
) : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(title).setMessage(message)
        return builder.create()
    }
}