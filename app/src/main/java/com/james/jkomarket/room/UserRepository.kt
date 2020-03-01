package com.james.jkomarket.room

import androidx.lifecycle.LiveData
import com.james.jkomarket.account.model.User

class UserRepository(private val userDao: UserDao) {

    suspend fun setUser(user: User): Long {
        return userDao.insert(user)
    }

    suspend fun getUser(name: String): LiveData<List<User>> = userDao.getUser(name)
}