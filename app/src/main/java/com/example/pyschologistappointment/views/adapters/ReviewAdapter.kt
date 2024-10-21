package com.example.pyschologistappointment.views.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pyschologistappointment.R
import com.example.pyschologistappointment.models.Appointments

class ReviewAdapter (
    val context: Context,
    val reviewList : List<Appointments>
) : RecyclerView.Adapter<ReviewAdapter.ViewHolder>() {

    class ViewHolder(view : View) : RecyclerView.ViewHolder(view){
        val userName = view.findViewById<TextView>(R.id.tvUserName)
        val dateSend = view.findViewById<TextView>(R.id.tvDate)
        val feedback = view.findViewById<TextView>(R.id.tvFeedBack)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.review_view , parent ,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return reviewList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.userName.text = reviewList[position].customerId.toString()
        holder.dateSend.text = reviewList[position].date
        holder.feedback.text = reviewList[position].feedback
    }
}