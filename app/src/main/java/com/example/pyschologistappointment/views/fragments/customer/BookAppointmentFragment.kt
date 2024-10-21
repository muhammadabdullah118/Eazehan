package com.example.pyschologistappointment.views.fragments.customer

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContentProviderCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.pyschologistappointment.R
import com.example.pyschologistappointment.databinding.FragmentBookAppointmentBinding
import com.example.pyschologistappointment.utils.APPOINTMENT
import com.example.pyschologistappointment.utils.APPOINTMENT_TABLE
import com.example.pyschologistappointment.utils.AppUtils
import com.example.pyschologistappointment.utils.Prefrences
import com.example.pyschologistappointment.utils.USER_TABLE
import com.example.pyschologistappointment.viewmodels.BookAppointmentVM
import com.example.pyschologistappointment.views.fragments.auth.SignUpFragmentDirections
import com.google.gson.Gson
import java.util.Calendar

class BookAppointmentFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentBookAppointmentBinding? = null
    val binding get() = _binding
    val viewModel: BookAppointmentVM by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentBookAppointmentBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initialize()
        registerClicks()
    }

    private fun initialize() {

        Glide.with(requireContext())
            .load(AppUtils.getPsychologistResponse()?.image)
            .placeholder(R.drawable.profile)
            .error(R.drawable.profile)
            .into(binding?.imgDoctorBA!!)

        binding?.txDoctorLabel?.text =
            "Dr." + AppUtils.getPsychologistResponse()?.fname + " " + AppUtils.getPsychologistResponse()?.lname

    }

    private fun registerClicks() {
        binding?.btnConfirmAppointment?.setOnClickListener(this)
        binding?.loDateBA?.setOnClickListener(this)
        binding?.loTimeBA?.setOnClickListener(this)
        binding?.btnBackBA?.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnBackBA->{
                findNavController().navigateUp()
            }
            R.id.btnConfirmAppointment -> {

                viewModel.psychologistName =
                    AppUtils.getPsychologistResponse()!!.fname + " " + AppUtils.getPsychologistResponse()!!.lname
                viewModel.psychologistId = AppUtils.getPsychologistResponse()!!.id
                viewModel.customerId = AppUtils.getLoginResponse()!!.id
                viewModel.date = binding?.txtDate?.text.toString()
                viewModel.time = binding?.txtTime?.text.toString()
                viewModel.image = AppUtils.getPsychologistResponse()!!.image

                if (viewModel.isEmptyFields()){
                    val appointment = viewModel.createAppointment()
                    Prefrences.setStringValue(APPOINTMENT , Gson().toJson(appointment))
                    APPOINTMENT_TABLE.child( "${appointment.id}").setValue(viewModel.createAppointment())
                        .addOnSuccessListener {
                            findNavController().navigate(
                                BookAppointmentFragmentDirections.actionBookAppointmentFragmentToSuccessFragment()
                            )
                            Toast.makeText(
                                context,
                                " Appointment Booked",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }
                        .addOnFailureListener {
                            Toast.makeText(
                                context,
                                "Error! : can not create appointment",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                }else{
                    viewModel.checkValues()
                    if (viewModel.dateError) {
                        binding?.txtDate?.error = " Please Insert Date"
                    }
                    if (viewModel.timeError) {
                        binding?.txtTime?.error = " Please Insert Time"
                    }
                }
            }

            R.id.loDateBA -> {
                showDatePicker()
            }

            R.id.loTimeBA -> {
                showTimePicker()
            }
        }
    }

    // function to pick the date
    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog =
            DatePickerDialog(requireContext(), { _, selectedYear, selectedMonth, selectedDay ->
                // Months are indexed from 0 in Calendar
                val formattedMonth = selectedMonth + 1
                val selectedDate = "$selectedDay/$formattedMonth/$selectedYear"
                binding?.txtDate?.text = selectedDate
            }, year, month, day)

        // Optional: Set minimum date to today to prevent past dates
        datePickerDialog.datePicker.minDate = calendar.timeInMillis
        datePickerDialog.show()
    }

    // function to pic the time
    private fun showTimePicker() {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePickerDialog =
            TimePickerDialog(requireContext(), { _, selectedHour, selectedMinute ->
                // Check if selected time is within the allowed range (10:00 AM - 10:00 PM)
                if (selectedHour in 10..21 || (selectedHour == 22 && selectedMinute == 0)) {
                    // Format the time as 24-hour, e.g., 14:30
                    val selectedTime = String.format("%02d:%02d", selectedHour, selectedMinute)
                    binding?.txtTime?.text = selectedTime
                } else {
                    // Show a message if the selected time is out of range
                    Toast.makeText(
                        requireContext(),
                        "Please select a time between 10:00 AM and 10:00 PM",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }, hour, minute, true) // true for 24-hour format

        timePickerDialog.show()
    }


}