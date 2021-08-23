package com.example.mymessenger.ui.fragments

import com.example.mymessenger.MainActivity
import com.example.mymessenger.R
import com.example.mymessenger.activities.RegisterActivity
import com.example.mymessenger.utils.*
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.android.synthetic.main.fragment_enter_code.*

class EnterCodeFragment(val phoneNumber: String, val id: String) :
    BaseFragment(R.layout.fragment_enter_code) {


    override fun onStart() {
        super.onStart()
        (activity as RegisterActivity).title = phoneNumber
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
        AUTH.signInWithCredential(cred).addOnCompleteListener { taskSignIn ->
            if (taskSignIn.isSuccessful) {
                val uid = AUTH.currentUser?.uid.toString()
                val dateMap = mutableMapOf<String, Any>()
                dateMap[CHILD_ID] = uid
                dateMap[CHILD_PHONE] = phoneNumber
                dateMap[CHILD_USERNAME] = uid

                REF_DATABASE_ROOT.child(NODE_USERS).child(uid).setValue(dateMap)
                    .addOnCompleteListener { taskAuth ->
                        println(taskAuth.toString())
                        if (taskAuth.isSuccessful) {
                            showToast("Authentication completed!")
                            (activity as RegisterActivity).replaceActivity(MainActivity())
                        } else {
                            showToast(taskAuth.exception?.message.toString())
                            println("DALEK!" + taskAuth.exception?.message.toString())
                        }
                    }
            } else {
                showToast(taskSignIn.exception?.message.toString())
                println(taskSignIn.exception?.message.toString())
            }
        }
    }
}