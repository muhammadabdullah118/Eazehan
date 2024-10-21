package com.example.pyschologistappointment.listener

import com.example.pyschologistappointment.models.Appointments

interface AppointmentListener {

    fun onAppointmentClick(appointments: Appointments)
}