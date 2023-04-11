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

        db = AppDatabase.getInstance(this)
        CoroutineScope(Dispatchers.Main).launch {
            async(Dispatchers.IO){
                db!!.countryDao().insertCountry(Country(1,"일본", R.drawable.japan))
                db!!.cityDao().insertCity(City(1, "발리", R.drawable.bali, db!!.countryDao().getCountryId("일본")))
            }
        }
        mBinding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initNavigationBar()

        CoroutineScope(Dispatchers.Main).launch {
            async(Dispatchers.IO){
                var countryList : List<Country> = db!!.countryDao().getAllCountries()
                val country: Country = countryList.get(0)
                val city : City = db!!.cityDao().getCityByName("발리")

                Log.v("test", "국가명 : " + country.country_name + ", 국가 id : " + country.country_id)
                Log.v("test2", "도시명 : " + city.city_name + ", 국가 id : " + city.country_id)
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

}