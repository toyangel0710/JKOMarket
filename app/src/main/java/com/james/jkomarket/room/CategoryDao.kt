package com.james.jkomarket.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.james.jkomarket.product.model.Category
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {

    @Insert
    suspend fun insert(category: Category): Long

    @Query("SELECT * from category_table WHERE id = :id")
    fun getCategory(id: Long): LiveData<List<Category>>

    @Query("SELECT * from category_table")
    fun getAll(): LiveData<List<Category>>

    @Query("SELECT * from category_table")
    fun getAllFlow(): Flow<List<Category>>


    @Query("DELETE FROM category_table")
    suspend fun deleteAll()
}