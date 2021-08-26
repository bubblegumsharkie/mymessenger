package com.example.mymessenger.ui.fragments

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import com.example.mymessenger.R
import com.example.mymessenger.activities.RegisterActivity
import com.example.mymessenger.utils.*
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import kotlinx.android.synthetic.main.fragment_settings.*

class SettingsFragment : BaseFragment(R.layout.fragment_settings) {

    override fun onResume() {
        super.onResume()
        setHasOptionsMenu(true)
        initFields()
    }

    private fun initFields() {
        settings_about.text = USER.bio
        settings_person_name.text = USER.fullname
        settings_phone_number.text = USER.phone
        settings_status.text = USER.state
        settings_username.text = USER.username

        //image
        settings_profile_image.downloadAndSetImage(USER.photoUrl)

        //Buttons
        settings_username_field.setOnClickListener {
            replaceFragment(ChangeUsernameFragment())
        }

        settings_about_field.setOnClickListener {
            replaceFragment(ChangeBioFragment())
        }

        settings_change_image.setOnClickListener {
            changeUserPic()
        }
    }

    private fun changeUserPic() {
        CropImage.activity()
            .setAspectRatio(1, 1)
            .setRequestedSize(600, 600)
            .setCropShape(CropImageView.CropShape.OVAL)
            .start(APP_ACTIVITY, this)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        activity?.menuInflater?.inflate(R.menu.settings_action_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.settings_menu_exit -> {
                AUTH.signOut()
                APP_ACTIVITY.replaceActivity(RegisterActivity())
            }
            R.id.settings_menu_change_name -> replaceFragment(ChangeNameFragment())
        }
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE
            && resultCode == RESULT_OK
            && data != null
        ) {
            val uri = CropImage.getActivityResult(data).uri
            val path = REF_STORAGE_ROOT.child(FOLDER_PROFILE_IMAGE).child(CURRENT_UID)

            putImageToStorage(uri, path) {
                getUrlFromStorage(path) {
                    putUrlToDB(it) {
                        settings_profile_image.downloadAndSetImage(it)
                        showToast("Upload success")
                        USER.photoUrl = it
                        APP_ACTIVITY.mDrawer.updateHeader()
                    }
                }
            }
        }
    }

}