package com.norbert.koller.shared.activities

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.lifecycleScope
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.norbert.koller.shared.R
import com.norbert.koller.shared.api.AuthenticationManager
import com.norbert.koller.shared.managers.DataStoreManager
import com.norbert.koller.shared.managers.ApplicationManager
import com.norbert.koller.shared.api.RetrofitInstance
import com.norbert.koller.shared.data.LoginTokensData
import com.norbert.koller.shared.data.LoginTokensResponseData
import com.norbert.koller.shared.data.UserData
import com.norbert.koller.shared.managers.DataStoreManager.Companion.loginDataStore
import com.norbert.koller.shared.widgets.WidgetHelper
import kotlinx.coroutines.launch

class LaunchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch)

        WidgetHelper.widgetTag = intent.getStringExtra(WidgetHelper.WIDGET_TAG_KEY)
        if(WidgetHelper.widgetTag != null) {
            Toast.makeText(this, WidgetHelper.widgetTag, Toast.LENGTH_SHORT).show()
            intent = null
        }

        AuthenticationManager.handleFailedTokenRefresh = {

            lifecycleScope.launch {


                loginDataStore.edit {
                    it.remove(DataStoreManager.TOKENS)
                }
                finishAffinity()
                ApplicationManager.openActivity(
                    this@LaunchActivity,
                    LoginActivity()::class.java
                )
            }

        }

        AuthenticationManager.handleRefreshedTokenSaving = {
            lifecycleScope.launch {
                DataStoreManager.saveTokens(
                    this@LaunchActivity,
                    LoginTokensData.instance!!
                )
            }
        }

        if(!isTaskRoot){
            finish()
            return
        }

        val content: View = findViewById(android.R.id.content)
        content.viewTreeObserver.addOnPreDrawListener(
            object : ViewTreeObserver.OnPreDrawListener {
                override fun onPreDraw(): Boolean {
                    // Check whether the initial data is ready.
                    return if (UserData.instance.uid != -1) {
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
            LoginTokensData.instance = DataStoreManager.readTokens(this@LaunchActivity)
            if (LoginTokensData.instance == null) {
                ApplicationManager.openLogin.invoke(this@LaunchActivity)
                finish()

            } else {

                if(ApplicationManager.isOnline(this@LaunchActivity)) {
                    RetrofitInstance.communicate(lifecycleScope,
                        RetrofitInstance.api::getCurrentUser,
                        {
                            UserData.instance = it as UserData

                            ApplicationManager.openMain.invoke(this@LaunchActivity)
                            finish()
                        },
                        { _, _ ->
                            ApplicationManager.openLogin.invoke(this@LaunchActivity)
                            finish()
                        })

                }
                else{
                    ApplicationManager.openMain.invoke(this@LaunchActivity)
                    finish()
                }
            }
        }


    }
}