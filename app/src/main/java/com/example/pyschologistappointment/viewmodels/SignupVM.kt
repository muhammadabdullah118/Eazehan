package com.example.pyschologistappointment.viewmodels

import androidx.lifecycle.ViewModel
import com.example.pyschologistappointment.models.User
import com.example.pyschologistappointment.utils.AppUtils
import com.example.pyschologistappointment.utils.PATIENT
import com.example.pyschologistappointment.utils.Prefrences
import com.example.pyschologistappointment.utils.ROLE
import kotlin.random.Random

class SignupVM : ViewModel() {

    var firstName = ""
    var lastName = ""
    var phone = ""
    var email = ""
    var password = ""
    var gender= ""
    var role = ""
    var confirmPassword=""
    var firstNameError = false
    var lastNameError = false
    var phoneError =  false
    var emailError = false
    var passwordError = false
    var confirmPasswordError = false
    var genderError = false

    fun  isEmptyFields():Boolean{
        return  firstName.isNotEmpty() && lastName.isNotEmpty() && phone.isNotEmpty()
                && email.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty()
                && gender.isNotEmpty() && (password == confirmPassword) && AppUtils.validPass(password)
                && AppUtils.isValidEmail(email) &&  validPhone()
                && password.isNotEmpty()
    }

    fun validPhone(): Boolean{
        return phone.length >= 11
    }

    fun  checkValues(){
        firstNameError = firstName.isEmpty()
        lastNameError = lastName.isEmpty()
        phoneError = (phone.isEmpty() || !validPhone())
        emailError = (email.isEmpty() || !AppUtils.isValidEmail(email))
        genderError = gender.isEmpty()
        passwordError = (password.isEmpty() || !AppUtils.validPass(password))
        confirmPasswordError = (confirmPassword.isEmpty() || !AppUtils.validPass(confirmPassword) || password==confirmPassword)
    }

    fun createUser() : User{
        val  id = Random.nextInt(1,1000)
        val active =  if(Prefrences.getStringValue(ROLE) == PATIENT){
            true
        }else{
          false
        }
        val user = User(
            id = id,
            fname = firstName,
            lname =lastName,
            phone = phone,
            email = email,
            password = password,
            gender = gender,
            role = role,
            activeStatus = active
        )
        return user
    }
}