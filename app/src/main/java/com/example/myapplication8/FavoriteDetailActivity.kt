package com.example.myapplication8

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.myapplication8.model.realm.FavPhone
import io.realm.Realm
import io.realm.exceptions.RealmException
import kotlinx.android.synthetic.main.activity_favorite_detail.*

import io.realm.RealmResults
import kotlinx.android.synthetic.main.activity_favorite_detail.idDimension
import kotlinx.android.synthetic.main.activity_favorite_detail.idHp
import kotlinx.android.synthetic.main.activity_favorite_detail.idOs
import kotlinx.android.synthetic.main.activity_favorite_detail.idStorage
import kotlinx.android.synthetic.main.activity_favorite_detail.imageView
import kotlinx.android.synthetic.main.activity_favorite_detail.phoneName
import kotlinx.android.synthetic.main.activity_online_detail.*


class FavoriteDetailActivity : AppCompatActivity() {

    lateinit var realm: Realm
    var slug: String = ""
    var image: String = ""
    var id: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite_detail)

        slug = intent.getStringExtra("intent_slug").toString()
        val intentPhonename = intent.getStringExtra("intent_phonename")
        val intentImage = intent.getStringExtra("intent_image")


        Glide.with(this)
            .load(intentImage )
            .placeholder(R.drawable.img_placeholder)
            .error(R.drawable.img_placeholder)
            .into(imageView)


        InitView()
        loadPhone(slug)
        action()

    }

    fun InitView(){
        realm = Realm.getDefaultInstance()
    }

    fun action(){
        btnDelete.setOnClickListener{
            realm.beginTransaction()
            try {

                //val obj = realm!!.where<FavPhone>().equalTo("id", id).findAll()

                val result: RealmResults<FavPhone> =
                    realm.where(FavPhone::class.java)
                        .equalTo("slug", slug).findAll()
                result.deleteAllFromRealm()

                realm.commitTransaction()

                Toast.makeText(this, "Done delete in favorite!!!", Toast.LENGTH_LONG).show()
                finish()

            } catch(e:RealmException){
                Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
            }
        }

    }

    fun loadPhone(slug:String){
        //realm.beginTransaction()
        try {

            val result: FavPhone? = realm.where(FavPhone::class.java)
                .equalTo("slug", slug).findFirst()

            //realm.commitTransaction()

            if(result != null){
                phoneName.text = result.getPhonename()
                idOs.text = result.getOs()
                idHp.text = result.getPhonename()
                idDimension.text = result.getDimension()
                idStorage.text = result.getStorage()

            }else{

            }

        } catch(e:RealmException){
            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
        }
    }

}