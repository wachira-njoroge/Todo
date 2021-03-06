package com.awesome.scheduler

import android.content.Context
import android.content.Intent
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.cardview.view.*
import kotlin.properties.Delegates

//String tag that will identify a logged message.
private const val TAG = "cardAdapter"

class CardAdapter(val arrayList: ArrayList<Model>, val context:Context):
    RecyclerView.Adapter<ViewHolder>(), View.OnClickListener {
    val listToSend = arrayList
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder{
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cardview, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(arrayList[position])
        //
        //Try to access views in a cardItem
        //1. Get the carditem position
        val item_pos = holder.adapterPosition
        //
       //val mypk = arrayList.get(item_pos)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onClick(v: View?) {

        //this.getPos()
        Toast.makeText(context, "position", Toast.LENGTH_SHORT).show()
    }

    fun sendList(): ArrayList<Model> {
        return this.arrayList
    }
    //
    //Current challenge
    //Cant access the views in my recyclerview to fish out a specific view
    //1 Need to know the exact position of the carditem in which a view was clicked.
    //2. With the position, get the pk
}