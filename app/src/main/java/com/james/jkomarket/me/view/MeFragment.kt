package com.james.jkomarket.me.view

import android.app.Activity
import android.content.Intent
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
import com.james.jkomarket.account.UserState
import com.james.jkomarket.me.adapter.MeListingAdapter
import com.james.jkomarket.me.viewmodel.MeViewModel
import com.james.jkomarket.product.model.Listing

class MeFragment : Fragment() {
    private val newListingRequestCode = 1
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

        addListingButton.setOnClickListener {
            startActivityForResult(Intent(activity, AddListingActivity::class.java), newListingRequestCode)
        }

        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == newListingRequestCode && resultCode == Activity.RESULT_OK) {
            val title = data?.getStringExtra(AddListingActivity.EXTRA_TITLE)
            val description = data?.getStringExtra(AddListingActivity.EXTRA_DESCRIPTION)
            val price = data?.getDoubleExtra(AddListingActivity.EXTRA_PRICE, 0.0)
            val category = data?.getIntExtra(AddListingActivity.EXTRA_CATEGORY, 0)
            val username = context?.let { UserState(it).userName }
            val listing = Listing(0, title!!, description!!, price!!, username!!, category!!.toLong())
            meViewModel.insert(listing)
        }
    }
}