package com.example.frontend.adapter

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
import androidx.annotation.Nullable
import androidx.recyclerview.widget.RecyclerView
import com.example.frontend.ProductSearchActivity
import com.example.frontend.R
import com.example.frontend.dto.Destination

class DestAdapter (var destList: List<Destination>, var setItem: Int, var context: Context)
    : RecyclerView.Adapter<DestAdapter.DestViewHolder>(){

    // 잡아주는 역할
    inner class DestViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val img : ImageView = itemView.findViewById<ImageView>(R.id.iv_destination) // 가계부 이미지
        val name : TextView = itemView.findViewById<TextView>(R.id.tv_destination_name) // 가계부 이름
        lateinit var btn: Button

        public fun setBtn(setBtn: Int){
            btn = itemView.findViewById<Button>(R.id.btn_choose)
        }
    }

    fun setFilteredList(destList: List<Destination>){
        this.destList = destList
        notifyDataSetChanged()
    }

    // onCreateViewHolder : 어떤 목록 레이아웃을 만들 것인지 반환(어떤 뷰를 생성할 것인가)
    // ViewHolder 생성시 뷰를 담아서 DestViewHolder에 적용하여 리턴
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DestViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(setItem, parent, false)
        if(setItem == R.layout.destination_search_item){

            return DestViewHolder(view).apply {
                itemView.setOnClickListener {
                    val curPos: Int = adapterPosition
                    val destList: Destination = destList.get(curPos)

                    //Intent 이동 하여 해당 가계부의 지출 내역 보여주기
                    Toast.makeText(parent.context, "여행이름 : ${destList.name}", Toast.LENGTH_LONG).show()
                    val intent = Intent(context, ProductSearchActivity::class.java)
                    intent.putExtra("DEST_NAME", destList.name)
                    context.startActivity(intent)

                }
            }
        }else if(setItem == R.layout.destination_select_item){
            // 클릭한
            return DestViewHolder(view).apply {

            }
        }else{
            return DestViewHolder(view)
        }
    }

    // onBindViewHolder : 생성된 뷰에 무슨 데이터를 넣을 것인가
    override fun onBindViewHolder(holder: DestViewHolder, position: Int) {
        holder.img.setImageResource(destList[position].img)
        holder.name.text = destList[position].name

        if(setItem == R.layout.destination_select_item){
            var chosen_dest_list : ArrayList<Destination> = arrayListOf()
            holder.setBtn(R.id.btn_choose)
            holder.btn.setOnClickListener(object : View.OnClickListener{
                override fun onClick(p0: View?) {

                    Log.v("test", "여행지 이름" + destList[holder.adapterPosition].name)

                    chosen_dest_list.add(Destination(destList[holder.adapterPosition].name, destList[holder.adapterPosition].img))
                }
            })
        }

    }

    override fun getItemCount(): Int {
        return destList.size
    }



}