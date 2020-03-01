package com.james.jkomarket.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.james.jkomarket.product.model.Category

@Dao
interface CategoryDao {

    @Insert
    suspend fun create(category: Category): Long

    @Query("SELECT * from category_table WHERE id = :id")
    fun getCategory(id: Long): LiveData<List<Category>>
}