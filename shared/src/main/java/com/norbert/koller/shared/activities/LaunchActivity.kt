package com.norbert.koller.shared.activities

import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.norbert.koller.shared.R
import com.norbert.koller.shared.managers.DataStoreManager
import com.norbert.koller.shared.managers.MyApplication
import com.norbert.koller.shared.api.RetrofitInstance
import com.norbert.koller.shared.data.LoginTokensData
import com.norbert.koller.shared.data.LoginTokensResponseData
import com.norbert.koller.shared.data.UserData
import kotlinx.coroutines.launch

class LaunchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch)

        if(!isTaskRoot){
            finish()
            return
        }

        val content: View = findViewById(android.R.id.content)
        content.viewTreeObserver.addOnPreDrawListener(
            object : ViewTreeObserver.OnPreDrawListener {
                override fun onPreDraw(): Boolean {
                    // Check whether the initial data is ready.
                    return if (UserData.instance.uid != -1 || !MyApplication.isOnline(this@LaunchActivity)) {
                        // The content is ready. Start drawing.
                        content.viewTreeObserver.removeOnPreDrawListener(this)
                        true
                    } else {
                        // The content isn't ready. Suspend.
                        false
                    }
                }
            }
        )

        lifecycleScope.launch {

            if (!MyApplication.isOnline(this@LaunchActivity)) {
                MaterialAlertDialogBuilder(this@LaunchActivity)
                    .setTitle("Nincs internet")
                    .setMessage("Az alkalmazás jelenleg csakis internettel képes működni")
                    .setIcon(R.drawable.no_internet)
                    .setPositiveButton("Alkalmazás bezárása")
                    { _, _ ->
                        finishAffinity()
                    }
                    .show()

                return@launch
            }

            LoginTokensData.instance = DataStoreManager.readTokens(this@LaunchActivity)
            if (LoginTokensData.instance == null) {
                MyApplication.openLogin.invoke(this@LaunchActivity)
                finish()

            }else {

                RetrofitInstance.communicate(lifecycleScope, RetrofitInstance.api::getCurrentUser,
                    {
                        UserData.instance = it as UserData

                        MyApplication.openMain.invoke(this@LaunchActivity)
                        finish()
                    },
                    {_,_->
                        MyApplication.openLogin.invoke(this@LaunchActivity)
                        finish()
                    })

            }

        }



    }
}