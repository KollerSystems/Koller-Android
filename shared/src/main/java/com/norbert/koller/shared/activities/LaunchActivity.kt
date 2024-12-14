package com.norbert.koller.shared.activities

import android.annotation.SuppressLint
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
import com.google.gson.Gson
import com.norbert.koller.shared.R
import com.norbert.koller.shared.api.AuthenticationManager
import com.norbert.koller.shared.managers.DataStoreManager
import com.norbert.koller.shared.managers.ApplicationManager
import com.norbert.koller.shared.api.RetrofitInstance
import com.norbert.koller.shared.data.LoginTokensData
import com.norbert.koller.shared.data.LoginTokensResponseData
import com.norbert.koller.shared.data.UserData
import com.norbert.koller.shared.managers.CacheManager
import com.norbert.koller.shared.managers.DataStoreManager.Companion.createDynamicUserDataStore
import com.norbert.koller.shared.managers.DataStoreManager.Companion.getCurrentUserDataStore
import com.norbert.koller.shared.managers.DataStoreManager.Companion.loginDataStore
import com.norbert.koller.shared.managers.DataStoreManager.Companion.userDataStore
import com.norbert.koller.shared.widgets.WidgetHelper
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
class LaunchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch)

        WidgetHelper.widgetTag = intent.getStringExtra(WidgetHelper.WIDGET_TAG_KEY)
        if(WidgetHelper.widgetTag != null) {
            Toast.makeText(this, WidgetHelper.widgetTag, Toast.LENGTH_SHORT).show()
            intent = null
        }

        //lehet felesleges
        AuthenticationManager.handleFailedTokenRefresh = {

            lifecycleScope.launch {


                loginDataStore.edit {
                    it.remove(DataStoreManager.TOKENS)
                }
                finishAffinity()
                ApplicationManager.openLogin(this@LaunchActivity)
            }

        }

        AuthenticationManager.handleRefreshedTokenSaving = {
            lifecycleScope.launch {
                DataStoreManager.saveTokens(this@LaunchActivity)
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
                    return if (CacheManager.getCurrentUserData() != null) {
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
            CacheManager.loginData = DataStoreManager.readTokens(this@LaunchActivity)

            if (CacheManager.loginData != null) {

                createDynamicUserDataStore(CacheManager.loginData!!.uid)
                val json = getCurrentUserDataStore()!!.data.first()[DataStoreManager.USER]

                if (json != null) {
                    val userData = Gson().fromJson(json, UserData::class.java)
                    CacheManager.detailsDataMap[Pair(UserData::class.simpleName!!, userData.uid)] = userData
                    CacheManager.currentUserId = userData.uid
                    ApplicationManager.openMain.invoke(this@LaunchActivity)
                }
                else{
                    ApplicationManager.openLogin.invoke(this@LaunchActivity)

                }

            } else {
                ApplicationManager.openLogin.invoke(this@LaunchActivity)
            }

            finish()



        }
    }
}