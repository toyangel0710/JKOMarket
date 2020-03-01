package com.james.jkomarket.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.james.jkomarket.account.model.User
import kotlinx.coroutines.CoroutineScope

@Database(entities = arrayOf(User::class), version = 2, exportSchema = false)
abstract class JkoMarketDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: JkoMarketDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): JkoMarketDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    JkoMarketDatabase::class.java,
                    "JkoMarketPlace_database"
                )
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}