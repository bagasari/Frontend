package com.example.frontend

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.example.frontend.databinding.ActivityHomeBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class HomeActivity : AppCompatActivity() {
    private var mBinding: ActivityHomeBinding? = null
    private val binding get() = mBinding!!

    // [YHJ 4/11] : fragment 필드
    private val homeFragment by lazy {HomeFragment()}
    private val searchFragment by lazy {SearchFragment()}
    private val myAccountBookListFragment by lazy {MyAccountBookListFragment()}

    // [YHJ 4/11] : DB 필드
    private var db: AppDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // [YHJ 4/12] : DB 세팅
        settingDB()

        // [YHJ 4/12] : BottomNavigationBar 바인딩
        mBinding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initNavigationBar()

        // [YHJ 4/12] : DB Test
        CoroutineScope(Dispatchers.Main).launch {
            async(Dispatchers.IO){
                var countryList : List<Country> = db!!.countryDao().getAllCountries()

                val city : City = db!!.cityDao().getCityByName("뉴올리언스")

                for(country in countryList){
                    Log.v("test", "국가명 : " + country.country_name + ", 국가 id : " + country.country_id)
                }
            }
        }
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
            selectedItemId = R.id.item_home
        }
    }

    // [YHJ 4/11] : 국가/도시 DB 초기 세팅 메서드
    private fun settingDB(){
        db = AppDatabase.getInstance(this)
        CoroutineScope(Dispatchers.Main).launch {
            async(Dispatchers.IO){

                // 국가 정보 삽입
                db!!.countryDao().insertCountry(Country(1,"일본", R.drawable.japan))
                db!!.countryDao().insertCountry(Country(2, "중국", R.drawable.china))
                db!!.countryDao().insertCountry(Country(3, "필리핀", R.drawable.philippine))
                db!!.countryDao().insertCountry(Country(4, "미국", R.drawable.usa))
//                db!!.countryDao().insertCountry(Country(5, "인도네시아", R.drawable.Indonesia))
//                db!!.countryDao().insertCountry(Country(6, "베트남", R.drawable.vietnam))
//                db!!.countryDao().insertCountry(Country(7, "프랑스", R.drawable.france))
//                db!!.countryDao().insertCountry(Country(8, "영국", R.drawable.uk))
//                db!!.countryDao().insertCountry(Country(9, "이탈리아", R.drawable.italy))

                //도시 정보 삽입
                db!!.cityDao().insertCity(City(1, "오사카", R.drawable.bali, db!!.countryDao().getCountryId("일본")))
                db!!.cityDao().insertCity(City(2, "도쿄", R.drawable.bali, db!!.countryDao().getCountryId("일본")))
                db!!.cityDao().insertCity(City(3, "교토", R.drawable.bali, db!!.countryDao().getCountryId("일본")))
                db!!.cityDao().insertCity(City(4, "후쿠오카", R.drawable.bali, db!!.countryDao().getCountryId("일본")))
                db!!.cityDao().insertCity(City(5, "삿포로", R.drawable.bali, db!!.countryDao().getCountryId("일본")))

                db!!.cityDao().insertCity(City(6, "베이징", R.drawable.bali, db!!.countryDao().getCountryId("중국")))
                db!!.cityDao().insertCity(City(7, "상하이", R.drawable.bali, db!!.countryDao().getCountryId("중국")))
                db!!.cityDao().insertCity(City(8, "광저우", R.drawable.bali, db!!.countryDao().getCountryId("중국")))
                db!!.cityDao().insertCity(City(9, "항저우", R.drawable.bali, db!!.countryDao().getCountryId("중국")))

                db!!.cityDao().insertCity(City(10, "세부", R.drawable.bali, db!!.countryDao().getCountryId("필리핀")))
                db!!.cityDao().insertCity(City(11, "보라카이", R.drawable.bali, db!!.countryDao().getCountryId("필리핀")))
                db!!.cityDao().insertCity(City(12, "마닐라", R.drawable.bali, db!!.countryDao().getCountryId("필리핀")))
                db!!.cityDao().insertCity(City(13, "보홀", R.drawable.bali, db!!.countryDao().getCountryId("필리핀")))

                db!!.cityDao().insertCity(City(14, "뉴욕", R.drawable.bali, db!!.countryDao().getCountryId("미국")))
                db!!.cityDao().insertCity(City(15, "샌프란시스코", R.drawable.bali, db!!.countryDao().getCountryId("미국")))
                db!!.cityDao().insertCity(City(16, "뉴올리언스", R.drawable.bali, db!!.countryDao().getCountryId("미국")))
                db!!.cityDao().insertCity(City(17, "텍사스", R.drawable.bali, db!!.countryDao().getCountryId("미국")))
                db!!.cityDao().insertCity(City(18, "보스턴", R.drawable.bali, db!!.countryDao().getCountryId("미국")))
                db!!.cityDao().insertCity(City(19, "라스베가스", R.drawable.bali, db!!.countryDao().getCountryId("미국")))
                db!!.cityDao().insertCity(City(20, "하와이", R.drawable.bali, db!!.countryDao().getCountryId("미국")))
                //  db!!.cityDao().insertCity(City(1, "발리", R.drawable.bali, db!!.countryDao().getCountryId("인도네시아")))
            }
        }
    }



}