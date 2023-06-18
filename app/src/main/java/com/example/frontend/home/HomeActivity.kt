package com.example.frontend.home

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.frontend.*
import com.example.frontend.accountBook.AccountBookListFragment
import com.example.frontend.database.AppDatabase
import com.example.frontend.databinding.ActivityHomeBinding
import com.example.frontend.dto.Destination
import kotlinx.coroutines.*

// 홈 Activity- Bottom Navigation + Home Fragment
open class HomeActivity : AppCompatActivity() {
    private var mBinding: ActivityHomeBinding? = null
    private val binding get() = mBinding!!

    // [YHJ 4/11] : fragment 필드
    private val homeFragment by lazy { HomeFragment() }
    private val searchFragment by lazy { SearchFragment() }
    private val myAccountBookListFragment by lazy { AccountBookListFragment() }

    // [YHJ 4/11] : DB 필드
    private var db: AppDatabase? = null
    private var destList = listOf<Destination>()
    private lateinit var cityList: List<Destination>
    private lateinit var countryList: List<Destination>
    private var userId: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val context: Context = this
        val name = intent.getStringExtra("userId")!!
        val sharedPref = context.getSharedPreferences("USER", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putString("EMAIL", name) // 예시: 문자열 값 저장
        editor.apply()

        if(!userId.equals("still"))
            userId = name

        // [YHJ 4/12] : DB 세팅
        settingDB(name)

        // [YHJ 4/12] : BottomNavigationBar 바인딩
        mBinding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }

    // [YHJ 4/11] : 바텀 네비게이션바 버튼 선택시 변경
    private fun changeFragment(fragment: Fragment, name: String) {

            val bundle = Bundle()
            bundle.putParcelableArrayList("destList", ArrayList(destList))
            bundle.putString("name", name)

            fragment.arguments = bundle
            supportFragmentManager.
            beginTransaction().
            replace(R.id.container, fragment).
            commit()

    }

    // [YHJ 4/11] : 바텀 네비게이션바 실행 메소드
    private fun initNavigationBar(name: String) {

        binding.navView.run {
            setOnItemSelectedListener { item ->
                when(item.itemId){
                    R.id.item_home -> {
                        changeFragment(homeFragment, name)
                    }
                    R.id.item_search -> {
                        changeFragment(searchFragment, name)
                    }
                    R.id.item_account_book -> {
                        changeFragment(myAccountBookListFragment, name)
                    }
                }
                true
            }
            // homeActivity로 넘어온 경우 초기 시작 화면 세팅
            val frag_num : String = intent.getStringExtra("FRAG_NUM")!!
            if(frag_num.equals("accountBook")){
                selectedItemId = R.id.item_account_book
            }else if(frag_num.equals("home")){
                selectedItemId = R.id.item_home
            }else{
                selectedItemId = R.id.item_search
            }

        }
    }

    // [YHJ 4/11] : 국가/도시 DB 초기 세팅 메서드
    private fun addToList() {

        destList = cityList
        destList += countryList

        destList[0].img = R.drawable.neworleans
        destList[1].img = R.drawable.newyork
        destList[2].img = R.drawable.lasvegas
        destList[3].img = R.drawable.boston
        destList[4].img = R.drawable.california
        destList[5].img = R.drawable.texas
        destList[6].img = R.drawable.hawaii

        destList[7].img = R.drawable.kyoto
        destList[8].img = R.drawable.tokyo
        destList[9].img = R.drawable.sapporo
        destList[10].img = R.drawable.osaka
        destList[11].img = R.drawable.fukuoka

        destList[12].img = R.drawable.guangzhou
        destList[13].img = R.drawable.beijing
        destList[14].img = R.drawable.shanghai
        destList[15].img = R.drawable.hangzhou

        destList[16].img = R.drawable.manila
        destList[17].img = R.drawable.boracay
        destList[18].img = R.drawable.bohol
        destList[19].img = R.drawable.cebu

        destList[20].img = R.drawable.japan
        destList[21].img = R.drawable.china
        destList[22].img = R.drawable.philippines
        destList[23].img = R.drawable.usa

    }

    // [YHJ 4/11] : 국가/도시 DB 초기 세팅 메서드
    private fun settingDB(name: String){
        db = AppDatabase.getInstance(this)
        CoroutineScope(Dispatchers.Main).launch {
            async(Dispatchers.IO){
                countryList = db!!.countryDao().getCountryNameAndImg()
                cityList = db!!.cityDao().getCityNameAndImg()
                addToList()
                initNavigationBar(name)
            }
        }
    }


}