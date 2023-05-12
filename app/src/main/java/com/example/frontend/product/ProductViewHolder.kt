package com.example.frontend.product

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.frontend.R

class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
    val ivProductImage: ImageView = itemView.findViewById(R.id.product_iv_img)
    val tvProductName: TextView = itemView.findViewById(R.id.product_tv_name)
    val tvProductPrice: TextView = itemView.findViewById(R.id.product_tv_price)
}