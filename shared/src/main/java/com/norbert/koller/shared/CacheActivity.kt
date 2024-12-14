package com.norbert.koller.shared

import android.graphics.Color
import android.os.Bundle
import android.widget.ScrollView
import android.widget.TextView
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ScrollingView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.setPadding
import androidx.core.view.updatePadding
import com.google.android.material.card.MaterialCardView
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.norbert.koller.shared.managers.CacheManager

class CacheActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_cache)
        val textView : TextView = findViewById(R.id.text)
        val cardSearch : MaterialCardView = findViewById(R.id.card_search)
        val scrollView : ScrollView = findViewById(R.id.scroll)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, 0, systemBars.right, 0)
            scrollView.updatePadding(bottom = scrollView.paddingLeft + systemBars.bottom)
            cardSearch.setContentPadding(cardSearch.contentPaddingLeft, cardSearch.contentPaddingLeft + systemBars.top, cardSearch.contentPaddingRight, cardSearch.contentPaddingBottom)
            insets
        }





        val gson = GsonBuilder().setPrettyPrinting().create()

        var cache = "---- LISTS ----\n\n"

        for (lists in CacheManager.listDataMap){
            cache += "${lists.key}:\n${gson.toJson(lists.value)}\n\n"
        }

        cache += "\n---- DETAILS ----\n\n"

        for (detailCategories in CacheManager.detailsDataMap){
            cache += "-- ${detailCategories.key} --\n"
            for (details in detailCategories.value){
                cache += "${details.key}:\n${gson.toJson(details.value)}\n\n"
            }
        }

        textView.text = cache
    }
}