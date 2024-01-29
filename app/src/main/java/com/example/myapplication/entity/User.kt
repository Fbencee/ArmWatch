package com.example.myapplication.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "registeredUsers")
class User (

    @PrimaryKey(autoGenerate = true)
    var id: Int,

    @ColumnInfo(name = "fullName")
    var fullName: String,

    @ColumnInfo(name = "role")
    var role: String,

    @ColumnInfo(name = "username")
    var username: String,

    @ColumnInfo(name = "address")
    var address: String,

    @ColumnInfo(name = "password")
    var password: String,

    @ColumnInfo(name = "phoneNumber")
    var phoneNumber: String

) : Serializable