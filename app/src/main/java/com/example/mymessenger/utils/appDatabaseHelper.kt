package com.example.mymessenger.utils

import android.net.Uri
import android.provider.ContactsContract
import com.example.mymessenger.models.ContactModel
import com.example.mymessenger.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.util.ArrayList

lateinit var AUTH: FirebaseAuth
lateinit var CURRENT_UID: String
lateinit var REF_DATABASE_ROOT: DatabaseReference
lateinit var REF_STORAGE_ROOT: StorageReference
lateinit var USER: User

const val FOLDER_PROFILE_IMAGE = "profile_image"

const val NODE_USERS = "users"
const val NODE_USERNAMES = "usernames"
const val NODE_PHONES = "phones"
const val NODE_PHONES_CONTACTS = "phones_contacts"


const val CHILD_ID = "id"
const val CHILD_PHONE = "phone"
const val CHILD_USERNAME = "username"
const val CHILD_FULLNAME = "fullname"
const val CHILD_BIO = "bio"
const val CHILD_USER_PIC_URI = "photoUrl"
const val CHILD_STATE = "state"

fun initFirebase() {
    AUTH = FirebaseAuth.getInstance()
    REF_DATABASE_ROOT = FirebaseDatabase.getInstance().reference
    USER = User()
    CURRENT_UID = AUTH.currentUser?.uid.toString()
    REF_STORAGE_ROOT = FirebaseStorage.getInstance().reference
}

fun initContacts() {
    if (checkPermissions(READ_CONTACTS)) {
        val arrayOfContacts = arrayListOf<ContactModel>()
        val cursor = APP_ACTIVITY.contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null,
            null,
            null,
            null
        )
        cursor?.let {
            while (it.moveToNext()) {
                val fullName =
                    it.getString(it.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                val phone =
                    it.getString(it.getColumnIndex((ContactsContract.CommonDataKinds.Phone.NUMBER)))
                val newContact = ContactModel()
                newContact.fullname = fullName
                newContact.phone = phone.replace(Regex("[\\s, -]"), "")
                arrayOfContacts.add(newContact)
            }
        }
        cursor?.close()
        uploadContactsToDatabase(arrayOfContacts)
    }
}

fun uploadContactsToDatabase(arrayOfContacts: ArrayList<ContactModel>) {
    REF_DATABASE_ROOT.child(NODE_PHONES).addListenerForSingleValueEvent(AppValueEventListener{ task ->
        task.children.forEach { dataSnapshot ->
        arrayOfContacts.forEach { contactModel ->
            if (dataSnapshot.key == contactModel.phone) {
                REF_DATABASE_ROOT.child(NODE_PHONES_CONTACTS).child(CURRENT_UID)
                    .child(dataSnapshot.value.toString()).child(CHILD_ID)
                    .setValue(dataSnapshot.value.toString())
                    .addOnFailureListener { showToast(it.message.toString()) }
            }
        }
        }
    })
}

inline fun putUrlToDB(url: String, crossinline function: () -> Unit) {
    REF_DATABASE_ROOT
        .child(NODE_USERS)
        .child(CURRENT_UID)
        .child(CHILD_USER_PIC_URI)
        .setValue(url)
        .addOnSuccessListener { function() }
        .addOnFailureListener { showToast(it.message.toString()) }
}

inline fun getUrlFromStorage(path: StorageReference, crossinline function: (url: String) -> Unit) {
    path.downloadUrl.addOnSuccessListener { function(it.toString()) }
        .addOnFailureListener { showToast(it.message.toString()) }
}

inline fun putImageToStorage(uri: Uri, path: StorageReference, crossinline function: () -> Unit) {
    path.putFile(uri)
        .addOnSuccessListener { function() }
        .addOnFailureListener { showToast(it.message.toString()) }
}

inline fun initUser(crossinline function: () -> Unit) {
    REF_DATABASE_ROOT.child(NODE_USERS).child(CURRENT_UID)
        .addListenerForSingleValueEvent(AppValueEventListener {
            USER = it.getValue(User::class.java) ?: User()
            if (USER.username.isEmpty()) {
                USER.username = CURRENT_UID
            }
            function()
        })
}