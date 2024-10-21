package com.example.pyschologistappointment.views.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.pyschologistappointment.R
import com.example.pyschologistappointment.models.Advertisements

class AdvertisementAdapter (
    val context : Context,
    val listAdvertisement : List<Advertisements>
) : RecyclerView.Adapter<AdvertisementAdapter.ViewHolder>(){

    class ViewHolder(view : View) : RecyclerView.ViewHolder(view){
        val imageAdvertisement = view.findViewById<ImageView>(R.id.imgAdvertisement)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.advertisement_views, parent , false)
        return  ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return  listAdvertisement.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.imageAdvertisement
    }

}