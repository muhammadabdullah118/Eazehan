package com.example.pyschologistappointment.views.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pyschologistappointment.R
import com.example.pyschologistappointment.listener.AppointmentListener
import com.example.pyschologistappointment.models.Appointments
import com.example.pyschologistappointment.utils.APPOINTMENT
import com.example.pyschologistappointment.utils.APPOINTMENT_SCREEN
import com.example.pyschologistappointment.utils.CANCEL
import com.example.pyschologistappointment.utils.COMPLETE
import com.example.pyschologistappointment.utils.PENDING
import com.example.pyschologistappointment.utils.Prefrences
import com.example.pyschologistappointment.utils.START

class AppointmentAdapter (
    val context : Context,
    var appointmentList : List<Appointments>,
    val listener : AppointmentListener
) : RecyclerView.Adapter<AppointmentAdapter.ViewHolder>() {

    class ViewHolder( view : View) : RecyclerView.ViewHolder(view){
        val tvDate = view.findViewById<TextView>(R.id.tvDateAppointment)
        val tvTime = view.findViewById<TextView>(R.id.tvTimeAppointment)
        val tvDoctorName = view.findViewById<TextView>(R.id.tvAppointmentDoctor)
        val imageAppointmentStatus = view.findViewById<ImageView>(R.id.imgAppointmentStatus)
        val tvCustomerId = view.findViewById<TextView>(R.id.txCustomerId)
        val imageProfileAppointment = view.findViewById<ImageView>(R.id.imgAppointmentProfile)
        val cvAppointment = view.findViewById<CardView>(R.id.cvAppointment)
        val btnNext = view.findViewById<ImageView>(R.id.imgNextArrow)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.appointment_view, parent , false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return appointmentList.size
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val status = appointmentList[position].status

        Glide.with(context)
            .load(appointmentList[position].psychologistImage)
            .placeholder(R.drawable.profile)
            .error(R.drawable.profile)
            .into(holder.imageProfileAppointment )

        holder.tvDoctorName.text = appointmentList[position].psychologistName
        holder.tvDate.text = appointmentList[position].date
        holder.tvTime.text = appointmentList[position].time
        holder.tvCustomerId.text = appointmentList[position].customerId.toString()

        when(status){
            PENDING->{
                holder.imageAppointmentStatus.setImageResource(R.drawable.pending)
            }
            START->{
                holder.imageAppointmentStatus.setImageResource(R.drawable.calltime)
            }
            COMPLETE->{
                holder.imageAppointmentStatus.setImageResource(R.drawable.confirm)
            }
            CANCEL->{
                holder.imageAppointmentStatus.setImageResource(R.drawable.cancel)
            }
        }

        if ( Prefrences.getStringValue(APPOINTMENT_SCREEN) == APPOINTMENT) {
            holder.cvAppointment.setOnClickListener {
                listener.onAppointmentClick(appointmentList[position])
            }

            holder.btnNext.setOnClickListener {
                listener.onAppointmentClick(appointmentList[position])
            }
        }else{
            holder.btnNext.visibility = View.GONE
        }

    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateSetAppointmentList(newItemList: List<Appointments>) {
        appointmentList = newItemList
        notifyDataSetChanged()
    }
}