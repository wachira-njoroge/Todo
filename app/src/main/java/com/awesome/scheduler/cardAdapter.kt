package com.awesome.scheduler

import android.content.Context
import android.content.Intent
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.cardview.view.*

//String tag that will identify a logged message.
private const val TAG = "cardAdapter"

class CardAdapter(private val arrayList: ArrayList<Model>, val context:Context):
    RecyclerView.Adapter<CardAdapter.Viewholder>() {

    //
    //Create a ViewHolder class that takes an itemView on construction.
    //These class extends the recyclerView which contains a viewHolder.
    //Pass the itemView as a parameter upon ViewHolder construction to the recyclerView`s ViewHolder.
    class Viewholder(itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener{
        //

        //Save the context globally
        lateinit var context:Context
        //
        //Get the items to display, into the ViewHolder.
        fun bindItems(model: Model){
            //
            //Make textview within the cardItem scrollable.
            itemView.des.movementMethod = ScrollingMovementMethod()
            //
            //Set the values of the views within the cardItem to the ones supplied by the model class
            itemView.name.text = model.name
            itemView.des.text = model.description
            itemView.due.text = model.date
            itemView.state.text = model.status
            //
            //Set the click listener for clickable views in the itemview
            itemView.complete.setOnClickListener(this)
            itemView.edit.setOnClickListener(this)
            //
            //Context is needed, to launch an activity once  the edit view is clicked.
            context = model.context

        }
        //
        //Develop action taken once the edit and mark as complete views are clicked
        override fun onClick(v: View?) {
            //
            //Implement click function for each clickable view
            when(v!!.id){
                //
                R.id.edit ->
                    //
                    //When the user clicks edit:-
                    //1. Get the position of the cardItem housing the edit view
                    //2. Get all the editable values
                    //i.e description, name
                    //3. Prepare to launch the edit activity by packing the values the user wishes to modify,
                    //in an Intent
                    //4. Launch the edit activity
                    startActivity(context, Intent(context, MainActivity::class.java), null)
                R.id.complete -> {
                    //
                    //Once the mark as complete is clicked, do the following:-
                    //1. Get the position of the cardItem containing the view
                    //2. Get the primary key of the item at the specified position
                    //3. Launch an update operation, given the item position and the primary key
                    //
                    //...jibambe
                    itemView.state.text = "Complete"
                    itemView.state.setTextColor(R.color.lime_A700)
                    v.visibility = View.INVISIBLE

                }

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cardview, parent, false)
        return Viewholder(view)
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        holder.bindItems(arrayList[position])
        //
        //Try to access views in a cardItem
        //1. Get the carditem position
        val item_pos = holder.adapterPosition
        //
        val mypk = arrayList.get(item_pos)
        //Log.d(TAG, "$mypk")

    }

    override fun getItemCount(): Int {
        return arrayList.size
    }
    //
    //Current challenge
    //Cant access the views in my recyclerview to fish out a specific view
    //1 Need to know the exact position of the carditem in which a view was clicked.
    //2. With the position, get the pk

}