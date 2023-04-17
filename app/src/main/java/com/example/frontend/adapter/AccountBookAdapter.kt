package com.example.frontend.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.frontend.ExpenditureActivity
import com.example.frontend.ProductSearchActivity
import com.example.frontend.R
import com.example.frontend.dto.AccountBook
import java.text.SimpleDateFormat
import kotlin.collections.ArrayList

class AccountBookAdapter(val accountBookList: ArrayList<AccountBook>) : RecyclerView.Adapter<AccountBookAdapter.CustomViewHolder>(){

    // ViewHolder가 생성될 때
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CustomViewHolder {
        // R.layout.account_book을 리스트에 붙여줌
        val view = LayoutInflater.from(parent.context).inflate(R.layout.account_book_list_item, parent, false) // context - 액티비티에서 담고 있는 모든 것
        return CustomViewHolder(view).apply {
            itemView.setOnClickListener {
                // 현재 클릭한 요소의 위치
                val curPos: Int = adapterPosition
                val accountBook: AccountBook = accountBookList.get(curPos)

                //Intent 이동 하여 해당 가계부의 지출 내역 보여주기
                Toast.makeText(parent.context, "여행이름 : ${accountBook.name}", Toast.LENGTH_LONG).show()
                val intent = Intent(parent.context, ExpenditureActivity::class.java)
                intent.putExtra("ACCOUNT_BOOK_NAME", accountBook.name)
                parent.context.startActivity(intent)
            }

        }
    }

    // onCreateViewHolder로 만들어준 뷰를 바인딩시켜주는 메서드
    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val dateFormat : SimpleDateFormat = SimpleDateFormat("yy-MM-dd")

        holder.img.setImageResource(accountBookList.get(position).img)
        holder.name.text =  accountBookList.get(position).name
        holder.startDate.text = dateFormat.format(accountBookList.get(position).startDate)
        holder.endDate.text = dateFormat.format(accountBookList.get(position).endDate)

    }

    // 리스트 요소 개수 리턴
    override fun getItemCount(): Int {
        return accountBookList.size
    }

    // 잡아주는 역할
    class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val img = itemView.findViewById<ImageView>(R.id.iv_account_book_city) // 가계부 이미지
        val name = itemView.findViewById<TextView>(R.id.tv_account_book_name) // 가계부 이름
        val startDate = itemView.findViewById<TextView>(R.id.tv_account_book_start_date) // 여행 시작 날짜
        val endDate = itemView.findViewById<TextView>(R.id.tv_account_book_end_date) // 여행 종료 날짜

    }

}