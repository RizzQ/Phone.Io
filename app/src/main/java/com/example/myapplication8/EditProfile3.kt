package com.example.myapplication8

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication8.model.realm.UserModel
import io.realm.Realm
import io.realm.exceptions.RealmException
import kotlinx.android.synthetic.main.activity_edit_profile.*
import kotlinx.android.synthetic.main.activity_edit_profile.back_button
import java.math.BigInteger
import java.security.MessageDigest

class EditProfile3 : AppCompatActivity() {
    lateinit var realm: Realm
    var userfullname: String = ""
    private var recyclerView: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        fullname_field.text = intent.getStringExtra("intent_fullname").toString()
        user_fullname.setText(intent.getStringExtra("intent_fullname").toString())
        useremail.setText(intent.getStringExtra("intent_email").toString())

        back_button.setOnClickListener(View.OnClickListener { view ->
            val intent = Intent(view.context, OnlineActivity::class.java)
            startActivity(intent)
        })
        InitView()
        action()
    }

    fun InitView(){
        realm = Realm.getDefaultInstance()
    }

    fun action() {

        btnSave.setOnClickListener {
            realm.beginTransaction()
            try {

                var strEmail: String = useremail.text.toString()
                var strFullname: String = user_fullname.text.toString()
                val result: UserModel? = realm.where(UserModel::class.java)
                    .equalTo("userEmailAddress", strEmail)
                    .findFirst()

                if (result != null) {
                    userfullname = result.getuserFullname()
                    result.setuserFullname(user_fullname.text.toString())
                    if (user_password.text.toString().length != 0)
                    {
                        result.setPassword(md5(user_password.text.toString()))
                    }

                    realm.commitTransaction()

                    Toast.makeText(this, "Edit Profile successfull!!!", Toast.LENGTH_LONG).show()
                }
                else {
                    Toast.makeText(this, "Email Address not Found!!!", Toast.LENGTH_LONG).show()
                }

            } catch (e: RealmException) {
                Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()

            }
        }
    }

    fun md5(input:String): String {
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0')
    }

}