package com.example.myapplication.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.myapplication.entity.Cart
import com.example.myapplication.entity.Watch

@Dao
interface CartDao {

    @Query("SELECT * FROM cartItem WHERE shoesAdded=:watch")
    fun checkIfAdded(watch: Watch): Cart

    @Insert
    fun insertCartItem(cart: Cart)

    @Update
    fun updateQuantity(cart: Cart)

    @Query("SELECT COUNT(*) FROM cartItem")
    fun getCartItemCount(): Int

    @Query("SELECT * FROM cartItem")
    fun getAllCartItems(): List<Cart>

    @Delete
    fun deleteItem(cart: Cart)

    @Query("SELECT * FROM cartItem where id=:id")
    fun getCartItem(id: Int?): Cart

}