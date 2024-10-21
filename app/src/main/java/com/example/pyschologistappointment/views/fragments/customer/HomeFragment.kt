package com.example.pyschologistappointment.views.fragments.customer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.pyschologistappointment.R
import com.example.pyschologistappointment.databinding.FragmentHomeBinding
import com.example.pyschologistappointment.utils.APPOINTMENT
import com.example.pyschologistappointment.utils.APPOINTMENT_SCREEN
import com.example.pyschologistappointment.utils.BOTTOM_SCREEN
import com.example.pyschologistappointment.utils.HISTORY
import com.example.pyschologistappointment.utils.HOME
import com.example.pyschologistappointment.utils.PROFILE
import com.example.pyschologistappointment.utils.PSYCHOLOGIST
import com.example.pyschologistappointment.utils.Prefrences
import com.example.pyschologistappointment.utils.ROLE
import com.example.pyschologistappointment.utils.SETTINGS

class HomeFragment : Fragment(), View.OnClickListener {


    private var _binding: FragmentHomeBinding? = null
    val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initialize()
        registerClicks()
    }

    private fun initialize(){
        val role = Prefrences.getStringValue(ROLE)
        if (role == PSYCHOLOGIST){
            binding?.bottomNavigationBar?.btnHomeNavigation?.visibility = View.GONE
            binding?.bottomNavigationBar?.btnHistoryNavigation?.visibility = View.GONE
        }

        binding?.bottomNavigationBar?.btnHomeNavigation?.setColorFilter(resources.getColor(R.color.teal_200))
    }

    private fun registerClicks() {
        binding?.bottomNavigationBar?.btnSettingNavigation?.setOnClickListener(this)
        binding?.bottomNavigationBar?.btnHistoryNavigation?.setOnClickListener(this)
        binding?.bottomNavigationBar?.btnAppointmentNavigation?.setOnClickListener(this)
        binding?.bottomNavigationBar?.btnProfileNavigation?.setOnClickListener(this)
        binding?.btnAiChat?.setOnClickListener(this)
        binding?.btnCommunityChat?.setOnClickListener(this)
        binding?.btnBookPsychologist?.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnSettingNavigation -> {
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToSettingFragment())
            }

            R.id.btnHistoryNavigation -> {
                Prefrences.setStringValue(APPOINTMENT_SCREEN, HISTORY)
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToAppointmentHistoryFragment())
            }

            R.id.btnProfileNavigation -> {
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToProfileFragment())
            }

            R.id.btnAppointmentNavigation -> {
                Prefrences.setStringValue(APPOINTMENT_SCREEN, APPOINTMENT)
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToAppointmentFragment())
            }

            R.id.btnAiChat->{
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToAiChatFragment())
            }
            R.id.btnCommunityChat->{
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToCommunityFragment())
            }
            R.id.btnBookPsychologist->{
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToPyschologistFragment())
            }
        }
    }
}