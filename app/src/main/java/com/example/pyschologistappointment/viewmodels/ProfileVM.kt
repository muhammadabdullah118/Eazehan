package com.example.pyschologistappointment.viewmodels

import android.app.AlertDialog
import android.content.Context
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import com.example.pyschologistappointment.R
import com.example.pyschologistappointment.models.User
import com.example.pyschologistappointment.utils.AppUtils
import com.example.pyschologistappointment.views.fragments.customer.ProfileFragmentDirections

class ProfileVM : ViewModel() {

    fun createUser( ) : User {
        val user = User(
            id = AppUtils.getLoginResponse()!!.id,
            image = AppUtils.getLoginResponse()!!.image,
            fname = AppUtils.getLoginResponse()!!.fname,
            lname =AppUtils.getLoginResponse()!!.lname,
            phone = AppUtils.getLoginResponse()!!.phone,
            gender = AppUtils.getLoginResponse()!!.gender,
            email = AppUtils.getLoginResponse()!!.email,
            role = AppUtils.getLoginResponse()!!.role,
            designation = AppUtils.getLoginResponse()!!.designation,
            description = AppUtils.getLoginResponse()!!.description,
            password = AppUtils.getLoginResponse()!!.password,
            activeStatus = false
        )
        return user
    }
}