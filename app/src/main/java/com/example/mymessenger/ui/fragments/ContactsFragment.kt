package com.example.mymessenger.ui.fragments

import com.example.mymessenger.R
import com.example.mymessenger.utils.APP_ACTIVITY

class ContactsFragment : BaseFragment(R.layout.fragment_contacts) {

    override fun onResume() {
        super.onResume()
        APP_ACTIVITY.title = "Contacts"
    }
}