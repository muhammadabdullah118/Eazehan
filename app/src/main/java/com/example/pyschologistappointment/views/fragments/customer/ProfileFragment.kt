package com.example.pyschologistappointment.views.fragments.customer

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.pyschologistappointment.R
import com.example.pyschologistappointment.databinding.FragmentProfileBinding
import com.example.pyschologistappointment.utils.APPOINTMENT
import com.example.pyschologistappointment.utils.APPOINTMENT_SCREEN
import com.example.pyschologistappointment.utils.AUTH
import com.example.pyschologistappointment.utils.AppUtils
import com.example.pyschologistappointment.utils.HISTORY
import com.example.pyschologistappointment.utils.PSYCHOLOGIST
import com.example.pyschologistappointment.utils.Prefrences
import com.example.pyschologistappointment.utils.ROLE
import com.example.pyschologistappointment.utils.USER_TABLE
import com.example.pyschologistappointment.viewmodels.ProfileVM
import com.example.pyschologistappointment.views.fragments.UpdateProfileFragmentDirections


class ProfileFragment : Fragment() , View.OnClickListener{

    private var _binding : FragmentProfileBinding?  = null
    val binding get() = _binding
    var alertDialogue : AlertDialog?= null
    val viewModel : ProfileVM  by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding =  FragmentProfileBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initialize()
        registerClicks()
    }

    private fun initialize(){

        val role = Prefrences.getStringValue(ROLE)
        if (role == PSYCHOLOGIST){
            if (AppUtils.getLoginResponse()?.activeStatus == false) {
                showDialog()
            }
            binding?.bottomNavigationBar?.btnHomeNavigation?.visibility = View.GONE
            binding?.bottomNavigationBar?.btnHistoryNavigation?.visibility = View.GONE
        }

        Glide.with(this)
            .load(AppUtils.getLoginResponse()?.image)
            .placeholder(R.drawable.profile)
            .error(R.drawable.profile)
            .into(binding?.imgProfile!!)
        val firstName = AppUtils.getLoginResponse()?.fname
        val lastName = AppUtils.getLoginResponse()?.lname
        val fullName = firstName + lastName

        binding?.txtProfileName?.text = AppUtils.getLoginResponse()?.fname
        binding?.nameText?.text =  fullName
        binding?.emailText?.text = AppUtils.getLoginResponse()?.email
        binding?.phoneText?.text = AppUtils.getLoginResponse()?.phone

        binding?.bottomNavigationBar?.btnProfileNavigation?.setColorFilter(resources.getColor(R.color.teal_200))
    }

    private fun registerClicks() {
        binding?.bottomNavigationBar?.btnSettingNavigation?.setOnClickListener(this)
        binding?.bottomNavigationBar?.btnHomeNavigation?.setOnClickListener(this)
        binding?.bottomNavigationBar?.btnHistoryNavigation?.setOnClickListener(this)
        binding?.bottomNavigationBar?.btnAppointmentNavigation?.setOnClickListener(this)
        binding?.btnDeActivate?.setOnClickListener(this)
        binding?.btnEdit?.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnEdit->{
             findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToUpdateProfileFragment())
            }
            R.id.btnDeActivate->{
                USER_TABLE.child(AUTH.currentUser!!.uid)
                    .setValue(viewModel.createUser())
                    .addOnSuccessListener {
                        Toast.makeText(
                            context,
                            "Account Status InActive",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    .addOnFailureListener {
                        Toast.makeText(
                            context,
                            "Failed to update account status",
                            Toast.LENGTH_LONG
                        ).show()
                    }
            }
            R.id.btnSettingNavigation -> {
                findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToSettingFragment())
            }

            R.id.btnHomeNavigation -> {
                findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToHomeFragment())
            }

            R.id.btnHistoryNavigation -> {
                Prefrences.setStringValue(APPOINTMENT_SCREEN, HISTORY)
                findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToAppointmentHistoryFragment())
            }

            R.id.btnAppointmentNavigation -> {
                Prefrences.setStringValue(APPOINTMENT_SCREEN, APPOINTMENT)
                findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToAppointmentFragment())
            }
        }
    }

    private fun showDialog() {
        val alertDialogBuilder = AlertDialog.Builder(requireContext())

        // Inflate the custom layout for the dialog
        val dialogView = layoutInflater.inflate(R.layout.dialog, null)
        alertDialogBuilder.setCancelable(false)
        alertDialogBuilder.setView(dialogView)

        // Set buttons on click
        val btnUpdate = dialogView.findViewById<Button>(R.id.btnUpdateDialog)
        val btnClose = dialogView.findViewById<ImageView>(R.id.btnClose)

        // Create the dialog and store it in alertDialogue variable
        alertDialogue = alertDialogBuilder.create()
        alertDialogue?.setCanceledOnTouchOutside(false)
        alertDialogue?.show()

        // Button click listeners
        btnUpdate.setOnClickListener {
            alertDialogue?.dismiss()
            findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToUpdateProfileFragment())
        }

        btnClose.setOnClickListener {
            // Safely dismiss the dialog
            alertDialogue?.dismiss()
        }
    }

}