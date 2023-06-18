package com.example.frontend.accountBook

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.frontend.product.ProductActivity
import com.example.frontend.R
import com.example.frontend.dto.Destination
import de.hdodenhof.circleimageview.CircleImageView

class DestAdapter (var destList: List<Destination>, var setItem: String, var context: Context)
    : RecyclerView.Adapter<DestAdapter.DestViewHolder>(){

    private lateinit var itemClickListener : OnItemClickListener

    interface OnItemClickListener {
        fun onItemClick(itemName: String, itemImg: Int)
    }

    // 잡아주는 역할
    inner class DestViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val img : CircleImageView = itemView.findViewById(R.id.iv_destination) // 가계부 이미지
        val name : TextView = itemView.findViewById<TextView>(R.id.tv_destination_name) // 가계부 이름

    }

    fun setFilteredList(destList: List<Destination>){
        this.destList = destList
        notifyDataSetChanged()
    }

    // onCreateViewHolder : 어떤 목록 레이아웃을 만들 것인지 반환(어떤 뷰를 생성할 것인가)
    // ViewHolder 생성시 뷰를 담아서 DestViewHolder에 적용하여 리턴
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DestViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.destination_search_item, parent, false)
        if(setItem.equals("search")){

            return DestViewHolder(view).apply {
                itemView.setOnClickListener {
                    val curPos: Int = adapterPosition
                    val destList: Destination = destList.get(curPos)

                    //Intent 이동 하여 해당 가계부의 지출 내역 보여주기
                    val intent = Intent(context, ProductActivity::class.java)
                    intent.putExtra("DEST_NAME", destList.name)
                    context.startActivity(intent)

                }
            }
        }else if(setItem.equals("select")){

            // 클릭한
            return DestViewHolder(view).apply {
                itemView.setOnClickListener {
                    val curPos: Int = adapterPosition
                    //Toast.makeText(parent.context, "여행이름 : ${destList[curPos].name}", Toast.LENGTH_LONG).show()
                    val itemName = destList[curPos].name
                    val itemImg = destList[curPos].img
                    itemClickListener.onItemClick(itemName, itemImg)
                }
            }
        }else{
            return DestViewHolder(view)
        }
    }

    // onBindViewHolder : 생성된 뷰에 무슨 데이터를 넣을 것인가
    override fun onBindViewHolder(holder: DestViewHolder, position: Int) {
        holder.img.setImageResource(destList[position].img)
        holder.name.text = destList[position].name

    }

    override fun getItemCount(): Int {
        return destList.size
    }


    fun setItemClickListener(onItemClickListener: OnItemClickListener){
        this.itemClickListener = onItemClickListener
    }


}