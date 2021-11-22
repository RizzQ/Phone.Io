package com.example.myapplication8.model.realm

import io.realm.RealmObject
import io.realm.annotations.RealmClass

@RealmClass
open class UserModel: RealmObject() {

//    private var id: Int = 0
//    private var title: String = ""
//    private var image: String = ""

    private var userFullname: String = ""
    private var userPassword: String = ""
    private var userEmailAddress: String = ""

    fun setuserFullname(username: String){
        this.userFullname = username
    }

    fun getuserFullname(): String{
        return userFullname
    }

    fun setPassword(password: String){
        this.userPassword = password
    }

    fun getPassword(): String{
        return userPassword
    }

    fun setEmail(useremail: String){
        this.userEmailAddress = useremail
    }

    fun getEmail(): String{
        return userEmailAddress
    }




}