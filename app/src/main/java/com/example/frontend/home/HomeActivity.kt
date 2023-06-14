package com.example.frontend.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.example.frontend.*
import com.example.frontend.accountBook.AccountBookListFragment
import com.example.frontend.accountBook.AccountService
import com.example.frontend.database.AppDatabase
import com.example.frontend.database.City
import com.example.frontend.database.Country
import com.example.frontend.databinding.ActivityHomeBinding
import com.example.frontend.dto.Destination
import com.example.frontend.retrofit.RetrofitClient
import com.example.frontend.utils.Utils
import kotlinx.coroutines.*
import org.json.JSONObject

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
    private lateinit var cityList: List<Destination>
    private lateinit var countryList: List<Destination>

    // retrofit 통신
    private val retrofit = RetrofitClient.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // [YHJ 4/12] : DB 세팅
        settingDB()

        // [YHJ 4/12] : BottomNavigationBar 바인딩
        mBinding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initNavigationBar()

    }

    // [YHJ 4/11] : 바텀 네비게이션바 버튼 선택시 변경
    private fun changeFragment(fragment: Fragment) {
        supportFragmentManager.
            beginTransaction().
            replace(R.id.container, fragment).
            commit()
    }

    // [YHJ 4/11] : 바텀 네비게이션바 실행 메소드
    private fun initNavigationBar() {

        binding.navView.run {
            setOnItemSelectedListener { item ->
                when(item.itemId){
                    R.id.item_home -> {
                        changeFragment(homeFragment)
                    }
                    R.id.item_search -> {
                        changeFragment(searchFragment)
                    }
                    R.id.item_account_book -> {
                        changeFragment(myAccountBookListFragment)
                        getAccountBookList()
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
     private fun settingDB(){
        db = AppDatabase.getInstance(this)
        CoroutineScope(Dispatchers.Main).launch {
            async(Dispatchers.IO){
                countryList = db!!.countryDao().getCountryNameAndImg()
                cityList = db!!.cityDao().getCityNameAndImg()

                for(country in countryList){
                    Log.v("test", "국가명 : " + country.name + ", 국가 img : " + country.img)
                }

                for(city in cityList){
                    Log.v("test", "도시명 : " + city.name)
                }
            }
        }
    }

    fun getAccountBookList() {
        // I/O 작업을 비동기적으로 처리하기 위한 코루틴 스코프를 생성
        val scope = CoroutineScope(Job() + Dispatchers.IO)
        scope.launch {
            try {
                // 로그인 요청
                val response = retrofit.create(AccountService::class.java).getAccountBookList()
                if (response.isSuccessful) {
                    Log.d("ACB", "가계부 리스트 통신 성공 ${response.body()}")
                    val accountBookDTO = response.body()


                } else {
                    val errorBody = JSONObject(response.errorBody()?.string() ?: "")
                    val errorCode = errorBody.optString("code")
                    Log.d("ACB", "가계부 리스트 통신 실패 $errorBody")
                    withContext(Dispatchers.Main){
                        when (errorCode) {
                            "A002" -> Utils.showToast(this@HomeActivity,"존재하지 않는 이메일")
                            "A003" -> Utils.showToast(this@HomeActivity,"잘못된 비밀번호")
                        }
                    }
                }
            } catch (e: Exception) {
                Log.d("ACB", "API 호출 실패 $e")
            }
        }
    }


}