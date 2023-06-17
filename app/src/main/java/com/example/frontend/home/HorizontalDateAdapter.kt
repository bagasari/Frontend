package com.example.frontend.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.frontend.R
import com.example.frontend.accountBook.GetCurrentAccountBookDTO
import com.example.frontend.accountBook.ProductsByDate

class HorizontalDateAdapter(var dateList: List<ProductsByDate>) : RecyclerView.Adapter<HorizontalDateAdapter.MyViewHolder>(){

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
        val dateString = dateList.get(position).purchaseDate
        holder.date.text = dateString.substring(dateString.length - 2)
        holder.itemView.setOnClickListener{
            itemClickListener.onClick(it, position)
        }
    }

    override fun getItemCount(): Int {
        return dateList.size
    }

    fun updateData(newItem: List<ProductsByDate>){
        dateList = newItem
        notifyDataSetChanged()
    }


    interface OnItemClickListener {
        fun onClick(v: View, position: Int)
    }

    fun setItemClickListener(onItemClickListener: OnItemClickListener){
        this.itemClickListener = onItemClickListener
    }
    private lateinit var itemClickListener : OnItemClickListener
}