package com.james.jkomarket.me.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.james.jkomarket.databinding.ListingItemBinding
import com.james.jkomarket.databinding.ListingItemTitleBinding
import com.james.jkomarket.me.viewmodel.MeViewModel
import com.james.jkomarket.product.model.Category
import com.james.jkomarket.product.model.Listing
import com.james.jkomarket.support.data.MultipleItemEntity


class MeListingAdapter internal constructor(
        context: Context,
        viewModel: MeViewModel
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        val VIEW_TYPE_TITLE = 1
        val VIEW_TYPE_CONTENT = 2
    }

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var listings = emptyList<MultipleItemEntity>()
    private val viewModel = viewModel

    inner class ListingViewHolder(binding: ListingItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val binding = binding
        fun bind(listing: Listing) {
            binding.data = listing
            binding.executePendingBindings()
            binding.listingDelete.setOnClickListener { v -> viewModel.onDeleteListingClick(listing) }
        }
    }

    inner class TitleViewHolder(binding: ListingItemTitleBinding) : RecyclerView.ViewHolder(binding.root) {
        val binding = binding
        fun bind(category: Category) {
            binding.data = category
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var binding: ViewDataBinding
        if (viewType == VIEW_TYPE_CONTENT) {
            binding = ListingItemBinding.inflate(inflater, parent, false)
            return ListingViewHolder(binding)
        } else {
            binding = ListingItemTitleBinding.inflate(inflater, parent, false)
            return TitleViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ListingViewHolder) {
            (holder as ListingViewHolder).bind(listings.get(position).getField(MultipleItemEntity.MultipleFields.ITEM_CONTENT)!!)
        } else {
            (holder as TitleViewHolder).bind(listings.get(position).getField(MultipleItemEntity.MultipleFields.ITEM_TITLE)!!)
        }
    }

    override fun getItemViewType(position: Int): Int { return listings.get(position).itemType }

    internal fun setListing(listings: List<MultipleItemEntity>) {
        this.listings = listings
        notifyDataSetChanged()
    }

    override fun getItemCount() = listings.size
}


