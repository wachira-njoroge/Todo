package com.awesome.scheduler

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
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
        //set the button clicklistener to this (since this class implements onclicklistener
        save.setOnClickListener(this)

    }
    //Once the user clicks the save button,
    // get-> the selected option.
    //       the schedule description.
    //       the date.
    override fun onClick(v: View?) {
        //
        //Perform checks on edittext.If empty, alert user to type in a description.
        if (text.text.isNotEmpty()){
            //
            //Get the user input fed.
            val input = get_data()
            //
            //Invoke the save to database method which requires a JSonObject.
            send(input)
        }else
        {Toast.makeText(this, "Type something to proceed", Toast.LENGTH_SHORT).show()}
    }
    //
    //Get the option selected and the description fed in by the user.
    fun get_data(): JSONObject {
        //
        //Create a Json object structure where the data will be saved.
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
    fun send(data:JSONObject){
        //
        //Feed the server url..i.e where the data will be saved.
        val url = "http://mutall.co.ke/soloo/todo/todo.php"
        //
        //Create a requestque using the volley class.
        val requestque = Volley.newRequestQueue(this)
        //
        //Define the nature of the request to the server...i.e is it a GET or a POST
        val jsonrequest = JsonObjectRequest(Request.Method.POST,url, data, {
            //
            //With the response..i.e Onsuccess, Alert the and display the results.
            response -> Toast.makeText(this, response.toString(), Toast.LENGTH_LONG ).show()
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