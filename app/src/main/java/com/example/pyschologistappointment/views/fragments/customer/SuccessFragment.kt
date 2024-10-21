package com.example.pyschologistappointment.views.fragments.customer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.pyschologistappointment.R
import com.example.pyschologistappointment.databinding.FragmentSuccessBinding
import com.example.pyschologistappointment.utils.AppUtils


class SuccessFragment : Fragment() , View.OnClickListener {

    private var _binding : FragmentSuccessBinding? =  null
    val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding =  FragmentSuccessBinding.inflate(inflater, container, false)
        return  binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initialize()
        registerClicks()
    }

    private fun initialize(){
        Glide.with(requireContext())
            .load(AppUtils.getPsychologistResponse()?.image)
            .placeholder(R.drawable.profile)
            .error(R.drawable.profile)
            .into(binding?.imgDoctorS!!)

        binding?.txtNamePsychologist?.text = "Dr."+AppUtils.getAppointment()!!.psychologistName
        binding?.txtDateS?.text = AppUtils.getAppointment()!!.date
        binding?.txtTimeS?.text = AppUtils.getAppointment()!!.time
    }

    private fun registerClicks(){
        binding?.btnDoneAppointment?.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btnDoneAppointment->{
                findNavController().navigate(SuccessFragmentDirections.actionSuccessFragmentToAppointmentFragment())
            }
        }
    }


}