package com.example.pyschologistappointment.views.fragments.auth

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.pyschologistappointment.R
import com.example.pyschologistappointment.databinding.FragmentSignUpBinding
import com.example.pyschologistappointment.utils.AUTH
import com.example.pyschologistappointment.utils.Prefrences
import com.example.pyschologistappointment.utils.ROLE
import com.example.pyschologistappointment.utils.USER_TABLE
import com.example.pyschologistappointment.viewmodels.SignupVM


class SignUpFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentSignUpBinding? = null
    val binding get() = _binding
    val viewModel: SignupVM by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        registerClicks()
    }

    private fun registerClicks() {
        binding?.cbMale?.setOnClickListener(this)
        binding?.cbFemale?.setOnClickListener(this)
        binding?.btnSignUp?.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.cbMale -> {
                binding?.cbFemale?.isChecked = false
                binding?.tvGender?.text = "male"
            }

            R.id.cbFemale -> {
                binding?.cbMale?.isChecked = false
                binding?.tvGender?.text = "female"
            }

            R.id.btnSignUp -> {

                viewModel.firstName = binding?.etFname?.text.toString()
                viewModel.lastName = binding?.etLname?.text.toString()
                viewModel.phone = binding?.etPhone?.text.toString()
                viewModel.email = binding?.etEmail?.text.toString()
                viewModel.gender = binding?.tvGender?.text.toString()
                viewModel.role = Prefrences.getStringValue(ROLE).toString()
                viewModel.password = binding?.etPassword?.text.toString()
                viewModel.confirmPassword = binding?.etConfirmPassword?.text.toString()

                if (viewModel.isEmptyFields()) {
                    AUTH.createUserWithEmailAndPassword(viewModel.email, viewModel.password)
                        .addOnSuccessListener {
                            AUTH.currentUser?.sendEmailVerification()
                                ?.addOnSuccessListener {

                                    val userId = AUTH?.currentUser?.uid
                                    val user = viewModel.createUser()

                                    userId?.let {
                                        USER_TABLE.child(it).setValue(user)
                                            .addOnSuccessListener {

                                                binding?.loadingProgressBar?.visibility = View.VISIBLE
                                                binding?.loadingProgressBar?.playAnimation()

                                                findNavController().navigate(
                                                    SignUpFragmentDirections.actionSignUpFragmentToSignInFragment()
                                                )
                                                Toast.makeText(
                                                    context,
                                                    " Please Verify Email",
                                                    Toast.LENGTH_SHORT
                                                )
                                                    .show()

                                                Handler(Looper.getMainLooper()).postDelayed(
                                                    {
                                                        binding?.loadingProgressBar?.visibility = View.GONE
                                                        binding?.loadingProgressBar?.cancelAnimation()
                                                    }, 5000
                                                )

                                            }
                                            .addOnFailureListener {
                                                Toast.makeText(
                                                    context,
                                                    "Error! : Check Credentials",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }
                                    }
                                }
                                ?.addOnFailureListener {
                                    Toast.makeText(
                                        context,
                                        "Error Sending Verification link to email ",
                                        Toast.LENGTH_SHORT
                                    )
                                        .show()
                                }
                        }
                        .addOnFailureListener {
                            Toast.makeText(context, " Cannot Create a User", Toast.LENGTH_SHORT)
                                .show()
                        }
                } else {
                    viewModel.checkValues()
                    if (viewModel.firstNameError) {
                        binding?.etFname?.error = " Please Enter First Name"
                    }
                    if (viewModel.lastNameError) {
                        binding?.etLname?.error = " Please Enter Second Name"
                    }
                    if (viewModel.emailError) {
                        binding?.etEmail?.error = " Please Enter Email"
                    }
                    if (viewModel.phoneError) {
                        binding?.etPhone?.error = " Please Enter Phone Number"
                    }
                    if (viewModel.passwordError) {
                        binding?.etPassword?.error = " Please Enter Password"
                    }
                    if (viewModel.confirmPasswordError) {
                        binding?.etConfirmPassword?.error = "Please Enter Confirm Password"
                    }
                }

            }
        }
    }


}