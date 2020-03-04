package com.james.jkomarket.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.james.jkomarket.product.model.Listing
import kotlinx.coroutines.flow.Flow

@Dao
interface ListingDao {

    @Insert
    suspend fun insert(listing: Listing): Long

    @Query("DELETE FROM listing_table WHERE user_name = :userName and id = :id")
    suspend fun delect(userName: String, id: Long): Int

    @Query("SELECT * from listing_table WHERE id = :id")
    fun getListing(id: Long): LiveData<List<Listing>>

    @Query("SELECT * from listing_table WHERE user_name = :name ORDER BY category, id ASC")
    fun getMyListings(name: String): LiveData<List<Listing>>

    @Query("SELECT * from listing_table WHERE user_name = :name ORDER BY category, id ASC")
    fun getMyListingsFlow(name: String): Flow<MutableList<Listing>>

    @Query("SELECT * from listing_table WHERE category = :category")
    fun getCategory(category: Int): LiveData<List<Listing>>

    @Query("SELECT * from listing_table")
    fun getTopCategory(): LiveData<List<Listing>>

    @Query("DELETE FROM listing_table")
    suspend fun deleteAll()
}