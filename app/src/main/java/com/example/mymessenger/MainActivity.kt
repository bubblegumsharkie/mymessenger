package com.example.mymessenger

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.mymessenger.activities.RegisterActivity
import com.example.mymessenger.databinding.ActivityMainBinding
import com.example.mymessenger.ui.fragments.ChatsFragment
import com.example.mymessenger.ui.objects.AppDrawer
import com.example.mymessenger.utils.*

class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding
    lateinit var mDrawer: AppDrawer
    private lateinit var mToolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        APP_ACTIVITY = this
        initFirebase()
        initUser {
            initFields()
            initFunc()
        }
    }

    private fun initFunc() {
        if (AUTH.currentUser != null) {
            setSupportActionBar(mToolbar)
            mDrawer.create()
            replaceFragment(ChatsFragment(), false)
        } else {
            replaceActivity(RegisterActivity())
        }
    }

    private fun initFields() {
        mToolbar = mBinding.mainToolbar
        mDrawer = AppDrawer(this, mToolbar)
    }

    override fun onStart() {
        super.onStart()
        appStates.updateState(appStates.ONLINE)
    }

    override fun onStop() {
        super.onStop()
        appStates.updateState(appStates.OFFLINE)
    }
}