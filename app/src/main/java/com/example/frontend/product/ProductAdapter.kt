package com.example.frontend.product

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.frontend.R

class ProductAdapter(private val context: Context, private var productList: List<ProductListResponse.ContentItem>, private val itemClickListener: OnItemClickListener) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {
    interface OnItemClickListener{
        fun onItemClick(productId: Long, productName: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)

        val viewHolder = ProductViewHolder(view)

        // 클릭 리스너 등록 - 품목 클릭 시 품목명 전달
        viewHolder.cvProduct.setOnClickListener {
            val position = viewHolder.adapterPosition
            val content = productList[position]
            itemClickListener.onItemClick(productId = content.product.id, productName = content.product.name)
        }

        return viewHolder
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        // 품목 - 이름, 가격, 지출 날짜, 상세 내용, 국가, 도시
        // 먹거리 extends 품목 - 개수, 무게, 위도, 경도
        // 교통 extends 품목 - 출발지 위도/경도, 도착지 위도/경도, 교통 수단 타입

        // 이름
        holder.tvProductName.text = productList[position].product.name

        // 가격
        val price = productList[position].product.price.toString() +"원"
        holder.tvProductPrice.text = price

        // 구매 날짜
        holder.tvProductDate.text = productList[position].product.purchaseDate

        // 추천
        holder.tvProductLike.text = productList[position].product.like.toString()

        // 좋아요 여부에 따른 변화
        if(productList[position].isLike){
            val likeOnDrawable = ContextCompat.getDrawable(context, R.drawable.ic_like_on)
            holder.ivProductLike.setImageDrawable(likeOnDrawable)
        }
        else{
            val likeOffDrawable = ContextCompat.getDrawable(context, R.drawable.ic_like_off)
            holder.ivProductLike.setImageDrawable(likeOffDrawable)
        }
    }

    override fun getItemCount(): Int = productList.size

    class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val cvProduct: CardView = itemView.findViewById(R.id.product_cv)
        val tvProductName: TextView = itemView.findViewById(R.id.product_tv_name)
        val tvProductPrice: TextView = itemView.findViewById(R.id.product_tv_price)
        val tvProductDate: TextView = itemView.findViewById(R.id.product_tv_date)
        val ivProductLike: ImageView = itemView.findViewById(R.id.product_iv_like)
        val tvProductLike: TextView = itemView.findViewById(R.id.product_tv_like)
    }
}