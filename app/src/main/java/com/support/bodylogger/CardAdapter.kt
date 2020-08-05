package com.support.bodylogger

import BodyInfo
import android.content.res.Resources
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.recyclerview_item.view.*
import java.util.*

class CardAdapter(private val bodyInfoList: List<BodyInfo>, private val resources: Resources) : RecyclerView.Adapter<CardAdapter.CardHolder>(){
    class CardHolder(val view: View) :RecyclerView.ViewHolder(view){
        /*
        val dateId = view.card_date
        val bodyWeightId = view.card_weight
        val bodyFatPercentageId = view.card_fat_percentage
         */
    }

    override fun getItemCount(): Int = bodyInfoList.size
    override fun onBindViewHolder(holder: CardHolder, position: Int) {
        holder.view.let{
            val item = bodyInfoList[position]
            val year = item.infoDate.get(Calendar.MONTH)
            val day = item.infoDate.get(Calendar.DATE)
            val yydd = "$year/$day"
            val weightText = resources.getString(R.string.body_weight) + " " +
                    item.bodyWeight.toString() + "kg"
            val bodyParFatText = resources.getString(R.string.body_fat_per) + " " +
                    item.bodyFatPercentage.toString() + "%"
            it.card_date.text = yydd
            it.card_weight.text = weightText
            it.card_fat_percentage.text =bodyParFatText
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val item = layoutInflater.inflate(R.layout.recyclerview_item,parent,false)
        return CardHolder(item)
    }
}
