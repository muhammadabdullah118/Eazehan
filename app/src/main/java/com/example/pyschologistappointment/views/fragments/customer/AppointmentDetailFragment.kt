package com.example.pyschologistappointment.views.fragments.customer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.pyschologistappointment.R
import com.example.pyschologistappointment.databinding.FragmentAppointmentDetailBinding
import com.example.pyschologistappointment.utils.PENDING
import com.example.pyschologistappointment.utils.START
import com.example.pyschologistappointment.viewmodels.AppointmentDetailVM
import com.google.firebase.database.collection.LLRBNode.Color
import java.util.Calendar

class AppointmentDetailFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentAppointmentDetailBinding? = null
    val binding get() = _binding
    val args: AppointmentDetailFragmentArgs by navArgs()
    val viewModel : AppointmentDetailVM by viewModels()
    val calendar = Calendar.getInstance()
    val currentHour = calendar.get(Calendar.HOUR_OF_DAY) // 24-hour format
    val currentMinute = calendar.get(Calendar.MINUTE)
    val currentTime = String.format("%02d:%02d", currentHour, currentMinute)
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH) + 1
    val day = calendar.get(Calendar.DAY_OF_MONTH)
    val currentDate = "$day/$month/$year"


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAppointmentDetailBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initialize()
        registerClicks()
    }

    private fun initialize() {

        viewModel.fetchCustomer(args.appointment.id)

        val status = args.appointment.status
        binding?.txtNameDoctor?.text = args.appointment.psychologistName
        binding?.txtDate?.text = args.appointment.date
        binding?.txtTime?.text = args.appointment.time
        binding?.txtStatus?.text = status

        when (status) {
            PENDING -> {
                binding?.imgStatus?.setImageResource(R.drawable.pending)
            }

            START -> {
                binding?.imgStatus?.setImageResource(R.drawable.calltime)
            }
        }

        Glide.with(this)
            .load(args.appointment.psychologistImage)
            .placeholder(R.drawable.profile) // Optional placeholder
            .error(R.drawable.profile) // Optional error image
            .into(binding!!.imgDoctorDetail)

    }

    private fun registerClicks() {
        binding?.btnJoin?.setOnClickListener(this)
        binding?.btnBackAD?.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnBackAD->{
                findNavController().navigateUp()
            }
            R.id.btnJoin -> {
                if (currentTime == args.appointment.time && currentDate == args.appointment.date) {
                    findNavController().navigate(AppointmentDetailFragmentDirections.actionAppointmentDetailFragmentToMeetingFragment())
                } else {
                    Toast.makeText(context,"Wait for the Appointment Time" , Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}