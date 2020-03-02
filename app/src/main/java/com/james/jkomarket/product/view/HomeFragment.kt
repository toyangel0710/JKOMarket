package com.james.jkomarket.product.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.james.jkomarket.R
import com.james.jkomarket.me.adapter.HomeListingAdapter
import com.james.jkomarket.me.adapter.MeListingAdapter
import com.james.jkomarket.product.model.Listing
import com.james.jkomarket.product.viewmodel.HomeViewModel

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val swipeRefresh: SwipeRefreshLayout = view.findViewById(R.id.swipeRefresh)
        swipeRefresh.setOnRefreshListener { homeViewModel.refresh() }

        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(activity)

        val adapter = HomeListingAdapter(requireContext())
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        homeViewModel.allListing.observe(viewLifecycleOwner, Observer { listings ->
            listings?.let { adapter.setListing(it) }
        })


        return view
    }
}