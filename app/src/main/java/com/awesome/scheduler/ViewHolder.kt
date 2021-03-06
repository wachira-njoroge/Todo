package com.awesome.scheduler

import android.content.Context
import android.content.Intent
import android.text.method.ScrollingMovementMethod
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.cardview.view.*

//Create a ViewHolder class that takes an itemView on construction.
//These class extends the recyclerView which contains a viewHolder.
//Pass the itemView as a parameter upon ViewHolder construction to the recyclerView`s ViewHolder.
class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView), View.OnClickListener{
    //Initialize a context to work with once a view is clicked
    lateinit var ctx:Context
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
        ctx = model.context
    }
    override fun onClick(v: View?) {
        //Get adapter position of the clicked holder
        val itemPos = this.adapterPosition
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
                ContextCompat.startActivity(ctx, Intent(ctx, MainActivity::class.java), null)
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
    fun cardDetails(arrayList: ArrayList<Model>): Model {
        val pos = this.adapterPosition
        val pk = arrayList.get(pos)
        return pk
    }
}
