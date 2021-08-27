package com.example.mymessenger.ui.fragments

import com.example.mymessenger.R
import com.example.mymessenger.utils.*
import kotlinx.android.synthetic.main.fragment_change_name.*

class ChangeNameFragment : BaseEditFragment(R.layout.fragment_change_name) {

    override fun onResume() {
        super.onResume()
        initFullnameList()
        APP_ACTIVITY.title = "Edit name"
    }

    private fun initFullnameList() {
        val fullnameList = USER.fullname.split(" ")
        if (fullnameList.size == 2) {
            settings_input_name.setText(fullnameList[0])
            settings_input_surname.setText(fullnameList[1])
        } else {
            settings_input_name.setText(fullnameList[0])
        }
    }


    override fun change() {
        val name = settings_input_name.text.toString()
        val surname = settings_input_surname.text.toString()
        if (name.isEmpty()) {
            showToast(getString(R.string.settings_toast_empty_name))
        } else {
            val fullname = "$name $surname"

            REF_DATABASE_ROOT.child(NODE_USERS).child(CURRENT_UID).child(CHILD_FULLNAME).setValue(fullname).addOnCompleteListener {
                if (it.isSuccessful) {
                    showToast(getString(R.string.settings_toast_change_name_success))
                    USER.fullname = fullname
                    APP_ACTIVITY.mDrawer.updateHeader()
                    fragmentManager?.popBackStack()
                }
            }

        }
    }
}