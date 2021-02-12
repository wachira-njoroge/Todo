package com.awesome.scheduler

import android.content.Context

//
//This class defines the properties of each CardItem
//In kotlin, you can define your class constructor parameters as below
class Model (val context: Context, val primary_key:Int, val name:String, val description:String, val date:String, val status:String){

}