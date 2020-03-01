package com.james.jkomarket.room

import androidx.lifecycle.LiveData
import com.james.jkomarket.product.model.Category

class CategoryRepository(private val categoryDao: CategoryDao) {

    suspend fun create(category: Category): Long = categoryDao.create(category)

    fun getCategory(id: Long): LiveData<List<Category>> = categoryDao.getCategory(id)
}