package com.example.pyschologistappointment.views.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pyschologistappointment.R
import com.example.pyschologistappointment.models.Appointments
import com.example.pyschologistappointment.models.ChatCommunity
import com.example.pyschologistappointment.utils.AppUtils

class AiChatAdapter(
    val context: Context,
    var listChat: List<ChatCommunity>
) : RecyclerView.Adapter<AiChatAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtName = view.findViewById<TextView>(R.id.recieveNameTextView)
        val txtMessage = view.findViewById<TextView>(R.id.messageTextViewRecieve)
        val txtTimeStap = view.findViewById<TextView>(R.id.timestampTextViewRecieve)
        val viewChat = view.findViewById<LinearLayout>(R.id.rvChatCommunity)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewRecieve = LayoutInflater.from(context).inflate(R.layout.chat_view, parent, false)
        return ViewHolder(viewRecieve)
//        val viewSend = LayoutInflater.from(context).inflate(R.layout.chat_view_send, parent, false)
//
//        if (AppUtils.getLoginResponse()?.id ==) {
//            return ViewHolder(viewSend)
//        } else {
//            return ViewHolder(viewRecieve)
//        }
    }

    override fun getItemCount(): Int {
        return listChat.size
    }

//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//
//        if (listChat[position].userId == AppUtils.getLoginResponse()?.id) {
//            holder.viewChat.gravity = Gravity.END
//            holder.txtMessage.text = listChat[position].message
//            holder.txtName.text = listChat[position].userName
//            holder.txtTimeStap.text = listChat[position].timestamp
//        } else {
//            holder.txtMessage.text = listChat[position].message
//            holder.txtName.text = listChat[position].userName
//            holder.txtTimeStap.text = listChat[position].timestamp
//        }
//    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val chatMessage = listChat[position]

        if (chatMessage.userId == AppUtils.getLoginResponse()?.id) {
            // This is the sender's message
            holder.viewChat.gravity = Gravity.END
            holder.txtMessage.text = chatMessage.message
            holder.txtName.text = "You"
            holder.txtTimeStap.text = chatMessage.timestamp
            holder.txtMessage.setBackgroundResource(R.drawable.sender_message_background)
        } else {
            // This is the receiver's message
            holder.viewChat.gravity = Gravity.START
            holder.txtMessage.text = chatMessage.message
            holder.txtName.text = chatMessage.userName
            holder.txtTimeStap.text = chatMessage.timestamp
            holder.txtMessage.setBackgroundResource(R.drawable.reciever_message_background)
        }
    }



    @SuppressLint("NotifyDataSetChanged")
    fun updateSetAppointmentList(newChatList: List<ChatCommunity>) {
        listChat = newChatList
        notifyDataSetChanged()
    }

}