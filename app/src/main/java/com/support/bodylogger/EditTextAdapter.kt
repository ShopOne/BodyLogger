package com.support.bodylogger

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import kotlinx.android.synthetic.main.list_item.view.*


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
