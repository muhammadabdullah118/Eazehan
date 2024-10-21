package com.example.pyschologistappointment.views.fragments.customer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.pyschologistappointment.R
import com.example.pyschologistappointment.databinding.FragmentAiChatBinding


class AiChatFragment : Fragment() , View.OnClickListener {

    private  var _binding : FragmentAiChatBinding? = null
    val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAiChatBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        registerClicks()
    }

    private fun registerClicks(){
        binding?.btnBackAC?.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btnBackAC->{
                findNavController().navigateUp()
            }
        }
    }
}