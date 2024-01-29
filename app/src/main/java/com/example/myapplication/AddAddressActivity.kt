package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.room.Room
import com.example.myapplication.dao.UserDao
import com.example.myapplication.database.WatchDB
import com.example.myapplication.entity.User

class AddAddressActivity : ComponentActivity() , View.OnClickListener {

    private lateinit var addBtn: Button
    private lateinit var addressName: EditText
    private lateinit var myDb : WatchDB
    private lateinit var userDao: UserDao
    private lateinit var loggedInUser: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_address_layout)

        myDb = Room.databaseBuilder(this, WatchDB::class.java, "myApp2")
            .allowMainThreadQueries().fallbackToDestructiveMigration().build()
        userDao = myDb.getUserDao()

        addBtn = findViewById(R.id.addNewAddressBtn)
        addressName = findViewById(R.id.newAddress)

        addBtn.setOnClickListener(this)

    }

    override fun onClick(p0: View?) {

        val sharedPreferences = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
        val savedUsername = sharedPreferences.getString("loggedInUser", "")
        loggedInUser = userDao.getLoggedInUser(savedUsername)

        val updatedUser = User(loggedInUser.id, loggedInUser.fullName, loggedInUser.role, loggedInUser.username,
            addressName.text.toString(), loggedInUser.password, loggedInUser.phoneNumber)

        myDb.runInTransaction {
            userDao.updateUser(updatedUser)

            Toast.makeText(
                this,
                "Cím hozzáadása sikeres!",
                Toast.LENGTH_SHORT
            ).show()

            val intent = Intent(this@AddAddressActivity, ProfileActivity::class.java)
            startActivity(intent)
        }
    }
}
