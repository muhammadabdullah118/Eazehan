package com.example.pyschologistappointment.application

import android.app.Application
import com.example.pyschologistappointment.utils.Prefrences

class PrefrenceApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Prefrences(this)
    }
}