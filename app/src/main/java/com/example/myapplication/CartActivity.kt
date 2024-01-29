package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.myapplication.adapter.CartAdapter
import com.example.myapplication.dao.OrderDao
import com.example.myapplication.dao.UserDao
import com.example.myapplication.database.WatchDB
import com.example.myapplication.entity.Cart
import com.example.myapplication.entity.Order
import com.example.myapplication.entity.User
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CartActivity : ComponentActivity() , View.OnClickListener, CartItemClickListener {

    private lateinit var myDb : WatchDB
    private lateinit var userDao: UserDao
    private lateinit var loggedInUser: User

    private lateinit var checkoutBtn: Button
    private lateinit var orderDao: OrderDao

    private var totalSum: Int = 0
    private var totalQuantity: Int = 0

    var arrCarts = ArrayList<Cart>()
    var cartAdapter = CartAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.checkout_layout)

        myDb = Room.databaseBuilder(this, WatchDB::class.java, "myApp2")
            .allowMainThreadQueries().fallbackToDestructiveMigration().build()
        orderDao = myDb.getOrderDao()

        checkoutBtn = findViewById(R.id.checkoutBtn)
        checkoutBtn.setOnClickListener(this)

        getDataFromDb()

        val recyclerView = findViewById<RecyclerView>(R.id.checkoutCardElement)
        recyclerView.adapter = cartAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        setBottomSum()
        bottomNavigationHandler()

    }

    override fun onClick(p0: View?) {
        val sharedPreferences = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
        val savedUsername = sharedPreferences.getString("loggedInUser", "")
        userDao = myDb.getUserDao()

        if(!(savedUsername.isNullOrEmpty())) {
            loggedInUser = userDao.getLoggedInUser(savedUsername)
            if(loggedInUser.address.isNullOrEmpty()){
                Toast.makeText(
                    this,
                    "A cím megadása szükséges a rendeléshez!",
                    Toast.LENGTH_SHORT
                ).show()
            }else{
                orderDao.insertOrder(Order(0, arrCarts, loggedInUser))
                Toast.makeText(
                    this,
                    "Sikeres rendelés!",
                    Toast.LENGTH_SHORT
                ).show()
                cartAdapter.clear()
                var bottomSum = findViewById<ConstraintLayout>(R.id.summedOrder)
                bottomSum.visibility = View.INVISIBLE
                var intent = Intent(this@CartActivity, FirstScreenActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

    }

    private fun getDataFromDb(){
        CoroutineScope(Dispatchers.IO).launch {
            this.let {
                var cat = WatchDB.getDatabase(this@CartActivity).getCartDao().getAllCartItems()
                arrCarts = cat as ArrayList<Cart>
                cartAdapter.setData(arrCarts)
            }
        }
    }

    override fun onItemQuantityMinus(minusPrice: Int) {
        totalSum -= minusPrice
        totalQuantity--
        updateTotalSumAndQuantity()
    }

    override fun onItemQuantityPlus(plusPrice: Int) {
        totalSum += plusPrice
        totalQuantity++
        updateTotalSumAndQuantity()
    }

    private fun updateTotalSumAndQuantity() {
        runOnUiThread {
            val sumPrice = findViewById<TextView>(R.id.totalPrice)
            val sumQuantity = findViewById<TextView>(R.id.totalQuantity)

            sumPrice.text = "Összesen fizetendő: " + totalSum.toString() + "Ft"
            sumQuantity.text = "Darabszám (összes): " + totalQuantity.toString()

            if(totalSum == 0){
                var bottomSum = findViewById<ConstraintLayout>(R.id.summedOrder)
                bottomSum.visibility = View.INVISIBLE
            }

        }
    }

    override fun onItemDelete(cart: Cart){

        totalSum -= cart.quantity * cart.watchAdded.price
        totalQuantity -= cart.quantity
        updateTotalSumAndQuantity()

    }


    private fun setBottomSum(){
            CoroutineScope(Dispatchers.IO).launch {
                this.let {
                    var cat =
                        WatchDB.getDatabase(this@CartActivity).getCartDao().getAllCartItems()
                    arrCarts = cat as ArrayList<Cart>

                    val summedOrder = findViewById<ConstraintLayout>(R.id.summedOrder)
                    val sumPrice = findViewById<TextView>(R.id.totalPrice)
                    val sumQuantity = findViewById<TextView>(R.id.totalQuantity)

                    if (arrCarts.size <= 0) {
                        summedOrder.visibility = View.INVISIBLE
                    } else {
                        summedOrder.visibility = View.VISIBLE

                        var sum: Int = 0
                        var quantity: Int = 0
                        for (cart: Cart in arrCarts) {

                            if (cart.quantity == 1) {
                                sum += cart.watchAdded.price
                                quantity++
                            } else if (cart.quantity > 1) {
                                sum += cart.watchAdded.price * cart.quantity
                                quantity += cart.quantity
                            }
                        }

                        runOnUiThread {

                            sumPrice.text = "Összesen fizetendő: " + sum.toString() + "Ft"
                            sumQuantity.text = "Összesen fizetendő: " + quantity.toString()
                            totalSum = sum
                            totalQuantity = quantity

                        }
                }
            }
        }
    }

    private fun bottomNavigationHandler() {

        val fab = findViewById<FloatingActionButton>(R.id.cartIcon)
        fab.setOnClickListener {
            var intent = Intent(this@CartActivity, CartActivity::class.java)
            startActivity(intent)
        }

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->

            when (item.itemId) {
                R.id.home -> {
                    var intent = Intent(this@CartActivity, FirstScreenActivity::class.java)
                    startActivity(intent)
                    finish()
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.profile -> {
                    var intent = Intent(this@CartActivity, ProfileActivity::class.java)
                    startActivity(intent)
                    finish()
                    return@setOnNavigationItemSelectedListener true
                }

                else -> false
            }
        }
    }
}
