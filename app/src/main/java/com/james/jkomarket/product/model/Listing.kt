package com.james.jkomarket.product.model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.*


@Entity(tableName = "listing_table")
data class Listing(

    @PrimaryKey(autoGenerate = true)
    var id: Long,

    var title: String,

    var description: String,

    var price: Double,

    var user_name: String,

    var category: Long,

    @ColumnInfo(name = "created_at")
    var createAt: Long = Date(System.currentTimeMillis()).time,

    @ColumnInfo(name = "update_at")
    var updateAt: Long = Date(System.currentTimeMillis()).time
)