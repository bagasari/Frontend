package com.example.frontend.product

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.frontend.R

class ProductSearchAdapter (private var productList: List<String>, private val itemClickListener: OnItemClickListener) : RecyclerView.Adapter<ProductSearchAdapter.ProductSearchViewHolder>() {

    interface OnItemClickListener{
        fun onItemClick(productName: String)
    }

    fun setProductList(productList:  List<String>){
        this.productList = productList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductSearchViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_product_search, parent, false)
        val viewHolder = ProductSearchViewHolder(view)

        // 클릭 리스너 등록 - 품목 클릭 시 품목명 전달
        viewHolder.cvProduct.setOnClickListener {
            val position = viewHolder.adapterPosition
            val productName = productList[position]
            itemClickListener.onItemClick(productName)
        }

        return viewHolder
    }

    override fun onBindViewHolder(holder: ProductSearchViewHolder, position: Int) {
        // 품목명 설정
        holder.tvProductName.text = productList[position]
    }

    override fun getItemCount(): Int = productList.size

    class ProductSearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val cvProduct: CardView = itemView.findViewById(R.id.product_search_cv)
        val tvProductName: TextView = itemView.findViewById(R.id.product_search_tv_name)
    }
}