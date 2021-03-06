package com.james.jkomarket.room

import androidx.lifecycle.LiveData
import com.james.jkomarket.product.model.Listing

class ListingRepository(private val listingDao: ListingDao) {

    suspend fun insert(listing: Listing): Long = listingDao.insert(listing)

    suspend fun delete(userName: String, id: Long): Int {
        // TODO Check if user mismatch

        return listingDao.delect(userName, id)
    }

    fun getListing(userName: String, id: Long): LiveData<List<Listing>> {
        // TODO Check if user not exist

        return listingDao.getListing(id)
    }

    fun getMyListings(userName: String): LiveData<List<Listing>> {
        return listingDao.getMyListings(userName)
    }

    fun getCategory(userName: String, category: Int): LiveData<List<Listing>> {
        // TODO Check if user not exist
        // TODO Check if category not found

        return listingDao.getCategory(category)
    }

    fun getTopCategory(userName: String): LiveData<List<Listing>> {
        // TODO Check if user not exist
        // TODO Sort by category count
        return listingDao.getTopCategory()
    }
}