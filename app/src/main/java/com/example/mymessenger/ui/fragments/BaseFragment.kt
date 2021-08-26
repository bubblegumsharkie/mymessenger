package com.example.mymessenger.ui.fragments

import androidx.fragment.app.Fragment
import com.example.mymessenger.MainActivity
import com.example.mymessenger.utils.APP_ACTIVITY

open class BaseFragment(layout: Int) : Fragment(layout) {

    override fun onStart() {
        super.onStart()
        APP_ACTIVITY.mDrawer.disableDrawer()
    }

    override fun onStop() {
        super.onStop()
        APP_ACTIVITY.mDrawer.enableDrawer()
    }
}