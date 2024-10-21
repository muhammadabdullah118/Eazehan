package com.example.pyschologistappointment.viewmodels

import androidx.lifecycle.ViewModel
import com.example.pyschologistappointment.models.Appointments
import com.example.pyschologistappointment.models.ChatCommunity
import com.example.pyschologistappointment.models.User
import com.example.pyschologistappointment.utils.APPOINTMENT_TABLE
import com.example.pyschologistappointment.utils.AppUtils
import com.example.pyschologistappointment.utils.COMMUNITYCHAT_TABLE
import com.example.pyschologistappointment.utils.PENDING
import com.example.pyschologistappointment.utils.START
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue

class CommunityChatVM : ViewModel() {

    var userid = 0
    var userName = ""
    var message = ""
    var timeStamp = ""

    var list = listOf <ChatCommunity>()

    fun fetchChat() : List<ChatCommunity>{

        val chatList = mutableListOf<ChatCommunity>()

        COMMUNITYCHAT_TABLE.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                chatList.clear()
                for (chats in snapshot.children) {
                    val chat = chats.getValue<ChatCommunity>()
                    if (chat != null) {
                        chatList.add(chat)
                        }
                    }
                }


            override fun onCancelled(error: DatabaseError) {
//                Toast.makeText(context , "Failed to load data.", Toast.LENGTH_SHORT).show()
            }

        })

        return  chatList
    }

//    private fun loadMessages() {
//        COMMUNITYCHAT_TABLE.addChildEventListener(object : ChildEventListener {
//            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
//                val message = snapshot.getValue(Message::class.java)
//                message?.let {
//                    messagesList.add(it)
//                    messageAdapter.notifyItemInserted(messagesList.size - 1)
//                }
//            }
//
//            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}
//            override fun onChildRemoved(snapshot: DataSnapshot) {}
//            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}
//            override fun onCancelled(error: DatabaseError) {
//                Toast.makeText(this@CommunityChatActivity, "Failed to load messages", Toast.LENGTH_SHORT).show()
//            }
//        })
//    }


    fun createChat( ) :ChatCommunity  {
        val chat = ChatCommunity(
            userId = userid,
            userName = userName,
            message = message,
            timestamp = timeStamp
        )
        return chat
    }

}