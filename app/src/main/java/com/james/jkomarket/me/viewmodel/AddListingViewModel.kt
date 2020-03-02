package com.james.jkomarket.me.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.james.jkomarket.product.model.Category
import com.james.jkomarket.room.CategoryRepository
import com.james.jkomarket.room.JkoMarketDatabase

class AddListingViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: CategoryRepository
    var allCategory: LiveData<List<Category>>

    init {
        val categoryDao = JkoMarketDatabase.getDatabase(application, viewModelScope).categoryDao()
        repository = CategoryRepository(categoryDao)
        allCategory = repository.getAll()
    }


}