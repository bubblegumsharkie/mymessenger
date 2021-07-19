package com.example.mymessenger

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.mymessenger.activities.RegisterActivity
import com.example.mymessenger.databinding.ActivityMainBinding
import com.example.mymessenger.ui.fragments.ChatsFragment
import com.example.mymessenger.ui.objects.AppDrawer

class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding
    private lateinit var mDrawer: AppDrawer
    private lateinit var mToolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
    }

    override fun onStart() {
        super.onStart()
        Toast.makeText(applicationContext, "HELLO!", Toast.LENGTH_LONG)
        initFields()
        initFunc()
    }

    private fun initFunc() {

        if (false) {
            setSupportActionBar(mToolbar)
            mDrawer.create()
            supportFragmentManager.beginTransaction()
                .replace(R.id.data_container, ChatsFragment())
                .commit()
        }

        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)

    }

    private fun initFields() {
        mToolbar = mBinding.mainToolbar
        mDrawer = AppDrawer(this, mToolbar)

    }
}