package com.james.jkomarket.room

import com.james.jkomarket.account.User

class UserRepository(private val userDao: UserDao) {

    suspend fun setUser(user: User): Long {
        return userDao.setUser(user)
    }

    suspend fun getUser(name: String) = userDao.getUser(name)
}