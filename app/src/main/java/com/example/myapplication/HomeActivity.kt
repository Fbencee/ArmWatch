package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.ComponentActivity

class HomeActivity : ComponentActivity() , View.OnClickListener {

    private lateinit var btnStart: Button
    private lateinit var btnLogin: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.homescreen_layout)
        val sharedPreferences = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        editor.remove("loggedInUser")
        editor.apply()

        btnStart = findViewById(R.id.btnStart)
        btnStart.setOnClickListener(this)

        btnLogin = findViewById(R.id.btnLogin)
        btnLogin.setOnClickListener {
            var intent = Intent(this@HomeActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    override fun onClick(p0: View?) {
        var intent = Intent(this@HomeActivity, FirstScreenActivity::class.java)
        startActivity(intent)
    }
}
