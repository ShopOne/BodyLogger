package com.support.bodylogger

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_add_new_info.*
import kotlinx.android.synthetic.main.activity_pop_up_card.*

class PopUpCardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pop_up_card)
        val state = intent.getSerializableExtra("BodyData")
        if(state is BodyInfoEntity){
            pop_up_card_weight.text = state.bodyWeight.toString()
            pop_up_card_fat_par.text = state.bodyFatPercentage.toString()
            pop_up_card_image.setImageResource(state.imageId ?: R.drawable.ic_launcher_background)
            otherList.adapter =  ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,state.commentText.split("\n"))
        }
    }

}
