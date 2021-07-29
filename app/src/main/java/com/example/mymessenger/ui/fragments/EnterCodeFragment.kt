package com.example.mymessenger.ui.fragments

import com.example.mymessenger.MainActivity
import com.example.mymessenger.R
import com.example.mymessenger.activities.RegisterActivity
import com.example.mymessenger.utils.AUTH
import com.example.mymessenger.utils.AppTextWatcher
import com.example.mymessenger.utils.replaceActivity
import com.example.mymessenger.utils.showToast
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.android.synthetic.main.fragment_enter_code.*

class EnterCodeFragment(val mPhoneNumber: String, val id: String) :
    BaseFragment(R.layout.fragment_enter_code) {


    override fun onStart() {
        super.onStart()
        (activity as RegisterActivity).title = mPhoneNumber
        register_edit_code.addTextChangedListener(AppTextWatcher {
            val string = register_edit_code.text.toString()
            if (string.length == 6) {
                verifyCode()
            }
        })
    }

    private fun verifyCode() {
        val code = register_edit_code.text.toString()
        val cred = PhoneAuthProvider.getCredential(id, code)
        AUTH.signInWithCredential(cred).addOnCompleteListener {
            if (it.isSuccessful) {
                showToast("Authentication completed!")
                (activity as RegisterActivity).replaceActivity(MainActivity())
            } else {
                showToast(it.exception?.message.toString())
                println(it.exception?.message.toString())
            }
        }
    }
}