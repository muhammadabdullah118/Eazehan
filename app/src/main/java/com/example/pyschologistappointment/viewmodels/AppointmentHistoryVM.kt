package com.example.pyschologistappointment.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.pyschologistappointment.models.Appointments
import com.example.pyschologistappointment.utils.APPOINTMENT_RESPONSE
import com.example.pyschologistappointment.utils.APPOINTMENT_TABLE
import com.example.pyschologistappointment.utils.AppUtils
import com.example.pyschologistappointment.utils.PENDING
import com.example.pyschologistappointment.utils.Prefrences
import com.example.pyschologistappointment.utils.START
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import com.google.gson.Gson

class AppointmentHistoryVM : ViewModel() {

    var list = listOf<Appointments>()

    fun fetchAppointments( status : String ):List<Appointments>{
        val appointmentList = mutableListOf<Appointments>()
        APPOINTMENT_TABLE.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for(appointments in snapshot.children){
                    val appointment = appointments.getValue<Appointments>()
                    if (appointment != null && appointment.customerId == AppUtils.getLoginResponse()?.id){
                        if (appointment.status == status){
                            appointmentList.add(appointment)
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
//                Toast.makeText(context , "Failed to load data.", Toast.LENGTH_SHORT).show()
            }

        })
        return appointmentList
    }

}