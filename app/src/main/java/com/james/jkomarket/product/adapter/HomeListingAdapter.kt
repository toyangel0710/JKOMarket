package com.james.jkomarket.me.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.james.jkomarket.databinding.ListingItemBinding
import com.james.jkomarket.product.model.Listing


class HomeListingAdapter internal constructor(
        context: Context
) : RecyclerView.Adapter<HomeListingAdapter.ListingViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var listings = emptyList<Listing>()

        inner class ListingViewHolder(binding: ListingItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val binding = binding
        fun bind(listing: Listing){
            binding.data = listing
            binding.listingDelete.visibility = View.INVISIBLE
            binding.executePendingBindings()

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListingViewHolder {
        // TODO inflater anthoer view for category title
        val binding = ListingItemBinding.inflate(inflater, parent, false)
        return ListingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListingViewHolder, position: Int) {
        holder.bind(listings[position])
    }

    internal fun setListing(listings: List<Listing>) {
        this.listings = listings
        notifyDataSetChanged()
    }

    override fun getItemCount() = listings.size
}


