package com.example.pyschologistappointment.views.fragments.auth

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.pyschologistappointment.R
import com.example.pyschologistappointment.databinding.FragmentSignInBinding
import com.example.pyschologistappointment.models.User
import com.example.pyschologistappointment.utils.AUTH
import com.example.pyschologistappointment.utils.AppUtils
import com.example.pyschologistappointment.utils.ISLOGEDIN
import com.example.pyschologistappointment.utils.LOGIN_RESPONSE
import com.example.pyschologistappointment.utils.PATIENT
import com.example.pyschologistappointment.utils.PSYCHOLOGIST
import com.example.pyschologistappointment.utils.Prefrences
import com.example.pyschologistappointment.utils.ROLE
import com.example.pyschologistappointment.utils.TRUE
import com.example.pyschologistappointment.viewmodels.LoginVM
import com.google.firebase.database.FirebaseDatabase
import com.google.gson.Gson


class SignInFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentSignInBinding? = null
    val binding get() = _binding
    val viewModel: LoginVM by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        registerClicks()
    }


    private fun registerClicks() {
        binding?.btnLogin?.setOnClickListener(this)
        binding?.btnCreate?.setOnClickListener(this)
        binding?.btnEye?.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnLogin -> {

                viewModel.email = binding?.etEmailL?.text.toString()
                viewModel.password = binding?.etPasswordL?.text.toString()

                if (viewModel.checkInput()) {

                    binding?.loadingProgressBar?.visibility = View.VISIBLE
                    binding?.loadingProgressBar?.playAnimation()

                    AUTH.signInWithEmailAndPassword(viewModel.email, viewModel.password)
                        .addOnSuccessListener {

                            val verification = AUTH.currentUser?.isEmailVerified

                            if (verification == true) {


                                val userId = AUTH.currentUser?.uid
                                val database =
                                    FirebaseDatabase.getInstance().getReference("user_table")

                                userId?.let { id ->
                                    database.child(id).get().addOnSuccessListener { snapshot ->

                                        val user = snapshot.getValue(User::class.java)
                                        val userRole = user?.role
                                        Prefrences.setStringValue(LOGIN_RESPONSE, Gson().toJson(user))

                                        if (userRole == Prefrences.getStringValue(ROLE)) {



                                            Prefrences.setBooleanValue(ISLOGEDIN , true)
                                            when (userRole) {

                                                PATIENT -> {
                                                    findNavController().navigate(
                                                        SignInFragmentDirections.actionSignInFragmentToCustomerDashBoardFragment()
                                                    )
                                                }
                                                PSYCHOLOGIST -> {

                                                    findNavController().navigate(
                                                        SignInFragmentDirections.actionSignInFragmentToPyschologistDashBoardFragment()
                                                    )
                                                }
                                            }

                                            Handler(Looper.getMainLooper()).postDelayed(
                                                {
                                                    binding?.loadingProgressBar?.visibility = View.GONE
                                                    binding?.loadingProgressBar?.cancelAnimation()
                                                }, 5000
                                            )

                                        } else {
                                            Handler(Looper.getMainLooper()).postDelayed(
                                                {
                                                    binding?.loadingProgressBar?.visibility = View.GONE
                                                    binding?.loadingProgressBar?.cancelAnimation()
                                                }, 2000
                                            )
                                            Toast.makeText(
                                                context,
                                                "User status mismatch",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                    }
                                }
                            } else {

                                Handler(Looper.getMainLooper()).postDelayed(
                                    {
                                        binding?.loadingProgressBar?.visibility = View.GONE
                                        binding?.loadingProgressBar?.cancelAnimation()
                                    }, 2000
                                )

                                binding?.loadingProgressBar?.visibility = View.GONE
                                binding?.loadingProgressBar?.cancelAnimation()
                                Toast.makeText(context, "Please Verify Email", Toast.LENGTH_SHORT)
                                    .show()
                            }

                        }
                        .addOnFailureListener {
                            Toast.makeText(context, " ${it.message}", Toast.LENGTH_SHORT).show()
                        }
                } else {
                    viewModel.checkValues()
                    if (viewModel.emailError) {
                        binding?.etEmailL?.error = "Please enter valid Email"
                    }
                    if (viewModel.passwordError) {
                        binding?.etPasswordL?.error = "Please enter valid Password"
                    }
                }
            }

            R.id.btnCreate -> {
                if (Prefrences.getStringValue(ROLE) == PATIENT) {
                    Prefrences.setStringValue(ROLE, PATIENT)
                    findNavController().navigate(SignInFragmentDirections.actionSignInFragmentToSignUpFragment())
                } else {
                    Prefrences.setStringValue(ROLE, PSYCHOLOGIST)
                    findNavController().navigate(SignInFragmentDirections.actionSignInFragmentToSignUpFragment())
                }
            }
//            R.id.btnEye->{
//                binding?.etPasswordL?.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
//            }
        }
    }
}