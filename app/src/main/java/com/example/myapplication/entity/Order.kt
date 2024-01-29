package com.example.myapplication.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "orderTable")
class Order (

    @PrimaryKey(autoGenerate = true)
    var id: Int,

    @ColumnInfo(name = "carts")
    var carts: List<Cart>,

    @ColumnInfo(name = "user")
    var user: User

) : Serializable