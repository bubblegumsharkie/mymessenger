package com.example.mymessenger.ui.fragments

import com.example.mymessenger.R
import com.example.mymessenger.utils.*
import kotlinx.android.synthetic.main.fragment_change_bio.*

class ChangeBioFragment : BaseEditFragment(R.layout.fragment_change_bio) {

    override fun onResume() {
        super.onResume()
        settings_input_bio.setText(USER.bio)
        APP_ACTIVITY.title = "Edit bio"
    }

    override fun change() {
        super.change()
        val newBio = settings_input_bio.text.toString()
        REF_DATABASE_ROOT.child(NODE_USERS).child(CURRENT_UID).child(CHILD_BIO).setValue(newBio).addOnCompleteListener {
            if (it.isSuccessful) {
                showToast("Update was successful")
                USER.bio = newBio
                fragmentManager?.popBackStack()
            }
        }
    }
}