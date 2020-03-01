package com.james.jkomarket.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.james.jkomarket.product.model.Listing

@Dao
interface ListingDao {

    @Insert
    suspend fun create(listing: Listing): Long

    @Query("DELETE FROM listing_table WHERE user_name = :userName and id = :id")
    fun delect(userName: String, id: Long)

    @Query("SELECT * from listing_table WHERE user_name = :userName and id = :id")
    fun getListing(userName: String, id: Long): LiveData<List<Listing>>

    @Query("SELECT * from listing_table WHERE user_name = :userName and category = :category")
    fun getCategory(userName: String, category: Int): LiveData<List<Listing>>

    @Query("SELECT * from listing_table")
    fun getTopCategory(userName: String, category: Int): LiveData<List<Listing>>
}