package com.example.pyschologistappointment.views.fragments.customer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pyschologistappointment.R
import com.example.pyschologistappointment.databinding.FragmentMeetingBinding

class MeetingFragment : Fragment() {

    private var _binding : FragmentMeetingBinding ? = null
    val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMeetingBinding.inflate(inflater, container, false)
        return binding?.root
    }



}