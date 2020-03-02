package com.james.jkomarket.me.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.james.jkomarket.account.UserState
import com.james.jkomarket.product.model.Listing
import com.james.jkomarket.room.JkoMarketDatabase
import com.james.jkomarket.room.ListingRepository
import kotlinx.coroutines.launch

class MeViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: ListingRepository
    lateinit var allMyListing: LiveData<List<Listing>>
    private var userName: String?

    init {
        val listingDao = JkoMarketDatabase.getDatabase(application, viewModelScope).listingDao()
        repository = ListingRepository(listingDao)
        userName = UserState(application).userName
        userName?.let {
            allMyListing = repository.getMyListings(userName!!)
        }
    }

    fun insert(listing: Listing) = viewModelScope.launch {
        repository.insert(listing)
    }

    fun onDeleteListingClick(position: Int) {
        allMyListing.value?.let {
            val listing = it.get(position)
            viewModelScope.launch {
                repository.delete(listing.user_name, listing.id)
            }
        }
    }
}