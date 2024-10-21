package com.example.pyschologistappointment.views.fragments.customer

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pyschologistappointment.R
import com.example.pyschologistappointment.databinding.FragmentCommunityBinding
import com.example.pyschologistappointment.utils.AppUtils
import com.example.pyschologistappointment.utils.COMMUNITYCHAT_TABLE
import com.example.pyschologistappointment.utils.COMPLETE
import com.example.pyschologistappointment.viewmodels.CommunityChatVM
import com.example.pyschologistappointment.views.adapters.AiChatAdapter
import com.example.pyschologistappointment.views.adapters.AppointmentAdapter
import java.util.Calendar


class CommunityFragment : Fragment() , View.OnClickListener{

    private var _binding : FragmentCommunityBinding ? = null
    val binding get() =  _binding
    val viewModel : CommunityChatVM by viewModels()
    val calendar = Calendar.getInstance()
    val currentHour = calendar.get(Calendar.HOUR_OF_DAY) // 24-hour format
    val currentMinute = calendar.get(Calendar.MINUTE)
    val currentTime = String.format("%02d:%02d", currentHour, currentMinute)
    var adapter : AiChatAdapter ? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding =  FragmentCommunityBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initialize()
        registerClicks()
    }

    private fun initialize(){

        viewModel.list = viewModel.fetchChat()

        if (viewModel.list.isEmpty()) {

            binding?.loadingProgressBar?.visibility = View.VISIBLE
            binding?.loadingProgressBar?.playAnimation()

            Handler(Looper.getMainLooper()).postDelayed(
                {
                    if (isAdded) {
                        binding?.rvCommunityChat?.layoutManager =
                            LinearLayoutManager(requireContext())
                        adapter = AiChatAdapter(requireContext() , viewModel.list)
                        binding?.rvCommunityChat?.adapter = adapter
                    }

                    binding?.loadingProgressBar?.visibility = View.GONE
                    binding?.loadingProgressBar?.cancelAnimation()

                }, 5000
            )
        } else {
            Toast.makeText(context, " No  ", Toast.LENGTH_SHORT).show()
        }

    }

    private fun registerClicks(){
        binding?.imgSendMessage?.setOnClickListener(this)
        binding?.btnBackCC?.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.imgSendMessage->{

                viewModel.userid = AppUtils.getLoginResponse()!!.id
                viewModel.userName = AppUtils.getLoginResponse()!!.fname
                viewModel.message = binding?.etCommunityChat?.text.toString()
                viewModel.timeStamp = currentTime

                if (binding?.etCommunityChat?.text!!.isNotEmpty()){

                    binding?.loadingProgressBar?.visibility = View.VISIBLE
                    binding?.loadingProgressBar?.playAnimation()

                    COMMUNITYCHAT_TABLE.push().setValue(viewModel.createChat())
                        .addOnSuccessListener {
                            binding?.etCommunityChat!!.text.clear()
                            adapter?.updateSetAppointmentList(viewModel.list)
                        }
                        .addOnFailureListener {
                            Toast.makeText(context , "Can not send message" , Toast.LENGTH_SHORT).show()
                        }
                    Handler(Looper.getMainLooper()).postDelayed(
                        {
                            binding?.loadingProgressBar?.visibility = View.GONE
                            binding?.loadingProgressBar?.cancelAnimation()
                        }, 5000
                    )
                }
                else{
                    Toast.makeText(context , "Please Enter Any Text" , Toast.LENGTH_SHORT).show()
                }
            }
            R.id.btnBackCC->{
                findNavController().navigateUp()
            }
        }

    }


}