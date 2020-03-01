package com.james.jkomarket.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.james.jkomarket.product.model.Listing

@Dao
interface ListingDao {

    @Insert
    suspend fun create(listing: Listing): Long

    @Query("DELETE FROM listing_table WHERE user_name = :userName and id = :id")
    suspend fun delect(userName: String, id: Long)

    @Query("SELECT * from listing_table WHERE id = :id")
    fun getListing(id: Long): LiveData<List<Listing>>

    @Query("SELECT * from listing_table WHERE category = :category")
    fun getCategory(category: Int): LiveData<List<Listing>>

    @Query("SELECT * from listing_table")
    fun getTopCategory(): LiveData<List<Listing>>
}