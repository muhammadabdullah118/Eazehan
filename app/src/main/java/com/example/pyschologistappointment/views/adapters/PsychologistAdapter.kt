package com.example.pyschologistappointment.views.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pyschologistappointment.R
import com.example.pyschologistappointment.models.User
import com.example.pyschologistappointment.listener.PsychologistListener


class PsychologistAdapter (
    val context : Context,
    val userList : List<User>,
    val listener : PsychologistListener
) : RecyclerView.Adapter<PsychologistAdapter.ViewHolder>() {

    class ViewHolder(val view : View): RecyclerView.ViewHolder(view){
        val image = view.findViewById<ImageView>(R.id.rvcImageUser)
        val name = view.findViewById<TextView>(R.id.rvcTvNameUser)
        val designation = view.findViewById<TextView>(R.id.rvcTvDesignationUser)
        val buttonNext = view.findViewById<CardView>(R.id.btnForward)
        val cardView = view.findViewById<CardView>(R.id.cvUser)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.doctor_view , parent , false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: PsychologistAdapter.ViewHolder, position: Int) {

        Glide.with(context)
            .load(userList[position].image)
            .placeholder(R.drawable.profile)
            .error(R.drawable.profile)
            .into(holder.image)

        holder.name.text = userList[position].fname
        holder.designation.text = userList[position].designation
        holder.cardView.setOnClickListener{
            listener.onPsychologistClick(userList[position])
        }
        holder.buttonNext.setOnClickListener{
            listener.onPsychologistClick(userList[position])
        }

    }

}
