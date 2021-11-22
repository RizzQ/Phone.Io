package com.example.myapplication8

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.myapplication8.model.realm.FavPhone
import com.example.myapplication8.retrofit.ApiService
import io.realm.Realm
import io.realm.exceptions.RealmException
import kotlinx.android.synthetic.main.activity_online_detail.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class   OnlineDetailActivity : AppCompatActivity() {
    private val TAG: String = "OnlineDetailActivity"

    lateinit var realm: Realm
    var slug: String = ""
    var phone_name: String = ""
    var image: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_online_detail)

        slug = intent.getStringExtra("intent_slug").toString()
        phone_name = intent.getStringExtra("intent_phone").toString()
        image = intent.getStringExtra("intent_image").toString()

        InitView()
        getDataFromApi(slug)
        action()
        checkFavorite()
    }

    private fun getDataFromApi(url: String){
        showLoading(true)
        ApiService.endpoint.datadetail(url)
            .enqueue(object : Callback<AppleDescriptionModel> {
                override fun onFailure(call: Call<AppleDescriptionModel>, t: Throwable) {
                    printLog( t.toString() )
                }
                override fun onResponse(
                    call: Call<AppleDescriptionModel>,
                    response: Response<AppleDescriptionModel>
                ) {
                    if (response.isSuccessful) {
                        showResult( response.body()!! )
                    }

                }

            })
        showLoading(false)
    }

    private fun printLog(message: String) {
        Log.d(TAG, message)
    }

    private fun showLoading(loading: Boolean) {
        when(loading) {
            true -> detailProgress.visibility = View.VISIBLE
            false -> detailProgress.visibility = View.GONE
        }
    }

    private fun showResult(phoneDesc: AppleDescriptionModel) {


        Glide.with(this)
            .load(phoneDesc.data.phone_images[0] )
            .placeholder(R.drawable.img_placeholder)
            .error(R.drawable.img_placeholder)
            .into(imageView)

        phoneName.text = phoneDesc.data.phone_name
        idOs.text = phoneDesc.data.os
        idHp.text = phoneDesc.data.phone_name
        idDimension.text = phoneDesc.data.dimension
        idStorage.text = phoneDesc.data.storage

    }

    fun InitView(){
        realm = Realm.getDefaultInstance()
    }

    fun action(){
        btnAdd.setOnClickListener{
            realm.beginTransaction()
            try {
                var dataphone = realm.createObject(FavPhone::class.java)
                dataphone.setPhonename(phone_name)
                dataphone.setImage(image)
                dataphone.setSlug(slug)
                dataphone.setOs(idOs.text.toString())
                dataphone.setDimension(idDimension.text.toString())
                dataphone.setStorage(idStorage.text.toString())

                realm.commitTransaction()

                Toast.makeText(this, "Done add to favorite!!!", Toast.LENGTH_LONG).show()
                checkFavorite()

            } catch(e:RealmException){
                Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
            }
        }


    }

    fun checkFavorite(){
        //realm.beginTransaction()
        try {

            val result: FavPhone? = realm.where(FavPhone::class.java)
                .equalTo("slug", slug).findFirst()

            //realm.commitTransaction()

            if(result != null){
                btnAdd.visibility = View.VISIBLE
                btnAddInactive.visibility = View.INVISIBLE

            }else{
                btnAdd.visibility = View.INVISIBLE
                btnAddInactive.visibility = View.VISIBLE
            }

        } catch(e:RealmException){
            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
        }
    }

}