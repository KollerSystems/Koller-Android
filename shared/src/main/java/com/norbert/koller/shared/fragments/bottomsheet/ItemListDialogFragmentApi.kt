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
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.view.ViewGroup.MarginLayoutParams
import android.widget.ProgressBar
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.norbert.koller.shared.R
import com.norbert.koller.shared.activities.MainActivity
import com.norbert.koller.shared.api.RetrofitInstance
import com.norbert.koller.shared.data.BaseData
import com.norbert.koller.shared.data.ClassData
import com.norbert.koller.shared.managers.CacheManager
import com.norbert.koller.shared.recycleradapters.ListItem
import kotlinx.coroutines.launch
import retrofit2.Response

class ItemListDialogFragmentApi(val apiToCall : suspend () -> Response<*>, alreadyChecked : ArrayList<String>? = null, val key : String) : ItemListDialogFragmentBase(alreadyChecked) {



    fun responseToListItemList(response : ArrayList<BaseData>) : ArrayList<ListItem>{
        var listItemList: ArrayList<ListItem> = arrayListOf()
        for (i in response.indices){
            var listItem = ListItem(response[i].getTitle(), response[i].getDescription(), null, response[i].getMainID().toString())
            listItemList.add(listItem)
        }

        return listItemList
    }

    fun setRecyclerViewWitResponse(response : ArrayList<BaseData>) : ArrayList<ListItem>{
        val listItemList = responseToListItemList(response)
        setRecyclerView(listItemList)

        return listItemList
    }

    override fun toggleList(): Boolean {
        return getValuesOnFinish != null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            if(CacheManager.savedListsOfValues.containsKey(key)){
                list = setRecyclerViewWitResponse(CacheManager.savedListsOfValues[key]!!)
                allLoaded()
            }
            else{
                val pb = ProgressBar(requireContext())
                val margin = requireContext().resources.getDimensionPixelSize(R.dimen.text_container_margin)
                val mlp = MarginLayoutParams(MATCH_PARENT, WRAP_CONTENT)
                mlp.setMargins(margin,margin,margin,margin)
                pb.layoutParams = mlp
                (recycleView.parent as ViewGroup).addView(pb)
                RetrofitInstance.communicate(apiToCall, {
                    (recycleView.parent as ViewGroup).removeView(pb)
                    val arrayList : ArrayList<BaseData> = ArrayList(it as List<BaseData>)
                    list = setRecyclerViewWitResponse(arrayList)
                    CacheManager.savedListsOfValues[key] = arrayList
                    allLoaded()
                },{ error, errorBody ->
                    dismiss()
                    var snackbar = (requireContext() as MainActivity).getSnackBar(getString(R.string.an_error_occurred), Snackbar.LENGTH_SHORT)
                    snackbar.show()
                })
            }
        }



    }

}