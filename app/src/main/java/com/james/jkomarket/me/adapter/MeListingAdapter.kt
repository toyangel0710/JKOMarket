package com.james.jkomarket.me.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.james.jkomarket.R
import com.james.jkomarket.me.viewmodel.MeViewModel
import com.james.jkomarket.product.model.Listing


class MeListingAdapter internal constructor(
        context: Context,
        viewModel: MeViewModel
) : RecyclerView.Adapter<MeListingAdapter.ListingViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var listings = emptyList<Listing>()
    private val viewModel = viewModel

    inner class ListingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.listingTitle)
        val description: TextView = itemView.findViewById(R.id.listingDescription)
        val price: TextView = itemView.findViewById(R.id.listingPrice)
        val id: TextView = itemView.findViewById(R.id.listingId)
        val delete: ImageButton = itemView.findViewById(R.id.listingDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListingViewHolder {
        val itemView = inflater.inflate(R.layout.listing_item, parent, false)
        return ListingViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ListingViewHolder, position: Int) {
        val listing = listings[position]
        holder.title.text = listing.title
        holder.description.text = listing.description
        holder.price.text = listing.price.toString()
        holder.id.text = listing.id.toString()
        holder.delete.setOnClickListener { v -> viewModel.onDeleteListingClick(position) }
    }

    internal fun setListing(listings: List<Listing>) {
        this.listings = listings
        notifyDataSetChanged()
    }

    override fun getItemCount() = listings.size
}


