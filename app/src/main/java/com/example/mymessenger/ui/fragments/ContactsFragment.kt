package com.example.mymessenger.ui.fragments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mymessenger.R
import com.example.mymessenger.models.ContactModel
import com.example.mymessenger.utils.*
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.contact_item.view.*
import kotlinx.android.synthetic.main.fragment_contacts.*

class ContactsFragment : BaseFragment(R.layout.fragment_contacts) {

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapter: FirebaseRecyclerAdapter<ContactModel, ContactsHolder>
    private lateinit var mRefContacts: DatabaseReference
    private lateinit var mRefUsers: DatabaseReference


    override fun onResume() {
        super.onResume()
        APP_ACTIVITY.title = "Contacts"
        initRecyclerView()
    }

    override fun onPause() {
        super.onPause()
        mAdapter.stopListening()
    }

    private fun initRecyclerView() {
        mRecyclerView = contacts_recyclerView
        mRefContacts = REF_DATABASE_ROOT.child(NODE_PHONES_CONTACTS).child(CURRENT_UID)

        val options = FirebaseRecyclerOptions.Builder<ContactModel>()
            .setQuery(mRefContacts, ContactModel::class.java)
            .build()
        mAdapter = object : FirebaseRecyclerAdapter<ContactModel, ContactsHolder>(options) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.contact_item, parent, false)
                return ContactsHolder(view)
            }

            override fun onBindViewHolder(
                holder: ContactsHolder,
                position: Int,
                model: ContactModel
            ) {
                mRefUsers = REF_DATABASE_ROOT.child(NODE_USERS).child(model.id)
                mRefUsers.addValueEventListener(AppValueEventListener {
                    val contact = it.getContactModel()
                    holder.name.text = contact.fullname
                    holder.status.text = contact.state
                    holder.userpic.downloadAndSetImage(contact.photoUrl)
                })
            }
        }
        mRecyclerView.adapter = mAdapter
        mAdapter.startListening()
    }

    class ContactsHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.contact_fullname
        val status: TextView = view.contact_status
        val userpic: CircleImageView = view.contact_user_pic
    }
}
