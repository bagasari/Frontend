package com.example.frontend

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.example.frontend.*
import com.example.frontend.database.AppDatabase
import com.example.frontend.databinding.ActivityHomeBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

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
    private lateinit var cityList: List<City>
    private lateinit var countryList: List<Country>

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
//                db!!.cityDao().deleteAll()
//               db!!.countryDao().deleteAll()
                countryList = db!!.countryDao().getAllCountries()
                cityList = db!!.cityDao().getAllCities()

                for(country in countryList){
                    Log.v("test", "update : " + country.country_name + ", drawable : " + resources.getIdentifier(country.country_name_eng, "drawable", packageName))
                    db!!.countryDao().updateCountryImg(resources.getIdentifier("japan", "drawable", packageName))
                }
                // db!!.countryDao().updateCountryImg()

                for(country in countryList){
                    Log.v("test", "국가명 : " + country.country_name + ", 국가 id : " + country.country_id + ", 국가 img : " + country.country_img)
                }

                for(city in cityList){
                    Log.v("test", "도시명 : " + city.city_name + ", 도시 id : " + city.city_id)
                }
            }
        }
    }

    fun getCityList() : List<City>{
        return cityList
    }

    fun getCountryList() : List<Country>{
        return countryList
    }



}