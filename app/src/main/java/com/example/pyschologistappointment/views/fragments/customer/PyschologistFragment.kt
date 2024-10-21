package com.example.pyschologistappointment.views.fragments.customer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.pyschologistappointment.R
import com.example.pyschologistappointment.databinding.FragmentPyschologistBinding
import com.example.pyschologistappointment.utils.DESIGNATION
import com.example.pyschologistappointment.utils.JUNIOR
import com.example.pyschologistappointment.utils.Prefrences
import com.example.pyschologistappointment.utils.SENIOR


class PyschologistFragment : Fragment() , View.OnClickListener {

    private var _binding : FragmentPyschologistBinding? = null
    val binding get() =  _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentPyschologistBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        registerClick()
    }

    private fun registerClick(){
        binding?.btnJunior?.setOnClickListener(this)
        binding?.btnSenior?.setOnClickListener(this)
        binding?.btnBack?.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btnJunior->{
                Prefrences.setStringValue(DESIGNATION , JUNIOR)
                findNavController().navigate(PyschologistFragmentDirections.actionPyschologistFragmentToPsychologistListFragment())
            }
            R.id.btnSenior->{
                Prefrences.setStringValue(DESIGNATION , SENIOR)
                findNavController().navigate(PyschologistFragmentDirections.actionPyschologistFragmentToPsychologistListFragment())
            }
            R.id.btnBack->{
                findNavController().navigateUp()
            }
        }
    }

}