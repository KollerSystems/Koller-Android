package com.example.koller.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import com.example.koller.R

class CommentView : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view : View = inflater.inflate(R.layout.view_comment, container, false)

        Toast.makeText(view.context, "You Clicked Button", Toast.LENGTH_SHORT).show()

        val buttonMore : Button = view.findViewById(R.id.comment_more_options)

        buttonMore.setOnClickListener {
            val popupMenu = PopupMenu(view.context, buttonMore)

            // Inflating popup menu from popup_menu.xml file
            popupMenu.menuInflater.inflate(R.menu.comment, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener { menuItem ->
                // Toast message on menu item clicked
                Toast.makeText(view.context, "You Clicked " + menuItem.title, Toast.LENGTH_SHORT).show()
                true
            }
            Toast.makeText(view.context, "You Clicked Button", Toast.LENGTH_SHORT).show()
            // Showing the popup menu
            popupMenu.show()
        }

        return view
    }
}