package com.awesome.scheduler

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import java.time.LocalDateTime

//
//Initialize a constant tag for debugging purpose.
private const val TAG = "Log_message"

class MainActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //
        //set the button ClickListener to this (since this class implements onclicklistener)
        save.setOnClickListener(this)

    }
    //Once the user clicks the save button, get the information and save the record.
    override fun onClick(v: View?) {
        //
        //Perform checks on edittext.If empty, alert user to type in a description.
        if (text.text.isNotEmpty()){
            //
            //Get the user input fed.
            val input = get_data()
            //
            //Invoke the send to database method which requires a JSonObject.
            send(input)
            val intent = Intent(applicationContext, Crud::class.java).apply {
                putExtra("data", data.toString())
            }
            startActivity(intent)

        }else
        Toast.makeText(this, "Type something to proceed", Toast.LENGTH_SHORT).show()
    }
    //
    //Get the option selected and the description fed in by the user.
    private fun get_data(): JSONObject {
        //
        //Create a Json object structure where the data will be stored.
        val data = JSONObject()
        //
        //Get the user input.i.e the selected option, description and the date.
        val selection = spinner.selectedItem.toString()
        val description = text.text.toString()
        //Date of record entry
        val date = LocalDateTime.now()
        //
        //Display the entries on the debugger.
        Log.d(TAG,"Data to send...$selection, $description, $date" )
        //
        //Package the user data into the JsonObject structure in preparation to send to server.
        data.put("option", selection)
        data.put("description", description)
        data.put("date", date)

        return data
    }
    //Save the data to the server.
    //For that to happen I`ll need to make a post request.
    //that can be done by incorporating libraries such as retrofit or volley.
    //in this case I`ll work with volley to make the post request.
    private fun send(data:JSONObject){
        //
        val url = Constants.url
        //
        //Create a requestque using the volley class.
        val requestque = Volley.newRequestQueue(this)
        //
        //Define the nature of the request to the server...i.e is it a GET or a POST
        val jsonrequest = JsonObjectRequest(Request.Method.POST,url, data, {
            //
            //OnSuccess, Alert and display the response.
            response -> Toast.makeText(this, "$response", Toast.LENGTH_LONG ).show()
        }, {
            //
            //OnFailure, alert and display the error message.
            error: VolleyError? ->
            //Display the error message.
            Toast.makeText(this, "something went wrong", Toast.LENGTH_SHORT).show()
            //
            //Show the error message on the debugger.
            Log.d(TAG, error.toString())

        })

        requestque.add(jsonrequest)
        }

 }