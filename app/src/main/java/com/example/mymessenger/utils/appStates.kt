package com.example.mymessenger.utils

enum class appStates(val state: String) {
    ONLINE("online"),
    OFFLINE("offline"),
    TYPING("typing");

    companion object {
        fun updateState(appStates: appStates) {
            REF_DATABASE_ROOT
                .child(NODE_USERS)
                .child(CURRENT_UID)
                .child(CHILD_STATE)
                .setValue(appStates.state)
                .addOnSuccessListener { USER.state = appStates.state }
                .addOnFailureListener { showToast(it.message.toString()) }
        }
    }
}