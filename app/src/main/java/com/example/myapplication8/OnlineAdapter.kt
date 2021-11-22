package com.example.myapplication8

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.adapter_online.view.*
import kotlin.collections.ArrayList

class OnlineAdapter (var results: ArrayList<Phone>, val listener: OnAdapterListener):
    RecyclerView.Adapter<OnlineAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder (
        LayoutInflater.from( parent.context ).inflate( R.layout.adapter_online,
            parent, false)
    )


    override fun getItemCount() = results.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val result = results[position]
        holder.view.textView.text = result.phone_name
        Glide.with(holder.view)
            .load(result.image)
            .placeholder(R.drawable.img_placeholder)
            .error(R.drawable.img_placeholder)
            .centerCrop()
            .into(holder.view.imageView)
        holder.view.setOnClickListener { listener.onClick( result ) }
    }

    class ViewHolder(val view: View): RecyclerView.ViewHolder(view)

    fun setData(data: List<Phone>){
        this.results.clear()
        this.results.addAll(data)
        notifyDataSetChanged()
    }

    interface OnAdapterListener {
        fun onClick(result: Phone)
    }
}