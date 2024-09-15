package com.norbert.koller.student.viewmodels

import com.norbert.koller.shared.viewmodels.LoginViewModel

class LoginViewModel() : LoginViewModel() {
    override fun correctRole(role : Int): Boolean {
        return role == 1
    }
}