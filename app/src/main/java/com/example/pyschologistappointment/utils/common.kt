package com.example.pyschologistappointment.utils

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

//Roles
const val PATIENT = "patient"
const val PSYCHOLOGIST = "psychologist"
const val ROLE = "role"
const val LOGIN_RESPONSE = "loginResponse"
const val ISLOGEDIN = "false"
const val TRUE = "true"
const val FALSE = "false"
const val PSYCHOLOGIST_RESPONSE = "psychologistResponse"
const val APPOINTMENT_BOOK = "appointment"
const val APPOINTMENT_RESPONSE = "appointmentResponse"
const val DESIGNATION = "designation"
const val SENIOR = "senior"
const val JUNIOR = "junior"
//BottomColor
const val HOME = "home"
const val PROFILE = "profile"
const val HISTORY = "history"
const val APPOINTMENT = "appointment"
const val SETTINGS = "settings"
const val BOTTOM_SCREEN = "screen"
//Appointment Status
const val APPOINTMENT_STATUS = "appointmentStatus"
const val PENDING = "pending"
const val COMPLETE = "complete"
const val CANCEL = "cancel"
const val START = "start"
//appointment view
const val APPOINTMENT_SCREEN = "appointmentScreen"
//Firebase
val AUTH = FirebaseAuth.getInstance()
val DATABASE = FirebaseDatabase.getInstance()
val USER_TABLE = DATABASE.getReference("user_table")
val APPOINTMENT_TABLE = DATABASE.getReference("appointment_table")
val AICHAT_TABLE = DATABASE.getReference("aichat_table")
val COMMUNITYCHAT_TABLE = DATABASE.getReference("communitychat_table")