package com.example.frontend

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.frontend.dto.Destination

class DestAdapter (var destList: List<Destination>, var setItem: Int)
    : RecyclerView.Adapter<DestAdapter.DestViewHolder>(){

    // 잡아주는 역할
    inner class DestViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val img : ImageView = itemView.findViewById<ImageView>(R.id.iv_destination) // 가계부 이미지
        val name : TextView = itemView.findViewById<TextView>(R.id.tv_destination_name) // 가계부 이름
    }

    fun setFilteredList(destList: List<Destination>){
        this.destList = destList
        notifyDataSetChanged()
    }

    // ViewHolder 생성시 뷰를 담아서 DestViewHolder에 적용하여 리턴
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DestViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(setItem, parent, false)
        return DestViewHolder(view)
    }

    override fun onBindViewHolder(holder: DestViewHolder, position: Int) {
        holder.img.setImageResource(destList[position].img)
        holder.name.text = destList[position].name
    }

    override fun getItemCount(): Int {
        return destList.size
    }


}