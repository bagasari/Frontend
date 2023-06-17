package com.example.frontend.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.frontend.R
import com.example.frontend.accountBook.DestAdapter
import com.example.frontend.database.AppDatabase
import com.example.frontend.databinding.ActivityDestinationSearchListBinding
import com.example.frontend.dto.Destination
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

// 검색 fragment
class SearchFragment : Fragment() {

    private lateinit var binding: ActivityDestinationSearchListBinding
    private lateinit var searchView: SearchView
    private lateinit var destAdapter: DestAdapter


    private var db: AppDatabase? = null
    private lateinit var cityList: List<Destination>
    private lateinit var countryList: List<Destination>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // setContentView() 한 것임
        val view = inflater.inflate(R.layout.activity_destination_search_list, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activityDestinationSearchListBinding = ActivityDestinationSearchListBinding.bind(view)

        val bundle = arguments
        val destList = bundle?.getParcelableArrayList<Destination>("destList")?.toList()
        Log.d("HomeFragment", destList.toString())
        //settingDB()
        binding = activityDestinationSearchListBinding
        searchView = binding.svDestination

        destAdapter = DestAdapter(destList!!, "search", requireContext())



        binding.rvDestination.layoutManager = LinearLayoutManager(context)
        binding.rvDestination.adapter = destAdapter



        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText, destList)
                return true
            }

        })
    }

    private fun filterList(query: String?, destList: List<Destination>) {
        if (query != null) {
            val filteredList = ArrayList<Destination>()
            for (i in destList) {
                if (i.name.contains(query)) {
                    filteredList.add(i)
                }
            }
            if (filteredList.isEmpty()) {
                Toast.makeText(context, "등록되지 않은 도시입니다.", Toast.LENGTH_SHORT).show()
            } else {
                destAdapter.setFilteredList(filteredList)
            }
        }
    }
}
