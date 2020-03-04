package com.james.jkomarket.me.viewmodel

import android.app.Application
import android.text.TextUtils
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.james.jkomarket.account.UserState
import com.james.jkomarket.me.adapter.MeListingAdapter
import com.james.jkomarket.product.model.Category
import com.james.jkomarket.product.model.Listing
import com.james.jkomarket.room.CategoryRepository
import com.james.jkomarket.room.JkoMarketDatabase
import com.james.jkomarket.room.ListingRepository
import com.james.jkomarket.support.data.MultipleItemEntity
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch

class MeViewModel(application: Application) : AndroidViewModel(application) {
    private val listingRepository: ListingRepository
    private val categoryRepository: CategoryRepository

    var allMyListing: MutableLiveData<List<MultipleItemEntity>> = MutableLiveData()
    private var userName: String?

    init {
        val listingDao = JkoMarketDatabase.getDatabase(application, viewModelScope).listingDao()
        val categoryDao = JkoMarketDatabase.getDatabase(application, viewModelScope).categoryDao()

        listingRepository = ListingRepository(listingDao)
        categoryRepository = CategoryRepository(categoryDao)

        userName = UserState(application).userName
        if (TextUtils.isEmpty(userName)) {
            // TODO Show warning

        } else {
            update()
        }
    }

    fun insert(listing: Listing) = viewModelScope.launch {
        listingRepository.insert(listing)
        update()
    }

    fun onDeleteListingClick(listing: Listing) {
        viewModelScope.launch {
            listingRepository.delete(listing.user_name, listing.id)
        }
    }

    fun update() {
        viewModelScope.launch {
            // Get category list Flow from the database.
            val categoryFlow = categoryRepository.getAllFlow().map { list ->
                val mutableMap: MutableMap<Long, Category> = mutableMapOf()
                list.forEach {
                    mutableMap.put(it.id, it)
                }

                mutableMap
            }

            // Get listing Flow from the database
            val listingFlow = listingRepository.getMyListingsFlow(userName!!)

            // Combine the two Flows to build a multiple item entity used for the adapter.
            categoryFlow.zip(listingFlow) { categoryMap, listings ->
                var category = 0L
                var entities: MutableList<MultipleItemEntity> = mutableListOf()

                listings.forEach {
                    if (category != it.category) {
                        // Add category title item to the list
                        category = it.category
                        entities.add(
                            MultipleItemEntity
                                .builder()
                                .setItemType(MeListingAdapter.VIEW_TYPE_TITLE)
                                .setField(
                                    MultipleItemEntity.MultipleFields.ITEM_TITLE,
                                    categoryMap.get(it.category)!!
                                )
                                .build()
                        )
                    }
                    entities.add(
                        MultipleItemEntity
                            .builder()
                            .setItemType(MeListingAdapter.VIEW_TYPE_CONTENT)
                            .setField(MultipleItemEntity.MultipleFields.ITEM_CONTENT, it)
                            .build()
                    )
                }

                entities
            }
            .collect { value ->
                allMyListing.postValue(value)
            }
        }
    }
}