package com.example.pyschologistappointment.views.fragments.customer

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.pyschologistappointment.R
import com.example.pyschologistappointment.databinding.FragmentCustomerDashBoardBinding
import com.example.pyschologistappointment.utils.APPOINTMENT
import com.example.pyschologistappointment.utils.AppUtils
import com.example.pyschologistappointment.utils.BOTTOM_SCREEN
import com.example.pyschologistappointment.utils.HISTORY
import com.example.pyschologistappointment.utils.HOME
import com.example.pyschologistappointment.utils.PROFILE
import com.example.pyschologistappointment.utils.Prefrences
import com.example.pyschologistappointment.utils.SETTINGS

class CustomerDashBoardFragment : Fragment() , View.OnClickListener {

    private var _binding : FragmentCustomerDashBoardBinding? = null
    val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCustomerDashBoardBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initialize()
        registerClicks()
    }

    private fun initialize(){
        binding?.txtNameCD?.text = AppUtils.getLoginResponse()?.fname
    }

    private fun registerClicks(){
        binding?.bottomNavigationBar?.btnSettingNavigation?.setOnClickListener(this)
        binding?.bottomNavigationBar?.btnHomeNavigation?.setOnClickListener(this)
        binding?.bottomNavigationBar?.btnHistoryNavigation?.setOnClickListener(this)
        binding?.bottomNavigationBar?.btnAppointmentNavigation?.setOnClickListener(this)
        binding?.bottomNavigationBar?.btnProfileNavigation?.setOnClickListener(this)
        binding?.btnStartCD?.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btnSettingNavigation->{
                Prefrences.setStringValue( BOTTOM_SCREEN,  SETTINGS)
                findNavController().navigate(CustomerDashBoardFragmentDirections.actionCustomerDashBoardFragmentToSettingFragment())
            }
            R.id.btnHomeNavigation->{
                Prefrences.setStringValue( BOTTOM_SCREEN,  HOME)
                findNavController().navigate(CustomerDashBoardFragmentDirections.actionCustomerDashBoardFragmentToHomeFragment())
            }
            R.id.btnHistoryNavigation->{
                Prefrences.setStringValue( BOTTOM_SCREEN,  HISTORY)
                findNavController().navigate(CustomerDashBoardFragmentDirections.actionCustomerDashBoardFragmentToAppointmentHistoryFragment())
            }
            R.id.btnProfileNavigation->{
                Prefrences.setStringValue( BOTTOM_SCREEN,  PROFILE)
                findNavController().navigate(CustomerDashBoardFragmentDirections.actionCustomerDashBoardFragmentToProfileFragment())
            }
            R.id.btnAppointmentNavigation->{
                Prefrences.setStringValue( BOTTOM_SCREEN,  APPOINTMENT)
                findNavController().navigate(CustomerDashBoardFragmentDirections.actionCustomerDashBoardFragmentToAppointmentFragment())
            }
            R.id.btnStartCD->{
                Prefrences.setStringValue( BOTTOM_SCREEN,  HOME)
                findNavController().navigate(CustomerDashBoardFragmentDirections.actionCustomerDashBoardFragmentToHomeFragment())
            }
        }
    }
}