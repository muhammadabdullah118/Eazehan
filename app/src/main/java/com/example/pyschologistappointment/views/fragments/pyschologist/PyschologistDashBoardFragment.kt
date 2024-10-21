package com.example.pyschologistappointment.views.fragments.pyschologist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.pyschologistappointment.R
import com.example.pyschologistappointment.databinding.FragmentPyschologistDashBoardBinding
import com.example.pyschologistappointment.utils.APPOINTMENT
import com.example.pyschologistappointment.utils.AppUtils
import com.example.pyschologistappointment.utils.BOTTOM_SCREEN
import com.example.pyschologistappointment.utils.PROFILE
import com.example.pyschologistappointment.utils.PSYCHOLOGIST
import com.example.pyschologistappointment.utils.Prefrences
import com.example.pyschologistappointment.utils.ROLE
import com.example.pyschologistappointment.utils.SETTINGS


class PyschologistDashBoardFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentPyschologistDashBoardBinding? = null
    val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentPyschologistDashBoardBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initialize()
        registerClicks()

    }

    private fun initialize() {
        binding?.txtNamePD?.text = AppUtils.getLoginResponse()?.fname
        binding?.bottomNavigationBar?.btnHomeNavigation?.visibility = View.GONE
        binding?.bottomNavigationBar?.btnHistoryNavigation?.visibility = View.GONE
    }

    private fun registerClicks() {
        binding?.btnStartPD?.setOnClickListener(this)
//        binding?.bottomNavigationBar?.btnSettingNavigation?.setOnClickListener(this)
//        binding?.bottomNavigationBar?.btnAppointmentNavigation?.setOnClickListener(this)
//        binding?.bottomNavigationBar?.btnProfileNavigation?.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnStartPD -> {
                Prefrences.setStringValue(ROLE , PSYCHOLOGIST)
                findNavController().navigate(PyschologistDashBoardFragmentDirections.actionPyschologistDashBoardFragmentToProfileFragment())
            }

            R.id.btnProfileNavigation -> {
                Prefrences.setStringValue(ROLE , PSYCHOLOGIST)
                findNavController().navigate(PyschologistDashBoardFragmentDirections.actionPyschologistDashBoardFragmentToProfileFragment())
            }

            R.id.btnAppointmentNavigation -> {
                Prefrences.setStringValue(ROLE , PSYCHOLOGIST)
                findNavController().navigate(PyschologistDashBoardFragmentDirections.actionPyschologistDashBoardFragmentToAppointmentFragment())
            }

            R.id.btnSettingNavigation -> {
                Prefrences.setStringValue(ROLE , PSYCHOLOGIST)
                findNavController().navigate(PyschologistDashBoardFragmentDirections.actionPyschologistDashBoardFragmentToSettingFragment())
            }
        }
    }

}