package com.example.mymessenger.ui.fragments

import androidx.fragment.app.Fragment
import com.example.mymessenger.MainActivity

open class BaseFragment(layout: Int) : Fragment(layout) {

    override fun onStart() {
        super.onStart()
        (activity as MainActivity).mDrawer.disableDrawer()
    }

    override fun onStop() {
        super.onStop()
        (activity as MainActivity).mDrawer.enableDrawer()
    }
}