package com.awesome.scheduler

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*
import java.time.LocalDateTime

//
//Initialize a constant tag for debugging purpose.
private const val TAG = "Log_message"

class MainActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //
        //set the button clicklistener to this (since this class implements onclicklistener
        save.setOnClickListener(this)
    }
    //Once the user clicks the save button,
    // get-> the selected option.
    //       the schedule description.
    //       the date.
    override fun onClick(v: View?) {
        //
        //Get the option selected.
        val selection = get_selection()
        //
        //Get the user input text.
        val  description = get_description()
        //
        //Date of record entry
        val date = LocalDateTime.now()
        Log.d(TAG,"Data to send...$selection, $description, $date" )
    }
    //
    //Get the selected option from the dropdown list.
    fun get_selection():String{
        val selection = spinner.selectedItem.toString()
        return selection
    }
    //
    //Get the description fed in by the user.
    fun get_description(): String {
        //
        //Get the input fed in by the user.
        val text = text.text.toString()
        //
        //Check whether the edittext is blank. If so alert the user to type something.
        if (text.isEmpty()){
            Toast.makeText(applicationContext, "Type a description to proceed", Toast.LENGTH_SHORT).show()
        }
        return text
    }
    //Save the data to the server.
    //For that to happen I`ll need to make a post request.
    //that can be done by incorporating libraries such as retrofit or volley.
    //in this case I`ll work with volley to make the post request.
    fun send(){
        //
        //Feed the server url..i.e where the data will be saved.
        val url = ""
        //
        //Create a requestque using the volley class.
        val requestque = Volley.newRequestQueue(this)
        //
        //Define the nature of the request to the server...i.e is it a GET or a POST
        val stringRequest = StringRequest(Request.Method.POST,url, Response.Listener {
            //
            //With the response..i.e Onsuccess, Alert the and display the results.
            response -> Toast.makeText(this, response, Toast.LENGTH_LONG ).show()
        }, Response.ErrorListener {
            //
            //OnFailure, alert and display the error message.
            error: VolleyError? ->
            //Display the error message.
            Toast.makeText(this, "something went wrong", Toast.LENGTH_SHORT).show()
            //
            //Show the error message on the debugger.
            Log.d(TAG, error.toString())

        } )

        requestque.add(stringRequest)
        }

 }