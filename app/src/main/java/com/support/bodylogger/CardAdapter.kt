package com.support.bodylogger

import BodyInfo
import android.annotation.SuppressLint
import android.content.res.Resources
import android.text.Html
import android.text.Html.FROM_HTML_MODE_COMPACT
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.recyclerview_item.view.*
import java.util.*

class CardAdapter(private val bodyInfoList: List<BodyInfo>,
                  private val resources: Resources,
                  private val onClick: View.OnClickListener
) : RecyclerView.Adapter<CardAdapter.CardHolder>(){
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
            val weightTextHtml =
                """${resources.getString(R.string.body_weight)}: 
                    <font color="Red">
                        ${item.bodyWeight}
                    </font>kg"""
            val bodyParFatTextHtml =
                """${resources.getString(R.string.body_fat_per)}:
                    <font color="Red">
                        ${item.bodyFatPercentage.toString()}
                    </font>%"""
            it.card_date.text = yydd
            it.card_weight.text = HtmlCompat.fromHtml(weightTextHtml,HtmlCompat.FROM_HTML_MODE_COMPACT)
            it.card_fat_percentage.text = HtmlCompat.fromHtml(bodyParFatTextHtml,HtmlCompat.FROM_HTML_MODE_COMPACT)
            it.setOnClickListener(onClick)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val item = layoutInflater.inflate(R.layout.recyclerview_item,parent,false)
        return CardHolder(item)
    }
}
