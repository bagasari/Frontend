package com.example.frontend.expenditure

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.frontend.R
import com.example.frontend.expenditure.Product

class ExpenditureAdapter() : RecyclerView.Adapter<ExpenditureAdapter.MyViewHolder>(){

    private var productList: MutableList<Product> = mutableListOf()

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        // 하나의 커스텀 뷰에 필요한 view들, ex) textview, imageview 생성
        val productType = itemView.findViewById<ImageView>(R.id.iv_category)
        val name = itemView.findViewById<TextView>(R.id.tv_product_name)
        val city = itemView.findViewById<TextView>(R.id.tv_product_city)
        val price = itemView.findViewById<TextView>(R.id.tv_product_price)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.expenditure_item, parent, false)
        return MyViewHolder(view).apply{
            itemView.setOnClickListener {
                val curPos: Int = adapterPosition
            }
        }
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        if(productList.isNotEmpty()){
            holder.productType.setImageResource(R.drawable.ic_food)
            holder.name.text = productList.get(position).name
            holder.city.text = productList.get(position).city
            holder.price.text = productList.get(position).price.toString()

        }
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    fun setData(newList: MutableList<Product>){
        productList = newList
        notifyDataSetChanged()
    }

}