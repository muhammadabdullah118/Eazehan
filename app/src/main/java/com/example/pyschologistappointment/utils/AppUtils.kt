package com.example.pyschologistappointment.utils

import android.util.Patterns
import com.example.pyschologistappointment.models.Appointments
import com.example.pyschologistappointment.models.User
import com.google.gson.Gson
import java.util.regex.Pattern

object AppUtils {

    fun validPass(password : String): Boolean{
        return password.length >= 8
    }


    fun isValidEmail(email : String): Boolean {
        val pattern : Pattern = Patterns.EMAIL_ADDRESS
        return pattern.matcher(email).matches()
    }

    fun getLoginResponse():User?{
        val stringResponse = Prefrences.getStringValue(LOGIN_RESPONSE)
        return Gson().fromJson(stringResponse, User::class.java)
    }

    fun getPsychologistResponse():User?{
        val stringResponse = Prefrences.getStringValue(PSYCHOLOGIST_RESPONSE)
        return Gson().fromJson(stringResponse, User::class.java)
    }

    fun getAppointment():Appointments?{
        val stringResponse = Prefrences.getStringValue(APPOINTMENT)
        return Gson().fromJson(stringResponse, Appointments::class.java)
    }

    fun getAppointmentResponse():Appointments?{
        val stringResponse = Prefrences.getStringValue(APPOINTMENT_RESPONSE)
        return Gson().fromJson(stringResponse, Appointments::class.java)
    }



}