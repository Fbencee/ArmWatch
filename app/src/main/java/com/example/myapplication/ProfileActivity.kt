package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.room.Room
import com.example.myapplication.dao.OrderDao
import com.example.myapplication.dao.UserDao
import com.example.myapplication.database.WatchDB
import com.example.myapplication.entity.User

class ProfileActivity : ComponentActivity() , View.OnClickListener {

    private lateinit var fullNameTextView: TextView
    private lateinit var usedUsername: TextView
    private lateinit var phoneNumber: TextView
    private lateinit var address: TextView
    private lateinit var orderCount: TextView

    private lateinit var btnLogout: Button
    private lateinit var btnHomepage: Button
    private lateinit var btnAddAddress: Button

    private lateinit var textAddress: TextView

    private lateinit var myDb : WatchDB
    private lateinit var userDao: UserDao
    private lateinit var orderDao: OrderDao
    private lateinit var loggedInUser: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile_layout)

        myDb = Room.databaseBuilder(this, WatchDB::class.java, "myApp2")
            .allowMainThreadQueries().fallbackToDestructiveMigration().build()
        userDao = myDb.getUserDao()
        orderDao = myDb.getOrderDao()

        btnLogout = findViewById(R.id.btnLogout)
        btnHomepage = findViewById(R.id.goToMainPageBtn)
        btnAddAddress = findViewById(R.id.newAddressBtn)
        textAddress = findViewById(R.id.addressAddress)
        orderCount = findViewById(R.id.countOfOrders)

        val sharedPreferences = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
        val savedUsername = sharedPreferences.getString("loggedInUser", "")

        if(savedUsername.toString().isNotEmpty()) {
            loggedInUser = userDao.getLoggedInUser(savedUsername)

            fullNameTextView = findViewById(R.id.fullName)
            usedUsername = findViewById(R.id.usedUsername)
            phoneNumber = findViewById(R.id.phoneNumber)
            address = findViewById(R.id.address)

            fullNameTextView.text = loggedInUser.fullName
            usedUsername.text = loggedInUser.username
            phoneNumber.text = loggedInUser.phoneNumber
            orderCount.text = orderDao.getOrdersCount(loggedInUser).toString()

            if(loggedInUser.address.toString().isNotEmpty()){
                address.text = loggedInUser.address
            }else{
                address.text = "A megadott cím nem található!"
            }

        }else{
            val intent = Intent(this@ProfileActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        btnLogout.setOnClickListener(View.OnClickListener {
            val sharedPreferences = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()

            editor.remove("loggedInUser")
            editor.apply()

            val intent = Intent(this@ProfileActivity, HomeActivity::class.java)
            startActivity(intent)
            finish()

        })

        if(loggedInUser.address == " " || loggedInUser.address.isEmpty() ){

            textAddress.visibility = View.INVISIBLE
            address.visibility = View.INVISIBLE
            btnAddAddress.visibility = View.VISIBLE

        }

        btnAddAddress.setOnClickListener(View.OnClickListener {
            val intent = Intent(this@ProfileActivity, AddAddressActivity::class.java)
            startActivity(intent)
            finish()
        })

        btnHomepage.setOnClickListener(this)

    }

    override fun onClick(p0: View?) {
        val intent = Intent(this@ProfileActivity, FirstScreenActivity::class.java)
        startActivity(intent)
        finish()
    }
}
