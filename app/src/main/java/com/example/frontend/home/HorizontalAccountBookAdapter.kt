package com.example.frontend.home

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.frontend.CreateAccountBook
import com.example.frontend.Expenditure.ExpenditureActivity
import com.example.frontend.R
import com.example.frontend.dto.AccountBook

class HorizontalAccountBookAdapter(var accountbookList: List<AccountBook>) : RecyclerView.Adapter<HorizontalAccountBookAdapter.MyViewHolder>(){


    // 인자로 받은 뷰에
    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val img = itemView.findViewById<ImageView>(R.id.iv_account_book_city)
        val name = itemView.findViewById<TextView>(R.id.tv_name)
    }

    // 리사이클러 뷰 생성 시 my_account_book_horizontal.xml을 element로 하여 MyViewHolder에 인자로 넣어 제공
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.my_account_book_horizontal, parent, false)
        return MyViewHolder(view).apply {
            itemView.setOnClickListener {
                val curPos: Int = adapterPosition

                val accountBook: AccountBook = accountbookList.get(curPos)

                // 클릭한 가계부의 지출내역 페이지로 이동
                if(curPos == 0){
                    // 가계부 생성 폼으로 이동
                    val intent = Intent(parent.context, CreateAccountBook::class.java)
                    parent.context.startActivity(intent)
                }else{
                    Toast.makeText(parent.context, "여행이름 : ${accountBook.name}", Toast.LENGTH_LONG).show()
                    val intent = Intent(parent.context, ExpenditureActivity::class.java)
                    intent.putExtra("ACCOUNT_BOOK_NAME", accountBook.name)
                    parent.context.startActivity(intent)
                }
            }
        }
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

            holder.img.setImageResource(R.drawable.bali)
            holder.name.text = accountbookList.get(position).name

    }

    // item수 턴
    override fun getItemCount(): Int {
        return accountbookList.size
    }

//    private fun translateCityToImg(cityName: String) : Int{
//
//    }
}