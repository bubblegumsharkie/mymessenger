package com.example.mymessenger.ui.fragments

import com.example.mymessenger.MainActivity
import com.example.mymessenger.R
import com.example.mymessenger.activities.RegisterActivity
import com.example.mymessenger.utils.*
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.android.synthetic.main.fragment_enter_phone_number.*
import java.util.concurrent.TimeUnit


class EnterPhoneNumberFragment : BaseFragment(R.layout.fragment_enter_phone_number) {

    private lateinit var mPhoneNumber: String
    private lateinit var mCallBack: PhoneAuthProvider.OnVerificationStateChangedCallbacks


    override fun onStart() {
        super.onStart()
        APP_ACTIVITY.title = "Login"
        AUTH = FirebaseAuth.getInstance()
        mCallBack = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(p0: PhoneAuthCredential) {
                AUTH.signInWithCredential(p0).addOnCompleteListener {
                    if (it.isSuccessful) {
                        showToast("Authentication completed!")
                        (activity as RegisterActivity).replaceActivity(MainActivity())
                    } else {
                        showToast(it.exception?.message.toString())
                        println("AAAAH! this is wrong")
                        println(it.exception?.message.toString())
                    }
                }

            }

            override fun onCodeSent(p0: String, p1: PhoneAuthProvider.ForceResendingToken) {
                replaceFragment(EnterCodeFragment(mPhoneNumber, p0))
                println("Enter code fragment!")
            }

            override fun onVerificationFailed(p0: FirebaseException) {
                showToast(p0.message.toString())
                println(p0.message.toString())
            }

        }
        register_btn_fab_next.setOnClickListener { sendCode() }
    }

    private fun sendCode() {
        if (register_text_edit_phone.text.toString().isEmpty()) {
            showToast(getString(R.string.register_toast_enter_your_number))
            println(getString(R.string.register_toast_enter_your_number))
        } else {
//            replaceFragment(EnterCodeFragment())
            authUser()
        }
    }

    private fun authUser() {
        mPhoneNumber = register_text_edit_phone.text.toString()
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
            mPhoneNumber,
            60,
            TimeUnit.SECONDS,
            activity as RegisterActivity,
            mCallBack
        )
    }

}


