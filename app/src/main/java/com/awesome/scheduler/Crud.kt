package com.awesome.scheduler

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.crud.*
import org.json.JSONArray
import org.json.JSONObject

private const val log = "Crud"
//
//Create an empty arraylist that will be filled with items to show on the cardview.
//In kotlin "var" is global while "val" is similar to const in js.
var arraylist = ArrayList<Model>()
//
class Crud:AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.crud)
        //
        //Ensure there are no records visible when this activity launches...
        //...to avoid overlapping with incoming records to be displayed.
        arraylist.clear()
        //
        //Refresh all views to ensure they aren`t displaying any previous records.
        recyclerView.adapter?.notifyDataSetChanged()
        //
        //Get items to display, from the server.
        mydata()
        //
        //Set the click listener for the add button in this activity, that launches an activity
        //to save a new record
        add.setOnClickListener(this)
    }
    //
    //Once the add button is clicked, launch the mainactivity for the user
    //to schedule something
    override fun onClick(v: View?) {
        //
        //Switch to the referenced activity where the user can save a new record.
        startActivity(Intent(this, MainActivity::class.java))
           
    }
    //
    //This function performs a server fetch operation for all scheduled tasks to display
    private fun mydata(){
        //
        //Create a request channel to the server.
        val requestque = Volley.newRequestQueue(this)
        //
        //Define the method to run on the server and post data to send
        val requested = JSONObject()
        requested.put("method","review")
        //
        //My php method expects some method arguments..I`ll pass a simple a simple "fetch all"
        //Consider making this value optional, on the serverside end.
        requested.put("data","fetch all")
        //
        //Initialize the request
        val myrequest = JsonObjectRequest(
            Request.Method.POST,
            Constants.url,
            requested,
            {
            response->
                //
                //Get the response json result
                val input = response.getJSONArray("results")
                display(input)
            },
            {
            //
            //OnFailure, alert and display the error message.
            error: VolleyError? ->
                //
                //Display the error message.
                Toast.makeText(this, "Check your Network Connection status", Toast.LENGTH_SHORT).show()
                //
                //Log the error message.
                Log.d(log, "${error.toString()}")
        })
        requestque.add(myrequest)
    }
    //
    //This function takes in the server response as a parameter and displays
    //each response item as a cardView item.
    private fun display(output:JSONArray){
        //
        //Loop through the server response data.
        //Output the values of all the keys, from the carditem array.
        for( obj in 0 until output.length()){
            //
            val item = output.getJSONObject(obj)
            val pk = item.getString("task")
            val nam = item.getString("name")
            val des = item.getString("description")
            val date = item.getString("date")
            var status = item.getString("status")
            //
            //Handle a null value from the server response
            //If the status value is null...
            if (status == "null"){
                //
                //...mark it as pending, on the cardItem
                status = "pending"
            }
            //
            //Convert the pk string to an integer..
            //The pk is a string since it came in as a json
            val primary = pk.toInt()
            //
            //Populate the recyclerview with the server response, as cardItems.
            arraylist.add(Model(primary, nam, des,date,status))
        }
        //Initialize the Activity adapter that takes the arraylist of items to display
        //and returns a recyclerview.
        val adp = CardAdapter(arraylist, this)
        //
        //Assign a layout to my recyclerview.
        //Without a layout assigned, the recyclerview is invisible.
        //In the layout to assign, pass the current activity as the context in which
        //the layout will be attached.
        recyclerView.layoutManager = LinearLayoutManager(this)
        //
        //At this point, the recyclerview is set but with no items to view.
        //Attach the cardAdapter containing the server response to the recyclerview.
        recyclerView.adapter = adp
    }
}
