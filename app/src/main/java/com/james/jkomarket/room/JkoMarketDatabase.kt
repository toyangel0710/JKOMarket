package com.james.jkomarket.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.james.jkomarket.account.model.User
import com.james.jkomarket.product.model.Category
import com.james.jkomarket.product.model.Listing
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = arrayOf(User::class, Listing::class, Category::class), version = 1, exportSchema = false)
abstract class JkoMarketDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun listingDao(): ListingDao
    abstract fun categoryDao(): CategoryDao

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
                    .addCallback(DatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }

        private class DatabaseCallback(
            private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {
            /**
             * Override the onCreate method to populate the database.
             * It will execute when first time created.
             */
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { database ->
                    scope.launch(Dispatchers.IO) {
                        populateDatabase(database)
                    }
                }
            }
        }

        /**
         * Populate the database in a new coroutine.
         */
        suspend fun populateDatabase(database: JkoMarketDatabase) {
            // Start the app with a clean database every time.
            // Not needed if you only populate on creation.

            val userDao = database.userDao()
            val listingDao = database.listingDao()
            val categoryDao = database.categoryDao()

            // Create default User1, User2
            userDao.insert(User(0, "user1"))
            userDao.insert(User(0, "user2"))

            // Create default category
            val category_1 = categoryDao.insert(Category(0, "Electronic", 0))
            val category_2 = categoryDao.insert(Category(0, "Clothes", 0))
            val category_3 = categoryDao.insert(Category(0, "Sport", 0))
            val category_4 = categoryDao.insert(Category(0, "Food", 0))
            val category_5 = categoryDao.insert(Category(0, "Backpack", 0))

            // Create default listings of User1 and User2
            listingDao.insert(Listing(0, "MacBook Pro 15", "Retina, 15-inch, Mid 2015", 60000.0, "user1", category_1))
            listingDao.insert(Listing(0, "MacBook Air 13", "Retina, 13-inch, Mid 2016", 40000.0, "user1", category_1))
            listingDao.insert(Listing(0, "White T-shirt", "For man", 900.0, "user1", category_2))
            listingDao.insert(Listing(0, "Black Shoes", "For man", 4000.0, "user1", category_3))
            listingDao.insert(Listing(0, "iPhone 11 pro", "iPhone 11", 38000.0, "user2", category_1))
            listingDao.insert(Listing(0, "Apple HomePod", "HomePod", 9900.0, "user2", category_1))
            listingDao.insert(Listing(0, "Apple TV", "4K", 4990.0, "user2", category_1))
            listingDao.insert(Listing(0, "Adidas Sport Shoes", "For woman", 24900.0, "user2", category_3))
            listingDao.insert(Listing(0, "Strawberry Cake", "Big Size", 899.0, "user2", category_4))
            listingDao.insert(Listing(0, "Pizza", "Confluence Pizza", 699.0, "user2", category_4))
            listingDao.insert(Listing(0, "BBQ Ranch Burger", "McDonal's", 135.0, "user2", category_4))
            listingDao.insert(Listing(0, "Ham Sandwich", "Subway", 99.0, "user2", category_4))
            listingDao.insert(Listing(0, "Auralux Backpack", "Nike, Black", 1999.0, "user2", category_5))
            listingDao.insert(Listing(0, "Girls Laptop Backpack", "Pink", 2500.0, "user2", category_5))
            listingDao.insert(Listing(0, "Nike Performance Backpack", "Nike, Black", 3000.0, "user2", category_5))
        }
    }
}