package com.example.frontend.accountBook

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.frontend.R
import com.example.frontend.dto.Destination
import com.example.frontend.home.HorizontalAccountBookAdapter

class DestListAdapter() : RecyclerView.Adapter<DestListAdapter.MyViewHolder>(){

    private val selectedDestList : MutableList<Destination> = mutableListOf()

    // 인자로 받은 뷰에
    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val img = itemView.findViewById<ImageView>(R.id.iv_account_book_city)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        TODO("Not yet implemented")


    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    fun addItem(destination: Destination){
        selectedDestList.add(destination)
        notifyDataSetChanged()
    }
}