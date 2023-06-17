package com.example.frontend.product

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.frontend.R

class ProductAdapter(private var productList: List<ProductListResponse.Product>, private val itemClickListener: OnItemClickListener) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {
    interface OnItemClickListener{
        fun onItemClick(productId: Long, productName: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        // 품목 - 이름, 가격, 지출 날짜, 상세 내용, 국가, 도시
        // 먹거리 extends 품목 - 개수, 무게, 위도, 경도
        // 교통 extends 품목 - 출발지 위도/경도, 도착지 위도/경도, 교통 수단 타입

        // 이름
        holder.tvProductName.text = productList[position].name

        // 가격
        val price = productList[position].price.toString() +"원"
        holder.tvProductPrice.text = price

        // 구매날짜
        holder.tvProductDate.text = productList[position].purchaseDate

        // 추천
        holder.tvProductLike.text = productList[position].like.toString()

        holder.cvProduct.setOnClickListener {
            val product = productList[position]
            itemClickListener.onItemClick(productId = product.id, productName = product.name)
        }
    }

    override fun getItemCount(): Int = productList.size

    class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val cvProduct: CardView = itemView.findViewById(R.id.product_cv)
        val tvProductName: TextView = itemView.findViewById(R.id.product_tv_name)
        val tvProductPrice: TextView = itemView.findViewById(R.id.product_tv_price)
        val tvProductDate: TextView = itemView.findViewById(R.id.product_tv_date)
        val tvProductLike: TextView = itemView.findViewById(R.id.product_tv_like)
    }
}