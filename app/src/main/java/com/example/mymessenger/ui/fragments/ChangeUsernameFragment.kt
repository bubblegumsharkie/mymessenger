package com.example.mymessenger.ui.fragments

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import com.example.mymessenger.MainActivity
import com.example.mymessenger.R
import com.example.mymessenger.utils.*
import kotlinx.android.synthetic.main.fragment_change_username.*
import java.util.*


class ChangeUsernameFragment : BaseFragment(R.layout.fragment_change_username) {

    lateinit var mNewUsername: String

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        (activity as MainActivity).menuInflater.inflate(R.menu.settings_confirm_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.settings_confirm_changes -> changeUsername()
        }

        return true
    }

    private fun changeUsername() {
        mNewUsername = settings_input_username.text.toString().lowercase(Locale.getDefault())
        if (mNewUsername.isEmpty()) {
            showToast("Enter your username!")
        } else {
            REF_DATABASE_ROOT.child(NODE_USERNAMES)
                .addListenerForSingleValueEvent(AppValueEventListener { sameNameCheck ->
                    if (sameNameCheck.hasChild(mNewUsername)) {
                        showToast("The username $mNewUsername is already in use")
                    } else {
                        REF_DATABASE_ROOT.child(NODE_USERNAMES).child(mNewUsername).setValue(UID)
                            .addOnCompleteListener {
                                if (it.isSuccessful) {
                                    updateCurrentUsername()
                                }
                            }
                    }
                })
        }
    }

    private fun updateCurrentUsername() {
        REF_DATABASE_ROOT.child(NODE_USERS).child(UID).child(CHILD_USERNAME).setValue(mNewUsername)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    showToast("Username successfully changed")
                    deletePreviousUsername()
                } else {
                    showToast(it.exception?.message.toString())
                }
            }
    }

    private fun deletePreviousUsername() {
        REF_DATABASE_ROOT.child(NODE_USERNAMES).child(USER.username).removeValue()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    showToast("Username successfully changed")
                    fragmentManager?.popBackStack()
                    USER.username = mNewUsername
                } else {
                    showToast(it.exception?.message.toString())
                }
            }
    }

    override fun onResume() {
        super.onResume()
        setHasOptionsMenu(true)
        settings_input_username.setText(USER.username)
    }

}