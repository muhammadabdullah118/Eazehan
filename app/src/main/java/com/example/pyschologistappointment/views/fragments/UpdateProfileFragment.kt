package com.example.pyschologistappointment.views.fragments

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.pyschologistappointment.R
import com.example.pyschologistappointment.databinding.FragmentUpdateProfileBinding
import com.example.pyschologistappointment.models.User
import com.example.pyschologistappointment.utils.APPOINTMENT_SCREEN
import com.example.pyschologistappointment.utils.AUTH
import com.example.pyschologistappointment.utils.AppUtils
import com.example.pyschologistappointment.utils.PATIENT
import com.example.pyschologistappointment.utils.PSYCHOLOGIST
import com.example.pyschologistappointment.utils.Prefrences
import com.example.pyschologistappointment.utils.ROLE
import com.example.pyschologistappointment.utils.USER_TABLE
import com.example.pyschologistappointment.viewmodels.UpdateProfileVM
import com.google.firebase.storage.FirebaseStorage


class UpdateProfileFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentUpdateProfileBinding? = null
    val binding get() = _binding
    val viewModel: UpdateProfileVM by viewModels()
    private var pickImage: ActivityResultLauncher<String>? = null
    var uri: Uri? = null
    var storageRef = FirebaseStorage.getInstance().getReference("Images")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentUpdateProfileBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initialize()
        registerClicks()
    }

    private fun initialize() {
        pickImage = registerForActivityResult(ActivityResultContracts.GetContent()) {
            binding?.imgProfileUpdate?.setImageURI(it)
            if (it != null) {
                uri = it
            }
        }

        if (AppUtils.getLoginResponse()?.role == PATIENT) {
            binding?.aboutField?.visibility = View.GONE
            binding?.tvDesignation?.visibility = View.GONE
            binding?.cbJunior?.visibility = View.GONE
            binding?.cbSenior?.visibility = View.GONE
        }

        Glide.with(this)
            .load(AppUtils.getLoginResponse()?.image)
            .placeholder(R.drawable.profile)
            .error(R.drawable.profile)
            .into(binding?.imgProfileUpdate!!)

        binding?.etFnameUP?.setText(AppUtils.getLoginResponse()?.fname)
        binding?.etLnameUP?.setText(AppUtils.getLoginResponse()?.lname)
        binding?.etPhoneUP?.setText(AppUtils.getLoginResponse()?.phone)
        binding?.etAbout?.setText(AppUtils.getLoginResponse()?.description)
        binding?.tvDesignation?.text = AppUtils.getLoginResponse()?.designation

        binding?.imgEditPicture?.setOnClickListener(this)
        binding?.btnUpdate?.setOnClickListener(this)

    }

    private fun registerClicks() {
        binding?.cbSenior?.setOnClickListener(this)
        binding?.cbJunior?.setOnClickListener(this)
        binding?.btnUpdate?.setOnClickListener(this)
        binding?.btnBackUP?.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.cbJunior -> {
                binding?.cbSenior?.isChecked = false
                binding?.tvDesignation?.text = "junior"
            }

            R.id.cbSenior -> {
                binding?.cbJunior?.isChecked = false
                binding?.tvDesignation?.text = "senior"
            }

            R.id.btnBackUP -> {
                findNavController().navigateUp()
            }

            R.id.imgEditPicture -> {
                pickImage!!.launch("image/*")
            }

            R.id.btnUpdate -> {
                viewModel.firstName = binding?.etFnameUP?.text.toString()
                viewModel.lastName = binding?.etLnameUP?.text.toString()
                viewModel.phone = binding?.etPhoneUP?.text.toString()
                viewModel.description = binding?.etAbout?.text.toString()
                viewModel.designation = binding?.tvDesignation?.text.toString()


                if (uri != null ) {
                    when (AppUtils.getLoginResponse()?.role) {
                        PSYCHOLOGIST -> {
                            if (viewModel.isEmptyFields()) {
                                uri?.let {
                                    storageRef.child(AUTH.currentUser!!.uid).putFile(it)
                                        .addOnSuccessListener { task ->
                                            task.metadata?.reference?.downloadUrl
                                                ?.addOnSuccessListener { url ->
                                                    val imgUrl = url.toString()
                                                    USER_TABLE.child(AUTH.currentUser!!.uid)
                                                        .setValue(viewModel.createUser(imgUrl))
                                                        .addOnSuccessListener {
                                                            Toast.makeText(
                                                                context,
                                                                "User updated successfully",
                                                                Toast.LENGTH_SHORT
                                                            ).show()
                                                            findNavController().navigate(
                                                                UpdateProfileFragmentDirections.actionUpdateProfileFragmentToProfileFragment()
                                                            )
                                                        }
                                                        .addOnFailureListener {
                                                            Toast.makeText(
                                                                context,
                                                                "Failed to update user",
                                                                Toast.LENGTH_LONG
                                                            ).show()
                                                        }
                                                }
                                        }
                                }
                            } else {
                                Toast.makeText(context, "Fill All fields", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }

                        PATIENT -> {
                            if (viewModel.isCustomerEmptyFields()) {
                                uri?.let {
                                    storageRef.child(AUTH.currentUser!!.uid).putFile(it)
                                        .addOnSuccessListener { task ->
                                            task.metadata?.reference?.downloadUrl
                                                ?.addOnSuccessListener { url ->
                                                    val imgUrl = url.toString()
                                                    USER_TABLE.child(AUTH.currentUser!!.uid)
                                                        .setValue(viewModel.createUser(imgUrl))
                                                        .addOnSuccessListener {
                                                            Toast.makeText(
                                                                context,
                                                                "User updated successfully",
                                                                Toast.LENGTH_SHORT
                                                            ).show()
                                                            findNavController().navigate(
                                                                UpdateProfileFragmentDirections.actionUpdateProfileFragmentToProfileFragment()
                                                            )
                                                        }
                                                        .addOnFailureListener {
                                                            Toast.makeText(
                                                                context,
                                                                "Failed to update user",
                                                                Toast.LENGTH_LONG
                                                            ).show()
                                                        }
                                                }
                                        }
                                }
                            } else {
                                Toast.makeText(context, "Fill All fields", Toast.LENGTH_SHORT)
                                    .show()
                            }

                        }
                    }
                } else {
                    Toast.makeText(context, "Please Insert Image", Toast.LENGTH_SHORT).show()
                    viewModel.checkValues()
                    if (viewModel.firstNameError) {
                        binding?.etFnameUP?.error = " Please Enter First Name"
                    }
                    if (viewModel.lastNameError) {
                        binding?.etLnameUP?.error = " Please Enter Second Name"
                    }
                    if (viewModel.phoneError) {
                        binding?.etPhoneUP?.error = " Please Enter Phone Number"
                    }
                    if (AppUtils.getLoginResponse()?.role == PSYCHOLOGIST) {
                        if (viewModel.descriptionError) {
                            binding?.etAbout?.error = " Please Enter Description"
                        }
                        if (viewModel.designationError) {
                            binding?.tvDesignation?.error = " Please Choose Designation"
                        }
                    }
                }
            }
        }
    }

}