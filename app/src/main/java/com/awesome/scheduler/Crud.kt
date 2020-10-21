package com.awesome.scheduler

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.crud.*
import org.json.JSONArray

private const val log = "Crud"
class Crud:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.crud)
        //
        //Create an arraylist of items to show on the cardview.
        val arraylist = ArrayList<Model>()
        //
        //Get the input passed over from the previous activity.
        val carditem = JSONArray(intent.getStringExtra("result"))
                //
                //Loop through the input data.
                //Output the values of all the keys, from the carditem array.
                for( obj in 0 until carditem.length()){
                    val item = carditem.getJSONObject(obj)
                    val pk = item.getString("todo")
                    val nam = item.getString("name")
                    val des = item.getString("description")
                    val date = item.getString("date")

                    var status = item.getString("status")
                    if (status == "null"){
                        status = "pending"
                    }
                    //
                    //Populate the recyclerview with carditems.
                    arraylist.add(Model(nam, des,date,status))
                }
        //
        //Initialize the Activity adapter that takes the list of items to display and returns a recyclerview.
        val adp = CardAdapter(arraylist, this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adp
    }
}
