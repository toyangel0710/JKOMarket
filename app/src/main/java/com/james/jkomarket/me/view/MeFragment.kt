package com.james.jkomarket.me.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.james.jkomarket.R
import com.james.jkomarket.me.adapter.MeListingAdapter
import com.james.jkomarket.me.viewmodel.MeViewModel

class MeFragment : Fragment() {

    private lateinit var meViewModel: MeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_me, container, false)
        meViewModel = ViewModelProvider(this).get(MeViewModel::class.java)

        var addListingButton: FloatingActionButton = view.findViewById(R.id.fab)
        var recyclerView: RecyclerView = view.findViewById(R.id.recyclerview)

        val adapter = MeListingAdapter(requireContext(), meViewModel)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        meViewModel.allMyListing.observe(viewLifecycleOwner, Observer { listings ->
            listings?.let { adapter.setListing(it) }
        })



        return view
    }
}