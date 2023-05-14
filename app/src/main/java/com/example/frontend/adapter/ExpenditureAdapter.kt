package com.example.frontend.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.frontend.R
import com.example.frontend.dto.Expenditure
import com.example.frontend.dto.Product
import com.example.frontend.home.HomeFragment
import org.w3c.dom.Text

class ExpenditureAdapter (val productList: ArrayList<Product>) : RecyclerView.Adapter<ExpenditureAdapter.MyViewHolder>(){
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
        if(productList.get(position).productType.equals("Transportation")){
            holder.productType.setImageResource(R.drawable.ic_transportation)
        }else{
            holder.productType.setImageResource(R.drawable.ic_food)
        }
        holder.name.text = productList.get(position).name
        holder.city.text = productList.get(position).city
        holder.price.text = productList.get(position).price.toString()
    }

    override fun getItemCount(): Int {
        return productList.size
    }
}