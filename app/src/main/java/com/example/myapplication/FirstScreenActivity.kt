package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.adapter.WatchAdapter
import com.example.myapplication.entity.Watch
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class FirstScreenActivity : ComponentActivity() , View.OnClickListener {

    var arrShoes = ArrayList<Watch>()
    var watchAdapter = WatchAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_layout)

        arrShoes.add(Watch(1, "Casio", 38000,"Vintage óra", "https://www.bondie.hu/sub/bondie.sk/shop/product/hodinky-casio-a1000mga-5ef-46335.png"))
        arrShoes.add(Watch(2, "Police", 40000,"Elegáns óra", "https://www.karorak-pro.hu/204360-large_default/police-smart-style-15968jsb-39-ferfi-karora.jpg"))
        watchAdapter.setData(arrShoes)

        val recyclerView = findViewById<RecyclerView>(R.id.shoesCardElement)
        recyclerView.adapter = watchAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        bottomNavigationHandler()

    }

    override fun onClick(p0: View?) {
        var intent = Intent(this@FirstScreenActivity, ProfileActivity::class.java)
        startActivity(intent)
    }

    private fun bottomNavigationHandler() {
        val cart = findViewById<FloatingActionButton>(R.id.cartIcon)
        cart.setOnClickListener {
            var intent = Intent(this@FirstScreenActivity, CartActivity::class.java)
            startActivity(intent)
        }

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->

            when (item.itemId) {
                R.id.profile -> {
                    var intent = Intent(this@FirstScreenActivity, ProfileActivity::class.java)
                    startActivity(intent)
                    finish()
                    return@setOnNavigationItemSelectedListener true
                }

                else -> false
            }
        }
    }

}
