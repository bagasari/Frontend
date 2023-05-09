package com.example.frontend.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.frontend.dto.Expenditure

class ExpendAdaptor (val expenditureList: ArrayList<Expenditure>) : RecyclerView.Adapter<ExpendAdaptor.ExpendViewHolder>(){
    inner class ExpendViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        // 하나의 커스텀 뷰에 필요한 view들, ex) textview, imageview 생성

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpendViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: ExpendViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }
}