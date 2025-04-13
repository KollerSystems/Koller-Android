package com.norbert.koller.shared.fragments.bottomsheet

import android.content.DialogInterface
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.view.ViewGroup.MarginLayoutParams
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.LinearLayout.HORIZONTAL
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.button.MaterialButton
import com.norbert.koller.shared.R
import com.norbert.koller.shared.activities.MainActivity
import com.norbert.koller.shared.databinding.FragmentBsdfTitleBinding
import com.norbert.koller.shared.managers.getAttributeColor


abstract class BsdfFragment : BottomSheetDialogFragment() {

    private lateinit var binding : FragmentBsdfTitleBinding
    lateinit var viewGroup : ViewGroup

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentBsdfTitleBinding.inflate(layoutInflater)
        viewGroup = getContentHolder(inflater)
        viewGroup.setBackgroundColor(requireContext().getAttributeColor(com.google.android.material.R.attr.colorSurface))
        binding.root.addView(viewGroup)
        if(context is MainActivity) {
            if (savedInstanceState == null){
                if((context as MainActivity).viewModel.currentBottomSheetDialogFragment != null){
                    (context as MainActivity).viewModel.currentBottomSheetDialogFragment!!.dismiss()
                }
                (context as MainActivity).viewModel.currentBottomSheetDialogFragment = this
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    fun addEndIcon(drawableId : Int){
        val image = ImageView(context)
        setSizeOfEndView(image)
        image.setImageResource(drawableId)
        addLinearLayout().addView(image)
    }

    fun addEndText(string : String){
        val text = TextView(ContextThemeWrapper(requireContext(), R.style.IconLikeText))
        text.setTypeface(ResourcesCompat.getFont(requireContext(), R.font.rubik), Typeface.BOLD)
        setSizeOfEndView(text)
        text.text = string
        text.gravity = Gravity.CENTER
        addLinearLayout().addView(text)
    }

    fun addEndButton(drawableId : Int) : MaterialButton{
        val button = MaterialButton(requireContext())
        button.setIconResource(drawableId)
        button.iconPadding = 0
        button.iconSize = resources.getDimensionPixelSize(R.dimen.full_card_padding)
        button.setPadding(button.paddingTop, button.paddingTop, button.paddingTop, button.paddingTop)
        button.setBackgroundColor(Color.TRANSPARENT)
        button.iconTint = ColorStateList(
            arrayOf(intArrayOf(android.R.attr.state_enabled)),
            intArrayOf(requireContext().getAttributeColor(com.google.android.material.R.attr.colorPrimary)))
        val params = MarginLayoutParams(WRAP_CONTENT, WRAP_CONTENT)
        params.marginEnd = resources.getDimensionPixelSize(R.dimen.text_container_margin)
        button.layoutParams = params
        addLinearLayout().addView(button)
        return button
    }

    private fun setSizeOfEndView(view: View){
        val cardIconSize = resources.getDimensionPixelSize(R.dimen.card_icon)
        val layoutParams = LinearLayout.LayoutParams(cardIconSize,cardIconSize)
        val cardPadding = resources.getDimensionPixelSize(R.dimen.card_padding)
        layoutParams.marginEnd = cardPadding
        layoutParams.topMargin = cardPadding
        layoutParams.bottomMargin = cardPadding
        view.layoutParams = layoutParams
    }

    private fun addLinearLayout() : LinearLayout {
        val linearLayout = LinearLayout(context)
        val layoutParams = LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
        linearLayout.layoutParams = layoutParams
        linearLayout.orientation = HORIZONTAL
        linearLayout.gravity = Gravity.CENTER_VERTICAL
        val card = binding.textTitle.parent as ViewGroup
        card.addView(linearLayout)
        card.removeView(binding.textTitle)
        linearLayout.addView(binding.textTitle)
        val params = binding.textTitle.layoutParams as LinearLayout.LayoutParams
        params.weight = 1f
        params.width = 0
        binding.textTitle.layoutParams = params
        return linearLayout
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        if(context is MainActivity) {
            if ((context as MainActivity).viewModel.currentBottomSheetDialogFragment == this) {
                (context as MainActivity).viewModel.currentBottomSheetDialogFragment = null
            }
        }
    }

    abstract fun getContentHolder(inflater: LayoutInflater) : ViewGroup

    fun setTitle(title : String) : TextView{
        binding.textTitle.visibility = VISIBLE
        binding.textTitle.text = title
        return binding.textTitle
    }

    fun getRoot() : ViewGroup{
        return binding.root
    }

}