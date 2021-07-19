package com.example.mymessenger.ui.fragments

import android.widget.Toast
import com.example.mymessenger.R
import kotlinx.android.synthetic.main.fragment_enter_phone_number.*


class EnterPhoneNumberFragment : BaseFragment(R.layout.fragment_enter_phone_number) {

    override fun onStart() {
        super.onStart()
        register_btn_fab_next.setOnClickListener {
            sendCode()
        }
    }

    private fun sendCode() {
        if (register_edit_phone.text.toString().isEmpty()) {
            Toast.makeText(
                activity, getString(R.string.register_toast_enter_your_number), Toast.LENGTH_LONG
            ).show()
        } else {
            //check if this works like deprecated fragmentManager \/
//            fragmentManager?.beginTransaction()
            parentFragmentManager.beginTransaction()
                .replace(R.id.register_data_container, EnterCodeFragment())
                .addToBackStack(null).commit()
        }
    }

}


