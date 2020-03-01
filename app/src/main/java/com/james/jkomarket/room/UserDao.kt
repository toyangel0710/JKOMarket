package com.james.jkomarket.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.james.jkomarket.account.model.User

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(user: User): Long

    @Query("SELECT * from user_table WHERE name = :userName")
    fun getUser(userName: String): LiveData<List<User>>

    @Query("DELETE FROM user_table")
    suspend fun deleteAll()
}