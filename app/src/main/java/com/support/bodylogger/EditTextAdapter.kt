package com.support.bodylogger

import android.app.Activity
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import androidx.core.view.children
import androidx.core.widget.addTextChangedListener
import kotlinx.android.synthetic.main.list_item.view.*


class EditTextAdapter(private val layoutInflater: LayoutInflater,
                      context: Context)
    : ArrayAdapter<String>(context, R.layout.list_item){
    companion object{
        const val MAX_LIST_SIZE = 4
    }
    var listView: ListView? = null
    var list = listOf<String>()
    override fun add(obj: String?) {
        if(list.size == MAX_LIST_SIZE) return
        val newList = list.toMutableList()
        if(obj is String){
            newList.add(obj)
            list = newList.toList()
            this.notifyDataSetChanged()
        }
    }

    fun popBack(){
        if(list.isNotEmpty()){
            remove(list[list.size-1])
        }
    }

    override fun remove(obj: String?) {
        val newList = list.toMutableList()
        newList.removeAt(list.size-1)
        list = newList
        super.remove(obj)
    }
    override fun getCount(): Int {
        return list.size
    }
    override fun getItem(position: Int): String? {
        return list[position]
    }
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val item = getItem(position)
        if(convertView == null){
            val newView = layoutInflater.inflate(R.layout.list_item, parent, false)
            newView.listItemEditText.setText(item, TextView.BufferType.NORMAL)
            if(newView is EditText){
                Log.d("data","$position hello}")
                newView.addTextChangedListener(object: TextWatcher {
                    override fun afterTextChanged(s: Editable?) {
                    }

                    override fun beforeTextChanged(
                        s: CharSequence?,
                        start: Int,
                        count: Int,
                        after: Int
                    ) {
                    }
                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                        refreshList()
                    }
                }
                )
            }
            return newView
        }else{
            convertView.listItemEditText.setText(item, TextView.BufferType.NORMAL)
            return convertView
        }
    }
    private fun refreshList(){
        var idx = 0
        listView?.children?.forEach{ view ->
            if(view is EditText){
                changeListByEditText(idx, view.text.toString())
                idx++
            }
        }
    }
    private fun changeListByEditText(position: Int,str: String){
        if(list.size<=position) return
        if(list[position] == str) return
        val newList = list.toMutableList()
        newList[position] = str
        list = newList.toList()
    }
}
