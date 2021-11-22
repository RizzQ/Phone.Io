package com.example.myapplication8

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication8.model.realm.FavPhone
import io.realm.Realm
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_favorite.*

class FavoriteActivity : AppCompatActivity() {

    private lateinit var favAdapter: FavoriteAdapter

    private var realm: Realm? = null
    private var recyclerView: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)
        setupButton(R.id.btnOnline, OnlineActivity::class.java)


        realm = Realm.getDefaultInstance()
        recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        showLoading(true)
        setUpRecyclerView()
        showLoading(false)

    }

    private fun setUpRecyclerView() {

        val obj = realm!!.where<FavPhone>().findAll()
        val list = ArrayList<FavPhone>()
        list.addAll(realm!!.copyFromRealm(obj))

        favAdapter = FavoriteAdapter(list, object : FavoriteAdapter.OnAdapterListener {
            override fun onClick(result: FavPhone) {
                startActivity(
                    Intent(this@FavoriteActivity, FavoriteDetailActivity::class.java)
                        .putExtra("intent_slug", result.getSlug())
                        .putExtra("intent_phonename", result.getPhonename())
                        .putExtra("intent_image", result.getImage())
                )
            }
        })
        recyclerView?.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = favAdapter
        }
    }

    /*
        private fun setUpRecyclerViewX() {
            val obj = realm!!.where<FavPhone>().findAll()
            val list = ArrayList<FavPhone>()

            list.addAll(realm!!.copyFromRealm(obj))

            val adapt = FavoriteAdapter(list, )

            recyclerView!!.layoutManager = LinearLayoutManager(this)
            recyclerView!!.adapter = adapt
        }
    */
    private fun startActivity(activityClass: Class<out Activity?>?) {
        startActivity(Intent(this, activityClass))
    }

    private fun setupButton(id: Int, activityClass: Class<out Activity?>) {
        findViewById<View>(id).setOnClickListener { startActivity(activityClass) }
    }

    private fun showLoading(loading: Boolean) {
        when (loading) {
            true -> progressBar.visibility = View.VISIBLE
            false -> progressBar.visibility = View.GONE
        }
    }


    override fun onRestart() {
        super.onRestart()
        showLoading(true)
        setUpRecyclerView()
        showLoading(false)
    }

}