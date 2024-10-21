package com.example.pyschologistappointment.viewmodels

import androidx.lifecycle.ViewModel
import com.example.pyschologistappointment.models.User
import com.example.pyschologistappointment.utils.PSYCHOLOGIST_RESPONSE
import com.example.pyschologistappointment.utils.Prefrences
import com.example.pyschologistappointment.utils.USER_TABLE
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import com.google.gson.Gson

class PsychologistListVM : ViewModel() {

    var list = listOf<User>()

    fun fetchPsychologist( designation : String):List<User>{
        val userList = mutableListOf<User>()
        USER_TABLE.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for(users in snapshot.children){
                    val user = users.getValue<User>()
                    if (user != null && user.role == "psychologist" && user.designation == designation){
                        userList.add(user)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
//                Toast.makeText(context , "Failed to load data.", Toast.LENGTH_SHORT).show()
            }

        })
        return userList
    }

}