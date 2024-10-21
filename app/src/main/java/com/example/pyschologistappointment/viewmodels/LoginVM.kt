package com.example.pyschologistappointment.viewmodels

import androidx.lifecycle.ViewModel
import com.example.pyschologistappointment.utils.AppUtils

class LoginVM : ViewModel() {

    var email : String = ""
    var password : String = ""
    var emailError = false
    var passwordError = false



    fun checkInput(): Boolean{
        return email.isNotEmpty() && password.isNotEmpty()
                && AppUtils.validPass(password) && AppUtils.isValidEmail(email)
    }

    fun checkValues(){
        if (email.isEmpty() || AppUtils.isValidEmail(email) ){
            emailError = true
        }
        if ( AppUtils.validPass(password) || password.isEmpty()){
            passwordError = true
        }
    }

}