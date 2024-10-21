package com.example.pyschologistappointment.views.fragments.customer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.pyschologistappointment.R
import com.example.pyschologistappointment.databinding.FragmentPyschologistDetailBinding
import com.example.pyschologistappointment.models.Appointments
import com.example.pyschologistappointment.viewmodels.PsychologistDetailVM
import com.example.pyschologistappointment.views.adapters.PsychologistAdapter
import com.example.pyschologistappointment.views.adapters.ReviewAdapter


class PyschologistDetailFragment : Fragment() , View.OnClickListener {

    private var _binding : FragmentPyschologistDetailBinding? = null
    val binding get() = _binding
    val args : PyschologistDetailFragmentArgs by navArgs()
    val viewModel : PsychologistDetailVM by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding =  FragmentPyschologistDetailBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initialize()
        registerClicks()
    }

    private fun initialize(){
        val email = args.user.email
        viewModel.fetchCustomer(email)

        Glide.with(requireContext())
            .load(args.user.image)
            .placeholder(R.drawable.profile)
            .error(R.drawable.profile)
            .into(binding?.imgDoctor!!)


        binding?.txtDoctorName?.text = "Dr." + args.user.fname + " " + args.user.lname
        binding?.txtDetail?.text = args.user.description

//        binding?.rvReviews?.layoutManager =  LinearLayoutManager(requireContext())
//        binding?.rvReviews?.adapter = ReviewAdapter(requireContext() , )
    }

    private fun registerClicks(){
        binding?.btnGetAppointment?.setOnClickListener(this)
        binding?.btnBackPD?.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btnBackPD->{
                findNavController().navigateUp()
            }
            R.id.btnGetAppointment->{
                findNavController().navigate(PyschologistDetailFragmentDirections.actionPyschologistDetailFragmentToBookAppointmentFragment())
            }
        }
    }

}