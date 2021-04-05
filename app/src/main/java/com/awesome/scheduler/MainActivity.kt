package com.awesome.scheduler

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

//
//Initialize a constant tag for debugging purpose.
private const val TAG = "Log_message"
//
//Declare a variable that will be initialized at some later time.
lateinit var selected:String
//
//Create a class that extends the activity class.
//Activity class contains all application lifecycle methods.
//e.g., OnCreate, OnDestroy, OnPause, OnResume etc.
//AppCompatActivity ensures the app can launch on all android OS versions
//unlike the activity class
class MainActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //
        //set the button ClickListener to this for all buttons (since this class implements OnClickListener)
        save.setOnClickListener(this)
        todo.setOnClickListener(this)
        idea.setOnClickListener(this)
        assign.setOnClickListener(this)
        plan.setOnClickListener(this)

    }
    //This function gets the value of the selected radio button
    private fun radio_value(){
        //
        //Get the radio option chosen.
        val radio_option = radio.checkedRadioButtonId
        //
        //Get the selected button.
        val selection:RadioButton = findViewById(radio_option)
        //
        //Get the value from selection.
        selected = selection.text.toString()
    }
    //
    //Every button has an onclick
    //Implement all button`s logic here for each, when clicked.
    override fun onClick(v: View?) {
        //
        //Get all the buttons by their Id and assign functionality to each
        when(v?.id) {
            //
            //When either of the radio buttons are clicked, update the classification hint input field
            //and set textview text in relation to the selected radio button option.
            R.id.plan -> {
                nature.text = "Classify your Plan"
                nature_text.hint = "e.g Vacation"
            }
            R.id.idea -> {
                nature.text = "Classify your Idea"
                nature_text.hint = "e.g Business"
            }
            R.id.assign -> {
                nature.text = "Classify your Task"
                nature_text.hint = "e.g Assignment"
            }
            R.id.todo -> {
                nature.text = "Classify your Todo"
                nature_text.hint = "e.g Hiking"
            }
            //
            //Once the user clicks the save button, get the information and save the record.
            R.id.save ->
                //
                //Perform checks on edittext.If empty, alert user to type in a description.
                if (full_des.text.isNotEmpty()) {
                    //
                    //Get the value of the radio button selected
                    radio_value()
                    //
                    //Get the user input fed.
                    val input = get_data()
                    //
                    //Invoke the send to database method that takes a JsonObject...and returns some data.
                    send(input)
                    //
                    //Package the results to be viewed in another activity other than this.
                } else
                    Toast.makeText(this, "Type something to proceed", Toast.LENGTH_SHORT).show()
            //
            //Can`t reach here..anyway let me handle such an occurence though impossible
            else -> println("something is up")
        }

    }
    //
    //Get the option selected and the description fed in by the user.
    private fun get_data(): JSONObject {
        //
        //Create a Json object structure where the data will be stored.
        val record = JSONObject()
        //
        //Get the schedule type from the user input
        val type = nature_text.text.toString()
        //
        //Get the schedule description
        val description = full_des.text.toString()
        //
        //Get the date of record entry
        val date = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE)
        //
        //Categorize user input to form post data
        val values = JSONObject()
        values.put("option", selected)
        values.put("name", type)
        values.put("description", description)
        values.put("date", date)
        //
        //Package the post data and the method to run on the server into a JsonObject
        //structure in preparation to send to server.
        record.put("method","create")
        record.put("data",values)
        //
        return record
    }
    //Save the data to the server.
    //For that to happen I`ll need to make a post request.
    //that can be done by incorporating libraries such as retrofit or volley.
    //in this case I`ll work with volley to make the post request.
    private fun send(record:JSONObject) {
        //
        //Get the server address.
        val url = Constants.url
        //
        //Create a requestque using the volley class.
        val requestque = Volley.newRequestQueue(this)
        //
        //Define the nature of the request to the server...i.e is it a GET or a POST
        val jsonrequest = JsonObjectRequest(
            Request.Method.POST,
            url,
            record,
            {
                //
                //OnSuccess,
                response ->
                    //
                    //Alert Success message to user.
                    Toast.makeText(applicationContext, "Save successful", Toast.LENGTH_SHORT).show()
                    //
                    //Show the success message on the logcat.
                    Log.d(TAG, "$response")
                    //
                    //Prepare to launch the next activity
                    //Launch the recyclerview activity.
                    startActivity(Intent(this, Crud::class.java))
            },
            {
                //
                //OnFailure, alert and display the error message.
                error: VolleyError? ->
                    //Display the error message.
                    Toast.makeText(this, "Check your Network Connection status", Toast.LENGTH_SHORT).show()
                    //
                    //Show the error message on the debugger.
                    Log.d(TAG  , error.toString())
            }
        )
        //
        requestque.add(jsonrequest)
    }
}






