package com.norbert.koller.shared.helpers

import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.google.android.material.shape.ShapeAppearanceModel
import com.google.android.material.shape.Shapeable
import com.norbert.koller.shared.R
import com.norbert.koller.shared.databinding.ItemBinding
import com.norbert.koller.shared.databinding.ItemUserBinding

class RecyclerViewHelper {

    class ItemViewHolder(val itemBinding: ItemBinding) : RecyclerView.ViewHolder(itemBinding.root){}

    class UserViewHolder(val itemBinding: ItemUserBinding) : RecyclerView.ViewHolder(itemBinding.root)

    companion object{
        fun roundRecyclerItemsVerticallyWithSeparator(view : View, position : Int, pagingDataAdapter : PagingDataAdapter<Any, RecyclerView.ViewHolder>){


            if(pagingDataAdapter.getItemViewType(position) != 0) return

            if(position == pagingDataAdapter.itemCount -1) {
                if(pagingDataAdapter.getItemViewType(position - 1) != 0){
                    roundCard(view)
                }
                else{
                    roundCardBottom(view)
                }

            }
            else if (pagingDataAdapter.getItemViewType(position - 1) != 0 && pagingDataAdapter.getItemViewType(position + 1) == 0) {
                roundCardTop(view)
            } else if (pagingDataAdapter.getItemViewType(position - 1) == 0 && pagingDataAdapter.getItemViewType(position + 1) != 0) {
                roundCardBottom(view)
            } else if  (pagingDataAdapter.getItemViewType(position - 1) != 0 && pagingDataAdapter.getItemViewType(position + 1) != 0){
                roundCard(view)
            }
            else{
                deroundCardVertical(view)
            }

        }

        fun roundRecyclerItemsVerticallyGrid(view : View, position: Int, size : Int){
            if (position == 0) {

                if(size == 1) {

                    roundCard(view)
                }
                else if (size == 2){
                    roundCardLeft(view)
                }
                else {
                    roundCardTopLeft(view)
                }

            }
            else if (position == 1) {

                if (size == 2 || size == 3){
                    roundCardRight(view)
                }
                else {
                    roundCardTopRight(view)
                }

            }
            else if (position == size - 1-1) {

                if(position % 2 != 0) {
                    roundCardBottomRight(view)
                }
                else{
                    roundCardBottomLeft(view)
                }

            }
            else if (position == size -1) {

                if(position % 2 != 0) {
                    roundCardBottomRight(view)
                }
                else{
                    roundCardBottom(view)
                }

            }
            else{
                if(position % 2 != 0) {
                    deroundCardWithLeftMargin(view)
                }
                else{
                    deroundCardWithRightMargin(view)
                }
            }
        }

        fun roundRecyclerItemsHorizontallyGrid(view : View, position: Int, size : Int){
            if (position == 0) {

                if(size == 1) {

                    roundCard(view)
                }
                else if (size == 2){
                    roundCardTop(view)
                }
                else {
                    roundCardTopLeft(view)
                }

            }
            else if (position == 1) {

                if (size == 2 || size == 3){
                    roundCardBottom(view)
                }
                else {
                    roundCardBottomLeft(view)
                }

            }
            else if (position == size - 1-1) {

                if(position % 2 != 0) {
                    roundCardBottomRight(view)
                }
                else{
                    roundCardTopRight(view)
                }

            }
            else if (position == size -1) {

                if(position % 2 != 0) {
                    roundCardBottomRight(view)
                }
                else{
                    roundCardRight(view)
                }

            }
            else{
                if(position % 2 != 0) {
                    deroundCardWithTopMargin(view)
                }
                else{
                    deroundCardWithBottomMargin(view)
                }
            }
        }


        fun roundRecyclerItemsX(view : View, position : Int, size : Int, startAppearance : Int, endAppearance : Int, leftPadding : Int, topPadding : Int, rightPadding : Int, bottomPadding : Int){
            val context = view.context
            if (size == 1) {

                roundCard(view)

            }
            else if(position == 0){

                roundCardX(view, startAppearance, 0, 0, rightPadding, bottomPadding)
            }
            else if (position == size-1){

                roundCardX(view, endAppearance, leftPadding, topPadding, 0, 0)
            }
            else{
                deroundCardX(view, leftPadding, topPadding, rightPadding, bottomPadding)
            }

        }

        fun roundRecyclerItemsVertically(view : View, position : Int, size : Int){

            roundRecyclerItemsVertically(view, null, position, size)
        }

        fun roundRecyclerItemsVertically(view : View, view2 : View?, position : Int, size : Int){
            if (size == 1) {

                roundCard(view, view2)

            }
            else if(position == 0){

                roundCardTop(view, view2)
            }
            else if (position == size-1){

                roundCardBottom(view, view2)
            }
            else{
                deroundCardVertical(view, view2)
            }

        }

        fun marginItemsVertically(view : View, position : Int, size : Int){

            val margin = view.context.resources.getDimensionPixelSize(R.dimen.card_margin)

            if (size == 1) {
                (view.layoutParams as? ViewGroup.MarginLayoutParams)?.setMargins(0,0,0,0)
            }
            else if(position == 0){

                (view.layoutParams as? ViewGroup.MarginLayoutParams)?.setMargins(0,0,0,margin)
            }
            else if (position == size-1){

                (view.layoutParams as? ViewGroup.MarginLayoutParams)?.setMargins(0,margin,0,0)
            }
            else{
                (view.layoutParams as? ViewGroup.MarginLayoutParams)?.setMargins(0,margin,0,margin)
            }

        }

        fun roundRecyclerItemsHorizontally(view: View, view2: View?, position: Int, size: Int){
            if (size == 1){

                roundCard(view, view2)
            }
            else if(position == 0){

                roundCardLeft(view, view2)
            }
            else if (position == size-1){

                roundCardRight(view, view2)
            }
            else{
                deroundCardHorizontal(view, view2)
            }
        }

        fun roundRecyclerItemsHorizontallyWithHeaderImages(view : View, view2: View, position : Int, size : Int){
            val context = view.context
            if (size == 1){

                var shapeAppearance = ShapeAppearanceModel.builder(context, R.style.OverlaySmallRoundedCard, 0).build()
                (view as MaterialCardView).shapeAppearanceModel = shapeAppearance

                shapeAppearance = ShapeAppearanceModel.builder(context, R.style.OverlaySmallRoundedCardTop, 0).build()
                (view2 as Shapeable).shapeAppearanceModel = shapeAppearance

                (view.layoutParams as? ViewGroup.MarginLayoutParams)?.setMargins(0,0,0,0)
                view.requestLayout()
            }
            else if(position == 0){

                var shapeAppearance = ShapeAppearanceModel.builder(context, R.style.OverlaySmallRoundedCardLeft, 0).build()
                (view as MaterialCardView).shapeAppearanceModel = shapeAppearance

                shapeAppearance = ShapeAppearanceModel.builder(context, R.style.OverlaySmallRoundedCardTopLeft, 0).build()
                (view2 as Shapeable).shapeAppearanceModel = shapeAppearance

                (view.layoutParams as? ViewGroup.MarginLayoutParams)?.setMargins(0,0,context.resources.getDimensionPixelSize(R.dimen.card_margin),0)
                view.requestLayout()
            }
            else if (position == size-1){

                var shapeAppearance = ShapeAppearanceModel.builder(context, R.style.OverlaySmallRoundedCardRight, 0).build()
                (view as MaterialCardView).shapeAppearanceModel = shapeAppearance

                shapeAppearance = ShapeAppearanceModel.builder(context, R.style.OverlaySmallRoundedCardTopRight, 0).build()
                (view2 as Shapeable).shapeAppearanceModel = shapeAppearance

                (view.layoutParams as? ViewGroup.MarginLayoutParams)?.setMargins(context.resources.getDimensionPixelSize(R.dimen.card_margin),0,0,0)
                view.requestLayout()
            }
            else{

                val shapeAppearance = ShapeAppearanceModel()
                (view as MaterialCardView).shapeAppearanceModel = shapeAppearance
                (view2 as Shapeable).shapeAppearanceModel = shapeAppearance

                (view.layoutParams as? ViewGroup.MarginLayoutParams)?.setMargins(context.resources.getDimensionPixelSize(R.dimen.card_margin),0,context.resources.getDimensionPixelSize(R.dimen.card_margin),0)
                view.requestLayout()
            }
        }


        fun roundCardX(view : View, view2 : View?, overlay : Int, leftPadding : Int, topPadding : Int, rightPadding : Int, bottomPadding : Int){
            val context = view.context
            val shapeAppearance = ShapeAppearanceModel.builder(
                context,
                overlay,
                0
            ).build()

            (view as Shapeable).shapeAppearanceModel = shapeAppearance
            if(view2 != null) (view2 as Shapeable).shapeAppearanceModel = shapeAppearance

            (view.layoutParams as? ViewGroup.MarginLayoutParams)?.setMargins(leftPadding,topPadding,rightPadding,bottomPadding)
            view.requestLayout()
        }

        fun roundCardX(view : View, overlay : Int, leftPadding : Int, topPadding : Int, rightPadding : Int, bottomPadding : Int){
            roundCardX(view, null, overlay, leftPadding, topPadding, rightPadding, bottomPadding)
        }

        fun roundCardBottom(view : View, view2 : View? = null){
            val context = view.context
            roundCardX(view, view2, R.style.OverlayRoundedCardBottom,0,context.resources.getDimensionPixelSize(R.dimen.card_margin),0,0)
        }

        fun roundCardBottomLeft(view : View, view2 : View? = null){
            val context = view.context
            roundCardX(view, view2, R.style.OverlayRoundedCardBottomLeft,0,context.resources.getDimensionPixelSize(R.dimen.card_margin),context.resources.getDimensionPixelSize(R.dimen.card_margin),0)
        }

        fun roundCardBottomRight(view : View, view2 : View? = null){
            val context = view.context
            roundCardX(view, view2, R.style.OverlayRoundedCardBottomRight,context.resources.getDimensionPixelSize(R.dimen.card_margin),context.resources.getDimensionPixelSize(R.dimen.card_margin),0,0)
        }

        fun roundCardTop(view : View, view2 : View? = null){
            val context = view.context
            roundCardX(view, view2, R.style.OverlayRoundedCardTop,0,0,0,context.resources.getDimensionPixelSize(R.dimen.card_margin))
        }

        fun roundCardTopLeft(view : View, view2 : View? = null){
            val context = view.context
            roundCardX(view, view2, R.style.OverlayRoundedCardTopLeft,0,0,context.resources.getDimensionPixelSize(R.dimen.card_margin),context.resources.getDimensionPixelSize(R.dimen.card_margin))
        }

        fun roundCardTopRight(view : View, view2 : View? = null){
            val context = view.context
            roundCardX(view, view2, R.style.OverlayRoundedCardTopRight,context.resources.getDimensionPixelSize(R.dimen.card_margin),0,0,context.resources.getDimensionPixelSize(R.dimen.card_margin))
        }

        fun roundCardLeft(view : View, view2 : View? = null){
            val context = view.context
            roundCardX(view, view2, R.style.OverlayRoundedCardLeft,0,0,context.resources.getDimensionPixelSize(R.dimen.card_margin),0)
        }

        fun roundCardRight(view : View, view2 : View? = null){
            val context = view.context
            roundCardX(view, view2, R.style.OverlayRoundedCardRight, context.resources.getDimensionPixelSize(R.dimen.card_margin),0,0,0)
        }

        fun roundCard(view : View, view2 : View? = null){
            roundCardX(view, view2, R.style.OverlayRoundedCard, 0,0,0,0)
        }

        fun deroundCardX(view : View, leftMargin : Int, topMargin : Int, rightMargin : Int, bottomMargin : Int){

            deroundCardX(view, null, leftMargin, topMargin, rightMargin, bottomMargin)
        }

        fun deroundCardX(view : View, view2 : View?, leftMargin : Int, topMargin : Int, rightMargin : Int, bottomMargin : Int){

            (view as Shapeable).shapeAppearanceModel = ShapeAppearanceModel()
            if(view2 != null) (view2 as Shapeable).shapeAppearanceModel = ShapeAppearanceModel()

            (view.layoutParams as? ViewGroup.MarginLayoutParams)?.setMargins(leftMargin,topMargin,rightMargin,bottomMargin)
            view.requestLayout()
        }


        fun deroundCardVertical(view : View, view2 : View? = null){

            val context = view.context
            deroundCardX(view, view2,0,context.resources.getDimensionPixelSize(R.dimen.card_margin),0,context.resources.getDimensionPixelSize(R.dimen.card_margin))
        }

        fun deroundCardWithLeftMargin(view : View, view2 : View? = null){

            val context = view.context
            deroundCardX(view, context.resources.getDimensionPixelSize(R.dimen.card_margin),
                context.resources.getDimensionPixelSize(R.dimen.card_margin),
                0,
                context.resources.getDimensionPixelSize(R.dimen.card_margin))
        }

        fun deroundCardWithTopMargin(view : View, view2 : View? = null){

            val context = view.context
            deroundCardX(view, context.resources.getDimensionPixelSize(R.dimen.card_margin),
                context.resources.getDimensionPixelSize(R.dimen.card_margin),
                context.resources.getDimensionPixelSize(R.dimen.card_margin),
                0)
        }

        fun deroundCardWithBottomMargin(view : View, view2 : View? = null){

            val context = view.context
            deroundCardX(view, context.resources.getDimensionPixelSize(R.dimen.card_margin),
                0,
                context.resources.getDimensionPixelSize(R.dimen.card_margin),
                context.resources.getDimensionPixelSize(R.dimen.card_margin))
        }

        fun deroundCardWithRightMargin(view : View, view2 : View? = null){

            val context = view.context
            deroundCardX(view, view2,0,
                context.resources.getDimensionPixelSize(R.dimen.card_margin),
                context.resources.getDimensionPixelSize(R.dimen.card_margin),
                context.resources.getDimensionPixelSize(R.dimen.card_margin))
        }

        fun deroundCardHorizontal(view : View, view2 : View? = null){

            val context = view.context
            deroundCardX(view, view2,context.resources.getDimensionPixelSize(R.dimen.card_margin),0,context.resources.getDimensionPixelSize(R.dimen.card_margin),0)
        }
    }

}
