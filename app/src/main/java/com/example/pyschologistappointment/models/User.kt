package com.example.pyschologistappointment.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val id: Int = 0,
    val fname: String = "",
    val lname: String = "",
    val email: String = "",
    val phone: String = "",
    val image: String = "",
    val password: String = "",
    val gender : String = "",
    val role: String = "",
    val designation: String = "",
    val description : String = "",
    val activeStatus: Boolean = true
) : Parcelable
