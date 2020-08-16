package com.support.bodylogger

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_add_new_info.*
import kotlinx.android.synthetic.main.activity_pop_up_card.*
import java.io.BufferedInputStream
import java.io.IOException

class PopUpCardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pop_up_card)
        val state = intent.getSerializableExtra("BodyData")
        if(state is BodyInfoEntity){
            pop_up_card_weight.text = String.format(getString(R.string.pop_up_card_body_weight), state.bodyWeight)
            pop_up_card_fat_par.text = String.format(getString(R.string.pop_up_card_body_per_fat), state.bodyFatPercentage)
            val bitmap = readImgFromFileName(state.imageName)
            if(bitmap != null){
                pop_up_card_image.setImageBitmap(bitmap)
            }
            otherList.adapter =  ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,state.commentText.split("\n"))
        }
    }
    private fun readImgFromFileName(fileName:String?): Bitmap? {
        if(fileName == null) return null
        val bufferedInputStream = BufferedInputStream(applicationContext.openFileInput(fileName))
        return BitmapFactory.decodeStream(bufferedInputStream)
    }
}
