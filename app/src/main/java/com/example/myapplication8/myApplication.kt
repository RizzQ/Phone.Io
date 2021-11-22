package com.example.myapplication8

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration

class myApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        // Initialize Realm
        Realm.init(this)

        // Get a Realm instance for this thread
        val realm = Realm.getDefaultInstance()

        val config = RealmConfiguration.Builder().build()

    }

}