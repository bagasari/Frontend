package com.example.frontend.product

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.frontend.R

class ProductAdapter(private val productList: ArrayList<TestProduct>) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

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


    class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val ivProductImage: ImageView = itemView.findViewById(R.id.product_iv_img)
        val tvProductName: TextView = itemView.findViewById(R.id.product_tv_name)
        val tvProductPrice: TextView = itemView.findViewById(R.id.product_tv_price)
    }
}