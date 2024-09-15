package com.norbert.koller.teacher.activities

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.norbert.koller.shared.activities.LoginActivity
import com.norbert.koller.teacher.recycleradapters.LoginViewPagerRecyclerAdapter
import com.norbert.koller.teacher.R
import com.norbert.koller.teacher.viewmodels.LoginViewModel

class LoginActivity : LoginActivity(){
    override fun getLoginViewModel(): LoginViewModel {
        return ViewModelProvider(this)[LoginViewModel::class.java]
    }

    override fun getRecyclerAdapter(): LoginViewPagerRecyclerAdapter {
        return LoginViewPagerRecyclerAdapter()
    }

    override fun getRoleError(): Int {
        return R.string.wrong_role
    }
}