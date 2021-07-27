package com.example.mymessenger.ui.fragments

import android.widget.Toast
import com.example.mymessenger.R
import com.example.mymessenger.utils.AppTextWatcher
import com.example.mymessenger.utils.showToast
import kotlinx.android.synthetic.main.fragment_enter_code.*

class EnterCodeFragment : BaseFragment(R.layout.fragment_enter_code) {

    override fun onStart() {
        super.onStart()
        register_edit_code.addTextChangedListener(AppTextWatcher {
            val string = register_edit_code.text.toString()
            if (string.length == 6) {
                verifyCode()
            }
        })
    }

    private fun verifyCode() {
        showToast(getString(R.string.register_toast_code_verified))
        println(getString(R.string.register_toast_code_verified))
    }
}