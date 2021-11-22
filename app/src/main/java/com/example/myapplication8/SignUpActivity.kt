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
import kotlinx.android.synthetic.main.activity_sign_in.*
import kotlinx.android.synthetic.main.activity_sign_up.*
import java.math.BigInteger
import java.security.MessageDigest


class SignUpActivity : AppCompatActivity() {

    lateinit var realm: Realm

    private var back: ImageView? = null
    private var signup: Button? = null
    private var emailsignup: TextInputEditText? = null
    private var usernamesignup: TextInputEditText? = null
    private var passwordsignup: TextInputEditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_sign_up)
        signup = findViewById(R.id.signupbutton)
        emailsignup = findViewById(R.id.emailup)
        passwordsignup = findViewById(R.id.passwordup)

        back_button_up.setOnClickListener(View.OnClickListener { view ->
            val intent = Intent(view.context, Opening::class.java)
            startActivity(intent)
        })

        InitView()

        action()

    }

    fun InitView() {
        realm = Realm.getDefaultInstance()
    }

    fun action() {

        signupbutton.setOnClickListener {

            if (emailsignup?.getText().toString().length == 0) {
                Toast.makeText(this@SignUpActivity, "Email required!", Toast.LENGTH_SHORT).show()
            } else if (usernamesignup?.getText().toString().length == 0) {
                Toast.makeText(this@SignUpActivity, "Password required!", Toast.LENGTH_SHORT).show()
            } else if (passwordsignup?.getText().toString().length == 0) {
                Toast.makeText(this@SignUpActivity, "Password required!", Toast.LENGTH_SHORT).show()

            } else {

                var apakahEmailSudahAdaDiDatabase: Boolean = isEmailAddressExist()

                if(apakahEmailSudahAdaDiDatabase == false) {
                    realm.beginTransaction()
                    try {
                        var userdata = realm.createObject(UserModel::class.java)
                        var strPassword: String = md5(passwordup.text.toString())
                        userdata.setEmail(emailup.text.toString())
                        userdata.setuserFullname(username.text.toString())
                        userdata.setPassword(strPassword)

                        realm.commitTransaction()

                        Toast.makeText(this, "Registration successfull!!!", Toast.LENGTH_LONG)
                            .show()

                    } catch (e: RealmException) {
                        Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
                    }
                    val intent = Intent(this, SignInActivity::class.java)
                    startActivity(intent)
                }
                else
                {
                    Toast.makeText(this, "Email address already exists!!!", Toast.LENGTH_LONG)
                        .show()

                }
            }


        }


    }

    fun isEmailAddressExist(): Boolean {
        //realm.beginTransaction()
        try {

            var strEmail: String = emailup.text.toString()
            val result: UserModel? = realm.where(UserModel::class.java)
                .equalTo("userEmailAddress", strEmail)
                .findFirst()

            if(result != null){
                return true
            }
            else {
                return false
            }

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