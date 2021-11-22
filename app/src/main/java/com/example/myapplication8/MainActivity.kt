package com.example.myapplication8

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupButton(R.id.button_Favorite, FavoriteActivity::class.java)
        setupButton(R.id.button_Online, OnlineActivity::class.java)
    }

    private fun startActivity(activityClass: Class<out Activity?>?) {
        startActivity(Intent(this, activityClass))
    }

    private fun setupButton(id: Int, activityClass: Class<out Activity?>) {
        findViewById<View>(id).setOnClickListener { startActivity(activityClass) }
    }
}