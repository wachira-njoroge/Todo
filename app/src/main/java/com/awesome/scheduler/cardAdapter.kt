package com.awesome.scheduler

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.cardview.view.*

class CardAdapter(private val arrayList: ArrayList<Model>, val context:Context):
    RecyclerView.Adapter<CardAdapter.Viewholder>() {
    //
    //Create a ViewHolder class that takes an itemView on construction.
    //ViewHolder class extends the recyclerView which contains a viewHolder.
    //Pass the itemView parameter provided upon ViewHolder construction to to the recyclerView`s ViewHolder.
    class Viewholder(itemView: View): RecyclerView.ViewHolder(itemView){
        //
        //Get the items to display, into the ViewHolder.
        fun bindItems(model: Model){
            itemView.name.text = model.name
            itemView.des.text = model.description
            itemView.due.text = model.date
            itemView.state.text = model.status
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cardview, parent, false)
        return Viewholder(view)
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        holder.bindItems(arrayList[position])
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

}