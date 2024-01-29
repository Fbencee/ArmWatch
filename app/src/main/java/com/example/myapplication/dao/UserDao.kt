package com.example.myapplication.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.myapplication.entity.User

@Dao
interface UserDao {

    @Query("SELECT * from registeredUsers")
    fun getAllUsers(): List<User>

    @Query("SELECT * from registeredUsers where username=:username")
    fun getLoggedInUser(username: String?): User

    @Insert
    fun insertUser(user: User)

    @Query("SELECT EXISTS (SELECT * from registeredUsers where username=:username)")
    fun is_taken(username: String): Boolean

    @Query("SELECT EXISTS (SELECT * from registeredUsers where username=:username AND password=:password)")
    fun login(username: String, password: String): Boolean

    @Update
    fun updateUser(user: User)


}