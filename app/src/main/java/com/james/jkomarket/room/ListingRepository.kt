package com.james.jkomarket.room

import androidx.lifecycle.LiveData
import com.james.jkomarket.product.model.Listing

class ListingRepository(private val listingDao: ListingDao) {

    suspend fun create(listing: Listing): Long = listingDao.create(listing)

    suspend fun delete(userName: String, id: Long) {
        // TODO Check if user mismatch

        return listingDao.delect(userName, id)
    }

    fun getListing(userName: String, id: Long): LiveData<List<Listing>> {
        // TODO Check if user not exist

        return listingDao.getListing(id)
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