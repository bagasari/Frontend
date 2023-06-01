package com.example.frontend.product

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.frontend.R

class ProductSearchAdapter (private var productList: List<String>) : RecyclerView.Adapter<ProductSearchAdapter.ProductSearchViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductSearchViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_product_search, parent, false)
        return ProductSearchViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductSearchViewHolder, position: Int) {
        // 품목 - 이름, 가격, 지출 날짜, 상세 내용, 국가, 도시
        // 먹거리 extends 품목 - 개수, 무게, 위도, 경도
        // 교통 extends 품목 - 출발지 위도/경도, 도착지 위도/경도, 교통 수단 타입

        holder.tvProductName.text = productList[position]
    }

    override fun getItemCount(): Int = productList.size

    fun setProductList(productList:  List<String>){
        this.productList = productList
        notifyDataSetChanged()
    }

    class ProductSearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val tvProductName: TextView = itemView.findViewById(R.id.product_search_tv_name)
    }
}