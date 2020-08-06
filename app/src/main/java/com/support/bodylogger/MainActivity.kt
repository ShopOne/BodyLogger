package com.support.bodylogger

import BodyInfo
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dummy = List<BodyInfo>(5){i -> BodyInfo(i,i,Calendar.getInstance(),List(2){j->"$j $i"})}
        my_recyclerview.layoutManager = LinearLayoutManager(this)

        my_recyclerview.adapter = CardAdapter(dummy,resources,View.OnClickListener {
            val intent = Intent(this,PopUpCardActivity::class.java)
            startActivity(intent)
        })

    }
}
