package com.norbert.koller.student.activities

import androidx.lifecycle.ViewModelProvider
import com.norbert.koller.shared.activities.LoginActivity
import com.norbert.koller.student.R
import com.norbert.koller.student.recycleradapters.LoginViewPagerRecyclerAdapter
import com.norbert.koller.student.viewmodels.LoginViewModel

class LoginActivity : LoginActivity(){
    override fun getLoginViewModel(): LoginViewModel {
        return ViewModelProvider(this)[LoginViewModel::class.java]
    }

    override fun getRoleError(): Int {
        return R.string.wrong_role
    }

    override fun getRecyclerAdapter(): LoginViewPagerRecyclerAdapter {
        return LoginViewPagerRecyclerAdapter()
    }

}