package com.norbert.koller.teacher.fragments

import android.os.Bundle
import android.view.View

class RoomListFragment(defaultFilters : MutableMap<String, ArrayList<String>>? = null) : com.norbert.koller.shared.fragments.RoomListFragment(defaultFilters) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*val buttonAddNew : Button = view.findViewById(R.id.button_add_new)

        val buttonLayout = buttonAddNew.parent as ViewGroup
        buttonLayout.visibility = View.VISIBLE*/
    }

}