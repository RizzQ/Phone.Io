package com.example.myapplication8

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myapplication8.retrofit.ApiService
import kotlinx.android.synthetic.main.activity_online.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.text.Editable

import android.text.TextWatcher
import kotlinx.android.synthetic.main.activity_online_detail.*
import android.widget.LinearLayout
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.auth.api.Auth


class OnlineActivity : AppCompatActivity(), GoogleApiClient.OnConnectionFailedListener {
    private val TAG: String = "OnlineActivity"
    private lateinit var onlineAdapter: OnlineAdapter
    private lateinit var phoneList: AppleModel
    private var profile: LinearLayout? = null
    private var googleApiClient: GoogleApiClient? = null
    private var gso: GoogleSignInOptions? = null

    var strEmail: String = ""
    var strFullname: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_online)
        setupButton(R.id.btnFavorite, FavoriteActivity::class.java)

        userFullname.text = "Hi, "+intent.getStringExtra("intent_fullname").toString()

        strFullname = intent.getStringExtra("intent_fullname").toString()
        strEmail = intent.getStringExtra("intent_email").toString()

        //profile = editProfile;

        setupRecyclerView()
        getDataFromApi()
        getEditprofile()

        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        googleApiClient = GoogleApiClient.Builder(this)
            .enableAutoManage(this, this)
            .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
            .build()
        //adding a TextChangedListener
        //to call a method whenever there is some change on the EditText
        //adding a TextChangedListener
        //to call a method whenever there is some change on the EditText
        editTextSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun afterTextChanged(editable: Editable) {
                //after the change calling the method and passing the search input
                filter(editable.toString())
            }
        })
        userFullname.setOnClickListener { view ->
            val intent = Intent(view.context, EditProfile3::class.java)
                .putExtra("intent_fullname", strFullname)
                .putExtra("intent_email", strEmail)
            startActivity(intent)
        }
        //userFullname.setOnClickListener()
    }

    private fun filter(searchText: String) {
        //new array list that will hold the filtered data
        var filterdPhones: List<Phone>

        filterdPhones = phoneList.data.phones.filter { x -> x.phone_name.toLowerCase().contains(searchText.toLowerCase()) }

        //calling a method of the adapter class and passing the filtered list
        onlineAdapter.setData( filterdPhones )
    }


    private fun setupRecyclerView(){
        onlineAdapter = OnlineAdapter(arrayListOf(), object : OnlineAdapter.OnAdapterListener {
            override fun onClick(result: Phone) {
                startActivity(
                    Intent(this@OnlineActivity, OnlineDetailActivity::class.java)
                        .putExtra("intent_slug", result.slug)
                        .putExtra("intent_phone", result.phone_name)
                        .putExtra("intent_image", result.image)

                )
            }
        })
        recyclerView.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = onlineAdapter
        }
    }

    private fun getDataFromApi(){
        showLoading(true)
        ApiService.endpoint.data()
            .enqueue(object : Callback<AppleModel> {
                override fun onFailure(call: Call<AppleModel>, t: Throwable) {
                    printLog( t.toString() )
                    showLoading(false)
                }
                override fun onResponse(
                    call: Call<AppleModel>,
                    response: Response<AppleModel>
                ) {
                    showLoading(false)
                    if (response.isSuccessful) {
                        phoneList = response.body()!!
                        showResult( phoneList )
                    }
                }
            })
    }

    private fun printLog(message: String) {
        Log.d(TAG, message)
    }

    private fun showLoading(loading: Boolean) {
        when(loading) {
            true -> progressBar.visibility = View.VISIBLE
            false -> progressBar.visibility = View.GONE
        }
    }

    private fun startActivity(activityClass: Class<out Activity?>?) {
        startActivity(Intent(this, activityClass))
    }
    private fun setupButton(id: Int, activityClass: Class<out Activity?>) {
        findViewById<View>(id).setOnClickListener { startActivity(activityClass) }
    }

    private fun showResult(resultList: AppleModel) {
//        for (datarow in resultList.data.phones) printLog( "phone name: ${datarow.phone_name}" )
        onlineAdapter.setData( resultList.data.phones )
    }
    private fun getEditprofile() {
        profile?.setOnClickListener(View.OnClickListener { view ->
            val intent = Intent(view.context, EditProfile3::class.java)
            startActivity(intent)
        })
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
        TODO("Not yet implemented")
    }




    private fun gotoOpening() {
        val intent = Intent(this, Opening::class.java)
        startActivity(intent)
    }
}
