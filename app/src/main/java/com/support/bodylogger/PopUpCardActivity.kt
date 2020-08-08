package com.support.bodylogger

import BodyInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_pop_up_card.*

class PopUpCardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pop_up_card)
        val state = intent.getSerializableExtra("BodyData")
        if(state is BodyInfo){
            pop_up_card_weight.text = state.bodyWeight.toString()
            pop_up_card_fat_par.text = state.bodyFatPercentage.toString()
            pop_up_card_image.setImageResource(state.imageId ?: R.drawable.ic_launcher_background)
            otherList.adapter =  ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,state.commentList)
        }
    }

}