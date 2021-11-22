package com.example.myapplication8

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication8.model.realm.FavPhone
import kotlinx.android.synthetic.main.adapter_favorite.view.*
import kotlin.collections.ArrayList

class FavoriteAdapter (var results: ArrayList<FavPhone>, val listener: FavoriteAdapter.OnAdapterListener):
    RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder (
        LayoutInflater.from( parent.context ).inflate( R.layout.adapter_favorite,
            parent, false)
    )


    override fun getItemCount() = results.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val result = results[position]
        holder.view.textView.text = result.getPhonename()
        Glide.with(holder.view)
            .load(result.getImage())
            .placeholder(R.drawable.img_placeholder)
            .error(R.drawable.img_placeholder)
            .centerCrop()
            .into(holder.view.imageView)
        holder.view.setOnClickListener { listener.onClick( result ) }
    }

    class ViewHolder(val view: View): RecyclerView.ViewHolder(view)


    fun setData(data: List<FavPhone>){
        this.results.clear()
        this.results.addAll(data)
        notifyDataSetChanged()
    }

    interface OnAdapterListener {
        fun onClick(result: FavPhone)
    }

}