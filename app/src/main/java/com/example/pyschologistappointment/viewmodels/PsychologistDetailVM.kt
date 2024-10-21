package com.example.pyschologistappointment.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.pyschologistappointment.models.User
import com.example.pyschologistappointment.utils.PSYCHOLOGIST_RESPONSE
import com.example.pyschologistappointment.utils.Prefrences
import com.example.pyschologistappointment.utils.USER_TABLE
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.gson.Gson

class PsychologistDetailVM : ViewModel() {

    fun fetchCustomer(email: String){
        USER_TABLE.orderByChild("email").equalTo(email).addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (snapshot in dataSnapshot.children) {
                        val value = snapshot.getValue(User::class.java)
                        Prefrences.setStringValue(PSYCHOLOGIST_RESPONSE, Gson().toJson(value))
                        Log.d("FirebaseData", "Data: $value")
                    }
                } else {
                    Log.d("FirebaseData", "No matching data found")
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                throw databaseError.toException() // Handle database error
            }
        })
    }
}