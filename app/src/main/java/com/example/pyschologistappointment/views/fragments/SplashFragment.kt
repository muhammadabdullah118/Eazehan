package com.example.pyschologistappointment.views.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.pyschologistappointment.R
import com.example.pyschologistappointment.databinding.FragmentSplashBinding
import com.example.pyschologistappointment.utils.AppUtils
import com.example.pyschologistappointment.utils.ISLOGEDIN
import com.example.pyschologistappointment.utils.PATIENT
import com.example.pyschologistappointment.utils.PSYCHOLOGIST
import com.example.pyschologistappointment.utils.Prefrences
import com.example.pyschologistappointment.utils.ROLE


class SplashFragment : Fragment() , View.OnClickListener {

    private var _binding : FragmentSplashBinding? =  null
    val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initialize()
        registerClicks()
    }

    private fun initialize(){

        if (AppUtils.getLoginResponse() != null) {
            // User is logged in, navigate to HomeFragment
            if (Prefrences.getStringValue(ROLE) == PATIENT) {
                findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToHomeFragment())
            }else{
                findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToAppointmentFragment())
            }
        }

//        if (Prefrences.getBooleanValue(ISLOGEDIN)) {
//            // User is logged in, navigate to HomeFragment
//            findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToHomeFragment())
//        }

    }

    private fun registerClicks(){
        binding?.btnDoctorSF?.setOnClickListener(this)
        binding?.btnPatientSF?.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btnDoctorSF->{
                Prefrences.setStringValue(ROLE , PSYCHOLOGIST)
                findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToSignInFragment())
            }
            R.id.btnPatientSF->{
                Prefrences.setStringValue(ROLE , PATIENT)
                findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToSignInFragment())
            }
        }
    }


}