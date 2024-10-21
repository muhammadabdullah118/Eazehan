package com.example.pyschologistappointment.views.fragments.customer

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pyschologistappointment.R
import com.example.pyschologistappointment.databinding.FragmentAppointmentBinding
import com.example.pyschologistappointment.listener.AppointmentListener
import com.example.pyschologistappointment.models.Appointments
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
import com.example.pyschologistappointment.viewmodels.AppointmentVM
import com.example.pyschologistappointment.views.adapters.AppointmentAdapter
import com.example.pyschologistappointment.views.adapters.PsychologistAdapter
import com.google.gson.Gson


class AppointmentFragment : Fragment(), View.OnClickListener, AppointmentListener {

    private var _binding: FragmentAppointmentBinding? = null
    val binding get() = _binding
    val viewModel: AppointmentVM by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAppointmentBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initialize()
        registerClicks()
    }

    private fun initialize() {
        val role = Prefrences.getStringValue(ROLE)
        if (role == PSYCHOLOGIST) {
            binding?.bottomNavigationBar?.btnHomeNavigation?.visibility = View.GONE
            binding?.bottomNavigationBar?.btnHistoryNavigation?.visibility = View.GONE
        }

        binding?.bottomNavigationBar?.btnAppointmentNavigation?.setColorFilter(resources.getColor(R.color.teal_200))

        viewModel.list = viewModel.fetchAppointments()

        if (viewModel.list.isEmpty()) {

            binding?.loadingProgressBar?.visibility = View.VISIBLE
            binding?.loadingProgressBar?.playAnimation()

            Handler(Looper.getMainLooper()).postDelayed(
                {
                    if (isAdded) {
                        binding?.rvAppointments?.layoutManager =
                            LinearLayoutManager(requireContext())
                        binding?.rvAppointments?.adapter =
                            AppointmentAdapter(requireContext(), viewModel.list, this)
                    }

                    binding?.loadingProgressBar?.visibility = View.GONE
                    binding?.loadingProgressBar?.cancelAnimation()

                }, 5000
            )
        } else {
            Toast.makeText(context, " No Appointments ", Toast.LENGTH_SHORT).show()
        }

    }

    private fun registerClicks() {
        binding?.bottomNavigationBar?.btnHomeNavigation?.setOnClickListener(this)
        binding?.bottomNavigationBar?.btnHistoryNavigation?.setOnClickListener(this)
        binding?.bottomNavigationBar?.btnProfileNavigation?.setOnClickListener(this)
        binding?.bottomNavigationBar?.btnSettingNavigation?.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {

            R.id.btnHistoryNavigation -> {
                Prefrences.setStringValue(APPOINTMENT_SCREEN, HISTORY)
                findNavController().navigate(AppointmentFragmentDirections.actionAppointmentFragmentToAppointmentHistoryFragment())
            }

            R.id.btnHomeNavigation -> {
                findNavController().navigate(AppointmentFragmentDirections.actionAppointmentFragmentToHomeFragment())
            }

            R.id.btnProfileNavigation -> {
                findNavController().navigate(AppointmentFragmentDirections.actionAppointmentFragmentToProfileFragment())
            }

            R.id.btnSettingNavigation -> {
                findNavController().navigate(AppointmentFragmentDirections.actionAppointmentFragmentToSettingFragment())
            }

        }
    }

    override fun onAppointmentClick(appointments: Appointments) {
        findNavController().navigate(
            AppointmentFragmentDirections.actionAppointmentFragmentToAppointmentDetailFragment(
                appointments
            )
        )

    }


}