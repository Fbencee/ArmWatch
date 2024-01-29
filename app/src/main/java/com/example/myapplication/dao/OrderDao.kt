package com.example.myapplication.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.myapplication.entity.Order
import com.example.myapplication.entity.User

@Dao
interface OrderDao {

   @Insert
   fun insertOrder(order: Order)

   @Query("SELECT COUNT(*) FROM orderTable where user=:user")
   fun getOrdersCount(user: User): Int

}