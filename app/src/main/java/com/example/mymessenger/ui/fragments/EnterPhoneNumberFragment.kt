package com.example.mymessenger.ui.fragments

import com.example.mymessenger.R
import com.example.mymessenger.utils.replaceFragment
import com.example.mymessenger.utils.showToast
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
            showToast(getString(R.string.register_toast_enter_your_number))
            println(getString(R.string.register_toast_enter_your_number))
        } else {
            replaceFragment(EnterCodeFragment())
        }
    }

}


