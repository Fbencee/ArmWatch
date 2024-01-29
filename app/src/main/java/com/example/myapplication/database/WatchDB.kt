package com.example.myapplication.database

import com.example.myapplication.converter.WatchTypeConverter
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.myapplication.converter.CartItemTypeConverter
import com.example.myapplication.converter.UserTypeConverter
import com.example.myapplication.dao.CartDao
import com.example.myapplication.dao.WatchDao
import com.example.myapplication.dao.OrderDao
import com.example.myapplication.dao.UserDao
import com.example.myapplication.entity.Cart
import com.example.myapplication.entity.Watch
import com.example.myapplication.entity.Order
import com.example.myapplication.entity.User

@Database(entities = [Watch::class, User::class, Cart::class, Order::class], version = 7, exportSchema = false)
@TypeConverters(WatchTypeConverter::class, CartItemTypeConverter::class, UserTypeConverter::class)
abstract class WatchDB: RoomDatabase() {

    companion object{

        private var watchDB:WatchDB? = null

        @Synchronized
        fun getDatabase(context: Context): WatchDB{
            if (watchDB == null){
                watchDB = Room.databaseBuilder(
                    context,
                    WatchDB::class.java,
                    "watch.db"
                ).fallbackToDestructiveMigration().build()
            }
            return watchDB!!
        }
    }

    abstract fun getWatchesDao():WatchDao
    abstract fun getUserDao():UserDao
    abstract fun getCartDao(): CartDao
    abstract fun getOrderDao(): OrderDao

}