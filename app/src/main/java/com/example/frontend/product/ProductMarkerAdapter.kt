package com.example.frontend.product

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.frontend.R

class ProductMarkerAdapter(private val context: Context, private var productList: List<ProductMarkerResponse>, private val itemClickListener: OnItemClickListener) : RecyclerView.Adapter<ProductMarkerAdapter.ProductMarkerViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(product: ProductMarkerResponse)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductMarkerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_product_map, parent, false)
        val viewHolder = ProductMarkerViewHolder(view)

        // 클릭 리스너 등록 - 품목 클릭 시 품목명 전달
        viewHolder.cvProduct.setOnClickListener {
            val position = viewHolder.adapterPosition   // 품목 위치
            val isLike = productList[position].isLike   // 좋아요 여부
            val likeNum = viewHolder.tvProductLike.text.toString().toInt()  // 좋아요 개수
            val product = productList[position] // 품목

            if(isLike){
                // 좋아요 눌린 상태 -> 좋아요 취소
                viewHolder.tvProductLike.text = (likeNum-1).toString()
                val likeOffDrawable = context.resources.getDrawable(R.drawable.ic_like_off, null)
                viewHolder.ivProductLike.setImageDrawable(likeOffDrawable)

            }
            else{
                // 좋아요 안눌린 상태 -> 좋아요
                viewHolder.tvProductLike.text = (likeNum+1).toString()
                val likeOnDrawable = context.resources.getDrawable(R.drawable.ic_like_on, null)
                viewHolder.ivProductLike.setImageDrawable(likeOnDrawable)
            }

            // 통신 - 좋아요/ 좋아요 취소
            itemClickListener.onItemClick(product = product)

            // 좋아요 상태 저장
            productList[position].isLike = !isLike
        }

        return viewHolder
    }

    override fun onBindViewHolder(holder: ProductMarkerViewHolder, position: Int) {
        // 품목 - 이름, 가격, 지출 날짜, 상세 내용, 국가, 도시
        // 먹거리 extends 품목 - 개수, 무게, 위도, 경도
        // 교통 extends 품목 - 출발지 위도/경도, 도착지 위도/경도, 교통 수단 타입

        // 이름
        holder.tvProductName.text = productList[position].product.name

        // 가격
        val price = productList[position].product.price.toString() +"원"
        holder.tvProductPrice.text = price

        // 구매날짜
        holder.tvProductDate.text = productList[position].product.purchaseDate

        // 추천
        holder.tvProductLike.text = productList[position].product.like.toString()
    }

    override fun getItemCount(): Int = productList.size

    class ProductMarkerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val cvProduct: CardView = itemView.findViewById(R.id.product_map_cv)
        val tvProductName: TextView = itemView.findViewById(R.id.product_map_tv_name)
        val tvProductPrice: TextView = itemView.findViewById(R.id.product_map_tv_price)
        val tvProductDate: TextView = itemView.findViewById(R.id.product_map_tv_date)
        val ivProductLike: ImageView = itemView.findViewById(R.id.product_map_iv_like)
        val tvProductLike: TextView = itemView.findViewById(R.id.product_map_tv_like)
    }
}