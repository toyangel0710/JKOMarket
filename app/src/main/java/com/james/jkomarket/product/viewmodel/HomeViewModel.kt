package com.james.jkomarket.product.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.james.jkomarket.account.UserState
import com.james.jkomarket.product.model.Listing
import com.james.jkomarket.room.JkoMarketDatabase
import com.james.jkomarket.room.ListingRepository

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: ListingRepository
    lateinit var allListing: LiveData<List<Listing>>
    private var userName: String?

    init {
        val listingDao = JkoMarketDatabase.getDatabase(application, viewModelScope).listingDao()
        repository = ListingRepository(listingDao)
        userName = UserState(application).userName
        userName?.let {
            // TODO Sort by listing count
            allListing = repository.getTopCategory(userName!!)
        }
    }

    fun refresh() {
        // TODO SwipeRefresh action
    }
}