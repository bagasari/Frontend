package com.example.frontend

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.frontend.adapter.DestAdapter
import com.example.frontend.databinding.ActivityDestinationSelectListBinding
import com.example.frontend.dto.Destination

class SearchFragment : Fragment() {

    private lateinit var binding: ActivityDestinationSelectListBinding
    private lateinit var searchView: SearchView
    private var destList = ArrayList<Destination>()
    private lateinit var destAdapter: DestAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // setContentView() 한 것임
        val view = inflater.inflate(R.layout.activity_destination_select_list, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activityDestinationSelectListBinding = ActivityDestinationSelectListBinding.bind(view)
        binding = activityDestinationSelectListBinding
        searchView = binding.svDestination

        destAdapter = DestAdapter(destList, R.layout.destination_search_item, requireContext())
        addToList()
        binding.rvDestination.layoutManager = LinearLayoutManager(context)
        binding.rvDestination.adapter = destAdapter



        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText)
                return true
            }

        })
    }

    private fun filterList(query : String?){
        if(query != null){
            val filteredList = ArrayList<Destination>()
            for(i in destList){
                if(i.name.contains(query)){
                    filteredList.add(i)
                }
            }
            if(filteredList.isEmpty()){
                Toast.makeText(context, "등록되지 않은 도시입니다.", Toast.LENGTH_SHORT).show()
            }else{
                destAdapter.setFilteredList(filteredList)
            }
        }
    }

    private fun addToList() {
        destList.add(Destination("bali", R.drawable.bali))
        destList.add(Destination("japan", R.drawable.japan))
        destList.add(Destination("usa", R.drawable.usa))
        destList.add(Destination("philippine", R.drawable.philippine))
        destList.add(Destination("china", R.drawable.china))
    }

}




