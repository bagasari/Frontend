package com.example.frontend.accountBook

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.frontend.R
import com.example.frontend.dto.Destination
import com.example.frontend.home.HorizontalAccountBookAdapter
import de.hdodenhof.circleimageview.CircleImageView
import java.util.*
import kotlin.collections.ArrayList

class DestListAdapter() : RecyclerView.Adapter<DestListAdapter.MyViewHolder>(){

    private val selectedDestList : MutableList<Destination> = mutableListOf()

    // 인자로 받은 뷰에
    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val img: CircleImageView = itemView.findViewById(R.id.iv_account_book_city)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.selected_destination, parent, false)
        return MyViewHolder(view).apply {
            itemView.setOnClickListener {
                val curPos: Int = adapterPosition
                selectedDestList.removeAt(curPos)
                notifyDataSetChanged()
            }
        }

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.img.setImageResource(selectedDestList.get(position).img)
    }

    override fun getItemCount(): Int {
        return selectedDestList.size
    }

    fun addItem(destination: Destination){
        selectedDestList.add(destination)
        notifyDataSetChanged()
    }

    fun selectDestName(): ArrayList<String> {
        var destName: ArrayList<String> = arrayListOf()
        for(i in selectedDestList)
            destName.add(i.name)
        return destName
    }
}