package com.example.pyschologistappointment.views.fragments.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.pyschologistappointment.R
import com.example.pyschologistappointment.databinding.FragmentSignOutBinding
import com.example.pyschologistappointment.utils.AUTH
import com.example.pyschologistappointment.utils.FALSE
import com.example.pyschologistappointment.utils.ISLOGEDIN
import com.example.pyschologistappointment.utils.LOGIN_RESPONSE
import com.example.pyschologistappointment.utils.Prefrences
import com.example.pyschologistappointment.utils.TRUE
import com.google.gson.Gson


class SignOutFragment : Fragment() , View.OnClickListener {


    private var _binding  : FragmentSignOutBinding? = null
    val binding get() =  _binding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding =FragmentSignOutBinding.inflate(inflater, container, false)
        return  binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        registerClicks()
    }

    private  fun registerClicks(){
        binding?.btnCancel?.setOnClickListener(this)
        binding?.btnLogout?.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btnCancel->{
                findNavController().navigateUp()
            }
            R.id.btnLogout->{
                AUTH.signOut()
                Prefrences.setStringValue(LOGIN_RESPONSE, "")
                Prefrences.setStringValue(ISLOGEDIN ,  FALSE)
                findNavController().navigate(SignOutFragmentDirections.actionSignOutFragmentToSplashFragment())
            }
        }
    }

}