package com.awesome.scheduler

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

//String tag that will identify a logged message.
private const val TAG = "cardAdapter"

class CardAdapter(val arrayList: ArrayList<Model>, val context:Context):
    RecyclerView.Adapter<ViewHolder>(){

    val listToSend = arrayList
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder{
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cardview, parent, false)
        return ViewHolder(view, context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(arrayList[position])
        //
        //Try to access views in a cardItem
        //1. Get the carditem position
        val item_pos = holder.adapterPosition
        //
        val mypk = arrayList.get(item_pos)
        Log.d(TAG, "onBindViewHolder: ${mypk.primary_key}")

    }

    override fun getItemCount(): Int {
        return arrayList.size
    }
    //Current challenge
    //Cant access the views in my recyclerview to fish out a specific view
    //1 Need to know the exact position of the carditem in which a view was clicked.
    //2. With the position, get the pk
}