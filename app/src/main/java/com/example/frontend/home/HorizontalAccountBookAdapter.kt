package com.example.frontend.home

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.frontend.accountBook.CreateAccountBook
import com.example.frontend.expenditure.ExpenditureActivity
import com.example.frontend.R
import com.example.frontend.accountBook.AccountBook
import com.example.frontend.accountBook.GetAccountBookDTO
import com.example.frontend.accountBook.SelectDestinationActivity
import com.example.frontend.dto.Destination

class HorizontalAccountBookAdapter() : RecyclerView.Adapter<HorizontalAccountBookAdapter.MyViewHolder>(){
    private var accountBookList: MutableList<GetAccountBookDTO> = mutableListOf()
    private var destList: MutableList<Destination> = mutableListOf()

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

                val getAccountBookDTO: GetAccountBookDTO = accountBookList.get(curPos)

                // 클릭한 가계부의 지출내역 페이지로 이동
                if(curPos == 0){

                    Log.d("HorizontalAccountBookAd", "OnCreateViewHolderB")
                    // 가계부 생성 폼으로 이동
                    val intent = Intent(parent.context, SelectDestinationActivity::class.java)
                    intent.putParcelableArrayListExtra("destList", destList as ArrayList<Destination>)
                    parent.context.startActivity(intent)

                    Log.d("HorizontalAccountBookAd", "OnCreateViewHolderA")
                }else{
                    //Toast.makeText(parent.context, "여행이름 : ${getAccountBookDTO.name}", setDatat.LENGTH_LONG).show()
                    val intent = Intent(parent.context, ExpenditureActivity::class.java)
                    intent.putExtra("AccountBookId", getAccountBookDTO.id.toString())
                    parent.context.startActivity(intent)
                }
            }
        }
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val city = accountBookList.get(position).city
        val dest: Destination? = destList?.find{ it.name == city}
        val TAG ="HorizontalAccountBook"
        Log.d(TAG, city + " before setImg")
        Log.d(TAG, destList.toString())
        if (dest != null) {
            holder.img.setImageResource(dest.img)
            Log.d(TAG, dest.name+ " : img match")
        }else{
            holder.img.setImageResource(R.drawable.ic_account_book)
            Log.d(TAG, "No name")
        }
        Log.d(TAG, "success setImg")
        holder.name.text = accountBookList.get(position).name
    }

    // item수 턴
    override fun getItemCount(): Int {
        return accountBookList.size
    }


    fun setData(aList: ArrayList<GetAccountBookDTO>, dList: ArrayList<Destination>){
        accountBookList.clear()
        accountBookList.addAll(aList)
        destList.clear()
        destList.addAll(dList)
        notifyDataSetChanged()
    }
//    private fun translateCityToImg(cityName: String) : Int{
//
//    }
}