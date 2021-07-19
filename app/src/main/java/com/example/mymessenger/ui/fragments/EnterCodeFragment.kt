package com.example.mymessenger.ui.fragments

import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import com.example.mymessenger.R
import kotlinx.android.synthetic.main.fragment_enter_code.*

class EnterCodeFragment : BaseFragment(R.layout.fragment_enter_code) {

    override fun onStart() {
        super.onStart()
        register_edit_code.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                val string = register_edit_code.text.toString()
                if (string.length == 6) {
                    verifyCode()
                }
            }

        })
    }

    private fun verifyCode() {
        Toast.makeText(context, getString(R.string.register_toast_code_verified), Toast.LENGTH_SHORT).show()
    }
}