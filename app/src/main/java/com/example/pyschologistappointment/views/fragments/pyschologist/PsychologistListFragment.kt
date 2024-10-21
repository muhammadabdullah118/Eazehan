package com.example.pyschologistappointment.views.fragments.pyschologist

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pyschologistappointment.R
import com.example.pyschologistappointment.databinding.FragmentPsychologistListBinding
import com.example.pyschologistappointment.listener.PsychologistListener
import com.example.pyschologistappointment.models.User
import com.example.pyschologistappointment.utils.DESIGNATION
import com.example.pyschologistappointment.utils.Prefrences
import com.example.pyschologistappointment.viewmodels.PsychologistListVM
import com.example.pyschologistappointment.views.adapters.PsychologistAdapter


class PsychologistListFragment : Fragment() , PsychologistListener , View.OnClickListener{


    private var _binding : FragmentPsychologistListBinding? = null
    val binding get() = _binding
    val viewModel : PsychologistListVM by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentPsychologistListBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initialize()
        registerClicks()
    }

    private fun initialize(){

        binding?.loadingProgressBar?.visibility = View.VISIBLE
        binding?.loadingProgressBar?.playAnimation()

        val designation = Prefrences.getStringValue(DESIGNATION)
        viewModel.list = viewModel.fetchPsychologist(designation!!)

        if (viewModel.list.isEmpty()) {

            Handler(Looper.getMainLooper()).postDelayed(
                {
                    if (isAdded) {
                        binding?.rvPsychologistList?.layoutManager = LinearLayoutManager(requireContext())
                        binding?.rvPsychologistList?.adapter = PsychologistAdapter(requireContext(), viewModel.list, this)
                    }
                    binding?.loadingProgressBar?.visibility = View.GONE
                    binding?.loadingProgressBar?.cancelAnimation()
                }, 5000
            )
        }else{
            Toast.makeText(context , " No Data Available " , Toast.LENGTH_SHORT).show()
        }
    }

    private fun registerClicks(){
        binding?.btnBackPL?.setOnClickListener(this)
    }

    override fun onPsychologistClick(user: User) {
       findNavController().navigate(PsychologistListFragmentDirections.actionPsychologistListFragmentToPyschologistDetailFragment(user))
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btnBackPL->{
                findNavController().navigateUp()
            }
        }
    }


}