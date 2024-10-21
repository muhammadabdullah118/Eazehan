package com.example.pyschologistappointment.viewmodels

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.ViewModel
import com.example.pyschologistappointment.models.Appointments
import com.example.pyschologistappointment.models.User
import com.example.pyschologistappointment.utils.AppUtils
import com.example.pyschologistappointment.utils.PATIENT
import com.example.pyschologistappointment.utils.PENDING
import com.example.pyschologistappointment.utils.Prefrences
import com.example.pyschologistappointment.utils.ROLE
import java.util.Calendar
import kotlin.random.Random

class BookAppointmentVM : ViewModel() {


    var customerId = 0
    var psychologistId = 0
    var psychologistName = ""
    var date = ""
    var time = ""
    var image = ""
    var dateError = false
    var timeError = false

    fun isEmptyFields(): Boolean {
        return time.isNotEmpty() && date.isNotEmpty()

    }

    fun checkValues() {
        dateError = date.isEmpty()
        timeError = time.isEmpty()
    }

    fun createAppointment(): Appointments {
        val id = Random.nextInt(1, 1000)
        val appointment = Appointments(
            id = id,
            customerId = customerId,
            psychologistId = psychologistId,
            psychologistName = psychologistName,
            psychologistImage = image,
            date = date ,
            time = time,
            status = PENDING
        )
        return appointment
    }

}