package com.example.mymessenger.ui.fragments

import androidx.fragment.app.Fragment
import com.example.mymessenger.R
import com.example.mymessenger.utils.APP_ACTIVITY

class ChatsFragment : Fragment(R.layout.fragment_chats) {

    override fun onResume() {
        super.onResume()
        APP_ACTIVITY.title = "Chats"

    }

}