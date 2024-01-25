package com.alexisboiz.boursewatcher.model.UsersModel

import com.alexisboiz.boursewatcher.utils.UserError
import com.google.firebase.auth.FirebaseUser

class UserDetail {
    var errors = mutableListOf<UserError>()
     var user : FirebaseUser? = null
    constructor(
        user: FirebaseUser?,
        errors: MutableList<UserError>
    ) {
        if (user != null) {
            this.user = user
        }
        this.errors = errors
    }

}