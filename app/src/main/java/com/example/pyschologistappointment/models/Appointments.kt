package com.example.pyschologistappointment.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Appointments(
    val id : Int = 0 ,
    val customerId : Int = 0,
    val psychologistId : Int = 0,
    val psychologistName : String = "",
    val psychologistImage : String = "",
    val date : String = "",
    val time : String  = "",
    val review : Int = 0,
    val feedback : String = "",
    val status : String = ""
):Parcelable
