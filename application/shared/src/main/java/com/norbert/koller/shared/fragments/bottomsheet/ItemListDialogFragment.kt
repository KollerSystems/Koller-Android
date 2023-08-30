package com.norbert.koller.shared.fragments.bottomsheet

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import com.norbert.koller.shared.R
import com.norbert.koller.shared.recycleradapter.ListAdapter
import com.norbert.koller.shared.recycleradapter.ListItem
import java.util.Arrays

class ItemListDialogFragment : BottomSheetDialogFragment() {

    private lateinit var recycleView : RecyclerView

    var list : ArrayList<ListItem> = arrayListOf()

    var getValuesOnFinish: ((listOftTrue : ArrayList<String>, localizedString : String) -> Unit)? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view : View = inflater.inflate(R.layout.fragment_item_list_dialog_list_dialog, container, false)

        recycleView = view.findViewById(R.id.simple_list_bottom_fragment_recycle_view)


        recycleView.adapter = ListAdapter(this, list)


        recycleView.layoutManager = LinearLayoutManager(context)
        recycleView.setHasFixedSize(true)

        return view
    }

    override fun onCancel(dialog: DialogInterface) {
        Log.d("TEST", "1")

        if(getValuesOnFinish != null) {

            val stringList: ArrayList<String> = arrayListOf()
            val localizedStringList: ArrayList<String> = arrayListOf()

            for (item in list) {
                if (item.isChecked!!) {
                    stringList.add(item.tag!!)
                    localizedStringList.add(item.title)
                }
            }

            val localizedStrings: String = Arrays.toString(localizedStringList.toArray()).replace("[", "").replace("]", "")
            getValuesOnFinish!!.invoke(stringList, localizedStrings)
        }
        super.onCancel(dialog)
    }



    companion object {
        const val TAG = "ItemListDialogFragment"
    }

}