package com.example.pyschologistappointment.utils

import android.app.Application
import android.content.Context
import android.content.SharedPreferences

class Prefrences( application: Application) {

    init {
        Companion.application = application
    }

    companion object{

        lateinit var application : Application

        fun setStringValue(key : String , value : String){
            sharedPreferences.edit().putString(key,value).apply()
        }

        fun getStringValue(key: String): String? {
            return sharedPreferences.getString(key,null)
        }

        fun setBooleanValue( key: String , value : Boolean){
            sharedPreferences.edit().putBoolean(key,value).apply()
        }

        fun getBooleanValue(key: String) : Boolean{
            return sharedPreferences.getBoolean(key, false)
        }

        private val sharedPreferences : SharedPreferences
            get(){
                return application.getSharedPreferences(
                    "pyschologist_appointment_preference", Context.MODE_PRIVATE
                )
            }
    }
}