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
import com.example.pyschologistappointment.databinding.FragmentAppointmentHistoryBinding
import com.example.pyschologistappointment.listener.AppointmentListener
import com.example.pyschologistappointment.models.Appointments
import com.example.pyschologistappointment.utils.APPOINTMENT
import com.example.pyschologistappointment.utils.APPOINTMENT_SCREEN
import com.example.pyschologistappointment.utils.BOTTOM_SCREEN
import com.example.pyschologistappointment.utils.CANCEL
import com.example.pyschologistappointment.utils.COMPLETE
import com.example.pyschologistappointment.utils.HISTORY
import com.example.pyschologistappointment.utils.HOME
import com.example.pyschologistappointment.utils.PROFILE
import com.example.pyschologistappointment.utils.PSYCHOLOGIST
import com.example.pyschologistappointment.utils.Prefrences
import com.example.pyschologistappointment.utils.ROLE
import com.example.pyschologistappointment.utils.SETTINGS
import com.example.pyschologistappointment.viewmodels.AppointmentHistoryVM
import com.example.pyschologistappointment.views.adapters.AppointmentAdapter

class AppointmentHistoryFragment : Fragment(), View.OnClickListener, AppointmentListener {

    private var _binding: FragmentAppointmentHistoryBinding? = null
    val binding get() = _binding
    val viewModel: AppointmentHistoryVM by viewModels()
    var adapter: AppointmentAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAppointmentHistoryBinding.inflate(inflater, container, false)
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

        binding?.bottomNavigationBar?.btnHistoryNavigation?.setColorFilter(resources.getColor(R.color.teal_200))


        viewModel.list = viewModel.fetchAppointments(COMPLETE)

        if (viewModel.list.isEmpty()) {

            binding?.loadingProgressBar?.visibility = View.VISIBLE
            binding?.loadingProgressBar?.playAnimation()

            Handler(Looper.getMainLooper()).postDelayed(
                {
                    if (isAdded) {
                        binding?.rvAppointmentsHistory?.layoutManager =
                            LinearLayoutManager(requireContext())
                        adapter = AppointmentAdapter(requireContext(), viewModel.list, this)
                        binding?.rvAppointmentsHistory?.adapter = adapter
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
        binding?.bottomNavigationBar?.btnSettingNavigation?.setOnClickListener(this)
        binding?.bottomNavigationBar?.btnHomeNavigation?.setOnClickListener(this)
        binding?.bottomNavigationBar?.btnAppointmentNavigation?.setOnClickListener(this)
        binding?.bottomNavigationBar?.btnProfileNavigation?.setOnClickListener(this)
        binding?.btnCompleteFilter?.setOnClickListener(this)
        binding?.btnCancelFilter?.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnCompleteFilter -> {

                binding?.loadingProgressBar?.visibility = View.VISIBLE
                binding?.loadingProgressBar?.playAnimation()

                binding?.btnCancelFilter?.setTextColor(resources.getColor(R.color.teal_200))
                binding?.btnCancelFilter?.setBackgroundColor(resources.getColor(R.color.white))
                binding?.btnCompleteFilter?.setTextColor(resources.getColor(R.color.white))
                binding?.btnCompleteFilter?.setBackgroundColor(resources.getColor(R.color.teal_200))

                viewModel.list = viewModel.fetchAppointments(COMPLETE)
                adapter?.updateSetAppointmentList(viewModel.list)

                Handler(Looper.getMainLooper()).postDelayed(
                    {
                        binding?.loadingProgressBar?.visibility = View.GONE
                        binding?.loadingProgressBar?.cancelAnimation()
                    }, 5000
                )


            }

            R.id.btnCancelFilter -> {

                binding?.loadingProgressBar?.visibility = View.VISIBLE
                binding?.loadingProgressBar?.playAnimation()

                binding?.btnCompleteFilter?.setTextColor(resources.getColor(R.color.teal_200))
                binding?.btnCompleteFilter?.setBackgroundColor(resources.getColor(R.color.white))
                binding?.btnCancelFilter?.setTextColor(resources.getColor(R.color.white))
                binding?.btnCancelFilter?.setBackgroundColor(resources.getColor(R.color.teal_200))

                viewModel.list = viewModel.fetchAppointments(CANCEL)
                adapter?.updateSetAppointmentList(viewModel.list)

                Handler(Looper.getMainLooper()).postDelayed(
                    {
                        binding?.loadingProgressBar?.visibility = View.GONE
                        binding?.loadingProgressBar?.cancelAnimation()
                    }, 5000
                )

            }

            R.id.btnSettingNavigation -> {
                findNavController().navigate(AppointmentHistoryFragmentDirections.actionAppointmentHistoryFragmentToSettingFragment())
            }

            R.id.btnHomeNavigation -> {
                findNavController().navigate(AppointmentHistoryFragmentDirections.actionAppointmentHistoryFragmentToHomeFragment())
            }

            R.id.btnAppointmentNavigation -> {
                Prefrences.setStringValue(APPOINTMENT_SCREEN, APPOINTMENT)
                findNavController().navigate(AppointmentHistoryFragmentDirections.actionAppointmentHistoryFragmentToAppointmentFragment())
            }

            R.id.btnProfileNavigation -> {
                findNavController().navigate(AppointmentHistoryFragmentDirections.actionAppointmentHistoryFragmentToProfileFragment())
            }

        }
    }

    override fun onAppointmentClick(appointments: Appointments) {

    }
}