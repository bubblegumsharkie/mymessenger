package com.example.mymessenger.utils

import android.content.Intent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.mymessenger.R

fun Fragment.showToast(message: String) {
    Toast.makeText(this.context, message, Toast.LENGTH_LONG).show()
}

fun AppCompatActivity.replaceActivity(activity: AppCompatActivity) {
    val intent = Intent(this, activity::class.java)
    startActivity(intent)
    this.finish()
}

fun AppCompatActivity.replaceFragment(fragment: Fragment, addStack: Boolean = true) {
    if (addStack) {
        supportFragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(R.id.data_container, fragment)
            .commit()
    } else {
        supportFragmentManager.beginTransaction()
            .replace(R.id.data_container, fragment)
            .commit()
    }


}

fun Fragment.replaceFragment(fragment: Fragment) {
    //check if this works like deprecated fragmentManager \/
//            fragmentManager?.beginTransaction()
    this.parentFragmentManager.beginTransaction()
        .addToBackStack(null)
        .replace(R.id.data_container, fragment)
        .commit()
}