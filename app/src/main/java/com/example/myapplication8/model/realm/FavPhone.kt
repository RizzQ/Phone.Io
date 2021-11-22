package com.example.myapplication8.model.realm

import io.realm.RealmObject
import io.realm.annotations.RealmClass

@RealmClass
open class FavPhone: RealmObject() {
//    private var id: Int = 0
//    private var title: String = ""
//    private var image: String = ""

    private var brand: String = ""
    private var detail: String = ""
    private var image: String = ""
    private var phone_name: String = ""
    private var slug: String = ""
    private var os: String = ""
    private var dimension: String = ""
    private var storage: String = ""

    fun setBrand(brand: String){
        this.brand = brand
    }

    fun getBrand(): String{
        return brand
    }

    fun setTitle(detail: String){
        this.detail = detail
    }

    fun getTitle(): String{
        return detail
    }

    fun setImage(image: String){
        this.image = image
    }

    fun getImage(): String{
        return image
    }
    fun setPhonename(phone_name: String){
        this.phone_name = phone_name
    }

    fun getPhonename(): String{
        return phone_name
    }

    fun setSlug(slug: String){
        this.slug = slug
    }

    fun getSlug(): String{
        return slug
    }
    fun setOs(os: String){
        this.os = os
    }

    fun getOs(): String{
        return os
    }
    fun setDimension(dimension: String){
        this.dimension = dimension
    }

    fun getDimension(): String{
        return dimension
    }
    fun setStorage(storage: String){
        this.storage = storage
    }

    fun getStorage(): String{
        return storage
    }


}