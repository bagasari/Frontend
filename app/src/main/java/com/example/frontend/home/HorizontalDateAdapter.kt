package com.example.frontend.home

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.frontend.ExpenditureActivity
import com.example.frontend.R
import com.example.frontend.accountBook.ProductsByDate
import com.example.frontend.adapter.ExpenditureAdapter
import com.example.frontend.databinding.FragHomeBinding

class HorizontalDateAdapter(var dateList: ArrayList<ProductsByDate>) : RecyclerView.Adapter<HorizontalDateAdapter.MyViewHolder>(){


    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val date = itemView.findViewById<TextView>(R.id.tv_date)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.date_horizontal, parent, false)
        return MyViewHolder(view).apply{
            itemView.setOnClickListener {
                val curPos: Int = adapterPosition
            }
        }
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.date.text = dateList.get(position).purchaseDate
        holder.itemView.setOnClickListener{
            itemClickListener.onClick(it, position)
        }
    }

    override fun getItemCount(): Int {
        return dateList.size
    }


    interface OnItemClickListener {
        fun onClick(v: View, position: Int)
    }

    fun setItemClickListener(onItemClickListener: OnItemClickListener){
        this.itemClickListener = onItemClickListener
    }
    private lateinit var itemClickListener : OnItemClickListener
}