package com.james.jkomarket.product.model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.*


@Entity(tableName = "category_table")
data class Category(

    @PrimaryKey(autoGenerate = true)
    var id: Long,

    var name: String,

    @ColumnInfo(name = "created_at")
    var createAt: Long = Date(System.currentTimeMillis()).time,

    @ColumnInfo(name = "update_at")
    var updateAt: Long = Date(System.currentTimeMillis()).time
)