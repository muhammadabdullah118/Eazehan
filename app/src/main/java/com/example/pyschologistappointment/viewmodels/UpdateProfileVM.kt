package com.example.pyschologistappointment.viewmodels

import androidx.lifecycle.ViewModel
import com.example.pyschologistappointment.models.User
import com.example.pyschologistappointment.utils.AppUtils
import com.example.pyschologistappointment.utils.PATIENT
import com.example.pyschologistappointment.utils.Prefrences
import com.example.pyschologistappointment.utils.ROLE
import kotlin.random.Random

class UpdateProfileVM : ViewModel() {

    var firstName = ""
    var lastName = ""
    var phone = ""
    var designation = ""
    var description = ""
    var firstNameError = false
    var lastNameError = false
    var phoneError =  false
    var designationError = false
    var descriptionError = false

    fun  isEmptyFields():Boolean{
        return  firstName.isNotEmpty() && lastName.isNotEmpty() && phone.isNotEmpty()
                && designation.isNotEmpty() && description.isNotEmpty()
    }

    fun  isCustomerEmptyFields():Boolean{
        return  firstName.isNotEmpty() && lastName.isNotEmpty() && phone.isNotEmpty()

    }

    fun validPhone(): Boolean{
        return phone.length >= 11
    }

    fun  checkValues(){
        firstNameError = firstName.isEmpty()
        lastNameError = lastName.isEmpty()
        phoneError = (phone.isEmpty() || !validPhone())
        descriptionError = designation.isEmpty()
        descriptionError = description.isEmpty()
    }

    fun createUser( image : String) : User {
        val user = User(
            id = AppUtils.getLoginResponse()!!.id,
            image = image,
            fname = firstName,
            lname =lastName,
            phone = phone,
            gender = AppUtils.getLoginResponse()!!.gender,
            email = AppUtils.getLoginResponse()!!.email,
            role = AppUtils.getLoginResponse()!!.role,
            designation = designation,
            description = description,
            password = AppUtils.getLoginResponse()!!.password,
            activeStatus = true
        )
        return user
    }

}