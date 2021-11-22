package com.example.myapplication8

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication8.model.realm.UserModel
import com.google.android.material.textfield.TextInputEditText
import io.realm.Realm
import io.realm.exceptions.RealmException
import kotlinx.android.synthetic.main.activity_online_detail.*
import kotlinx.android.synthetic.main.activity_sign_in.*
import java.math.BigInteger
import java.security.MessageDigest

class SignInActivity : AppCompatActivity() {
    lateinit var realm: Realm

    var userfullname: String = ""
    var txtemail: TextInputEditText? = null
    var txtpassword: TextInputEditText? = null
    var btnlogin: Button? = null
    var forgot: android.widget.Button? = null
    var buttonback: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        txtemail = findViewById<TextInputEditText>(R.id.emailaddress)
        txtpassword = findViewById<TextInputEditText>(R.id.password)
        btnlogin = findViewById<Button>(R.id.loginbutton)
        forgot = findViewById<Button>(R.id.forgot_pass)
        buttonback = findViewById<ImageView>(R.id.back_button)

        InitView()

        btnlogin!!.setOnClickListener {

            val email = txtemail!!.text.toString()
            val pass: String = txtpassword!!.getText().toString()

            if (CheckSignIn()
            ) {
                Toast.makeText(this@SignInActivity, "Success", Toast.LENGTH_SHORT).show()
                val move = Intent(this@SignInActivity, OnlineActivity::class.java)
                    .putExtra("intent_fullname", userfullname)
                    .putExtra("intent_email", email)
                startActivity(move)
            } else {
                Toast.makeText(this@SignInActivity, "Failed", Toast.LENGTH_SHORT).show()
            }
        }

        forgot?.setOnClickListener(View.OnClickListener {
            Toast.makeText(
                this@SignInActivity, "Email = phone@gmail.com" +
                        "Password = phone", Toast.LENGTH_LONG
            ).show()
        })

        buttonback!!.setOnClickListener { view ->
            val intent = Intent(view.context, Opening::class.java)
            startActivity(intent)
        }
    }

    fun InitView(){
        realm = Realm.getDefaultInstance()
    }

    fun CheckSignIn(): Boolean {
        //realm.beginTransaction()
        try {

            var strEmail: String = emailaddress.text.toString()
            var strPassword: String = md5(password.text.toString())
            val result: UserModel? = realm.where(UserModel::class.java)
                .equalTo("userEmailAddress", strEmail)
                .equalTo("userPassword", strPassword)
                .findFirst()

            //realm.commitTransaction()
            if (result != null) {
                userfullname = result.getuserFullname()

            }
            return result != null

        } catch(e: RealmException){
            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
            return false
        }
    }

    fun md5(input:String): String {
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0')
    }

}
