package com.example.myapplication.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "cartItem")
class Cart (

    @PrimaryKey(autoGenerate = true)
    var id: Int,

    @ColumnInfo(name = "shoesAdded")
    var watchAdded: Watch,

    @ColumnInfo(name = "quantity")
    var quantity: Int,

    ) : Serializable