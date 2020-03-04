package com.james.jkomarket.room

import androidx.lifecycle.LiveData
import com.james.jkomarket.product.model.Category
import kotlinx.coroutines.flow.Flow

class CategoryRepository(private val categoryDao: CategoryDao) {

    suspend fun create(category: Category): Long = categoryDao.insert(category)

    fun getCategory(id: Long): LiveData<List<Category>> = categoryDao.getCategory(id)

    fun getAll(): LiveData<List<Category>> = categoryDao.getAll()

    fun getAllFlow(): Flow<List<Category>> = categoryDao.getAllFlow()
}