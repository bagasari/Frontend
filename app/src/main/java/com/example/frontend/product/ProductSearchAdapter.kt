package com.example.frontend.product

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.frontend.R
import com.example.frontend.dto.Destination

class ProductSearchAdapter (private var productList: ArrayList<TestProduct>) : RecyclerView.Adapter<ProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        // 품목 - 이름, 가격, 지출 날짜, 상세 내용, 국가, 도시
        // 먹거리 extends 품목 - 개수, 무게, 위도, 경도
        // 교통 extends 품목 - 출발지 위도/경도, 도착지 위도/경도, 교통 수단 타입

        holder.tvProductName.text = productList[position].name
        holder.tvProductPrice.text = productList[position].price
    }

    override fun getItemCount(): Int = productList.size

    fun setProductList(productList: ArrayList<TestProduct>){
        this.productList = productList
        notifyDataSetChanged()
    }
}