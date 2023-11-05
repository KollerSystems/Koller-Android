package com.norbert.koller.shared

import android.app.Application
import android.app.Dialog
import android.content.ClipData
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import android.icu.text.SimpleDateFormat
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.AppBarLayout
import com.norbert.koller.shared.data.UserData
import com.norbert.koller.shared.fragments.CalendarFragment
import com.norbert.koller.shared.fragments.HomeFragment
import com.norbert.koller.shared.fragments.NotificationsFragment
import com.norbert.koller.shared.fragments.RoomFragment
import com.norbert.koller.shared.fragments.StudentHostelFragment
import com.norbert.koller.shared.fragments.UserFragment
import com.google.android.material.card.MaterialCardView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.color.DynamicColors
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.shape.ShapeAppearanceModel
import com.google.android.material.shape.Shapeable
import com.google.android.material.textfield.TextInputLayout
import com.norbert.koller.shared.data.FilterDateData
import com.norbert.koller.shared.data.FiltersData
import com.norbert.koller.shared.fragments.RoomsFragment
import com.norbert.koller.shared.fragments.UserOutgoingPermanentFragment
import com.norbert.koller.shared.fragments.UserOutgoingTemporaryFragment
import com.norbert.koller.shared.fragments.UsersFragment
import com.norbert.koller.shared.fragments.bottomsheet.ItemListDialogFragment
import com.norbert.koller.shared.recycleradapter.ListItem
import java.util.Date


open class MyApplication : Application() {


    override fun onCreate() {
        super.onCreate()
        DynamicColors.applyToActivitiesIfAvailable(this)
    }

    companion object Comp{

        lateinit var version : String

        lateinit var openSettings: (context : Context) -> Unit
        lateinit var openLogin: (context : Context) -> Unit
        lateinit var openMain: (context : Context) -> Unit
        lateinit var openProfile: (context : Context) -> Unit

        lateinit var homeFragment: () -> HomeFragment
        lateinit var calendarFragment: () -> CalendarFragment
        lateinit var studentHostelFragment: () -> StudentHostelFragment
        lateinit var notificationFragment: () -> NotificationsFragment

        lateinit var roomFragment: (RID : Int) -> RoomFragment
        lateinit var userFragment: (UID : Int) -> UserFragment

        var roomsFragment: () -> RoomsFragment = { RoomsFragment() }
        var usersFragment: () -> UsersFragment = { UsersFragment() }

        var userOutgoingTemporaryFragment: (UID : Int) -> UserOutgoingTemporaryFragment = { UserOutgoingTemporaryFragment() }
        var userOutgoingPermanentFragment: (UID : Int) -> UserOutgoingPermanentFragment = { UserOutgoingPermanentFragment() }

        fun setupDrpd(fragmentManager : FragmentManager, chip : Chip){

            chip.setOnClickListener {

                val dprdb = MaterialDatePicker.Builder.dateRangePicker()

                if (chip.tag is FilterDateData) {
                    dprdb.setSelection((chip.tag as FilterDateData).filterFrom)
                }

                val drpd = dprdb.build()

                drpd.addOnPositiveButtonClickListener { selection ->

                    var stringForChip = java.text.SimpleDateFormat(shortMonthDayFormat).format(selection.first)
                    if (selection.first != selection.second) {
                        stringForChip += " - ${java.text.SimpleDateFormat(shortMonthDayFormat).format(selection.second)}"
                    }
                    chip.tag = FilterDateData("Date", selection)
                    if(chip.text.toString() != stringForChip) {
                        chip.text = stringForChip
                    }

                    chip.checkByPass(true)

                    addCloseOptionToFilterChip(chip, R.string.date)
                }

                drpd.show(fragmentManager, "MATERIAL_DATE_RANGE_PICKER")

            }
        }

        fun addCloseOptionToFilterChip(chip : Chip, localizedFilterId : Int){
            chip.closeIcon = AppCompatResources.getDrawable(chip.context, R.drawable.close_thick)
            chip.setOnCloseIconClickListener {
                chip.restoreDropDown()
                chip.tag = null
                chip.text = chip.context.getString(localizedFilterId)
                chip.checkByPass(false)
            }
        }

        fun setToolbarToBottomViewColor(bottomView : View, window:Window){
            bottomView.post{
                val navViewColor = getPixelColorFromView(bottomView, 0, 0)
                window.navigationBarColor = navViewColor
            }
        }

        fun orderSingleNumber(context : Context, number : String) : String{


            return when (number.last()){
                '1' -> number + context.getString(R.string.st)
                '2' -> number + context.getString(R.string.nd)
                '3' -> number + context.getString(R.string.rd)
                else -> number + context.getString(R.string.th)
            }
        }

        fun setupCheckBoxList(fragmentManager: FragmentManager, chip : Chip, filterName : String, localizedFilterId : Int, arrayList : ArrayList<ListItem>){

            chip.setOnClickListener {

                var filterTo : ArrayList<String>? = null
                if(chip.tag is FiltersData){
                    filterTo = (chip.tag as FiltersData).filterTo
                }

                val dialog = ItemListDialogFragment(arrayList, filterTo)


                dialog.getValuesOnFinish = {values, locNames ->

                    chip.tag = FiltersData(filterName, values)
                    editChipBasedOnResponse(chip, values, locNames, localizedFilterId)

                }

                dialog.show(fragmentManager, ItemListDialogFragment.TAG)


            }

        }

        fun setupDbd(textView : TextView) : MaterialDatePicker<Long> {

            val dpdb = MaterialDatePicker.Builder.datePicker()

            if(textView.text.isNullOrEmpty()){
                dpdb.setSelection(textView.tag as Long)
            }

            val dpd = dpdb.build()

            dpd.addOnPositiveButtonClickListener {selection ->


                textView.tag = selection
                textView.setText(java.text.SimpleDateFormat(shortMonthDayFormat).format(selection))


            }

            return dpd
        }


        fun getPixelColorFromView(view: View, x: Int, y: Int): Int {
            val drawingCache = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(drawingCache)
            view.draw(canvas)
            return drawingCache.getPixel(x, y)
        }

        fun openActivity(context : Context, className : Class<*>){
            val intent = Intent(context, className)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }

        fun visibilityOn(boolean : Boolean) : Int{
            return if(!boolean){
                GONE
            } else{
                VISIBLE
            }
        }

        fun visibilityOn(chip : Chip, int : Int){
            if(int == 0){
                chip.visibility = GONE
            } else{
                chip.visibility = VISIBLE
                chip.text = int.toString()
            }
        }


        fun visibilityOn(chip : Chip, string : String?){
            if(string.isNullOrBlank()){
                chip.visibility = GONE
            } else{
                chip.visibility = VISIBLE
                chip.text = string
            }
        }

        fun visibilityOn(chip : Chip, date : Date?){
            if(date == null){
                chip.visibility = GONE
            } else{
                chip.visibility = VISIBLE
                chip.text = SimpleDateFormat(shortMonthDayTimeFormat).format(date)
            }
        }

        fun timeToString(hours : Int, minutes : Int) : String{
            return (hours).toString().padStart(2, '0')+":"+(minutes).toString().padStart(2, '0')
        }

        fun timeFromString(time : String?) : Pair<Int, Int>{

            if(time.isNullOrEmpty()) return Pair(12,0)

            val values : List<String> = time.split(":")
            val hours : Int = values[0].toInt()
            val minutes : Int = values[1].toInt()

            return Pair(hours, minutes)
        }

        fun roundRecyclerItemsVerticallyWithSeparator(view : View, position : Int, pagingDataAdapter : PagingDataAdapter<Any, RecyclerView.ViewHolder>){

            if(pagingDataAdapter.getItemViewType(position) == 1) return

            val context = view.context

            Log.d("INFO", position.toString() +": " + pagingDataAdapter.getItemViewType(position))


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

                var shapeAppearance = ShapeAppearanceModel.builder(context, R.style.overlaySmallRoundedCard, 0).build()
                (view as MaterialCardView).shapeAppearanceModel = shapeAppearance

                shapeAppearance = ShapeAppearanceModel.builder(context, R.style.overlaySmallRoundedCardTop, 0).build()
                (view2 as Shapeable).shapeAppearanceModel = shapeAppearance

                (view.layoutParams as? ViewGroup.MarginLayoutParams)?.setMargins(0,0,0,0)
                view.requestLayout()
            }
            else if(position == 0){

                var shapeAppearance = ShapeAppearanceModel.builder(context, R.style.overlaySmallRoundedCardLeft, 0).build()
                (view as MaterialCardView).shapeAppearanceModel = shapeAppearance

                shapeAppearance = ShapeAppearanceModel.builder(context, R.style.overlaySmallRoundedCardTopLeft, 0).build()
                (view2 as Shapeable).shapeAppearanceModel = shapeAppearance

                (view.layoutParams as? ViewGroup.MarginLayoutParams)?.setMargins(0,0,context.resources.getDimensionPixelSize(R.dimen.card_margin),0)
                view.requestLayout()
            }
            else if (position == size-1){

                var shapeAppearance = ShapeAppearanceModel.builder(context, R.style.overlaySmallRoundedCardRight, 0).build()
                (view as MaterialCardView).shapeAppearanceModel = shapeAppearance

                shapeAppearance = ShapeAppearanceModel.builder(context, R.style.overlaySmallRoundedCardTopRight, 0).build()
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

            (view as MaterialCardView).shapeAppearanceModel = shapeAppearance
            if(view2 != null) (view2 as Shapeable).shapeAppearanceModel = shapeAppearance

            (view.layoutParams as? ViewGroup.MarginLayoutParams)?.setMargins(leftPadding,topPadding,rightPadding,bottomPadding)
            view.requestLayout()
        }

        fun roundCardX(view : View, overlay : Int, leftPadding : Int, topPadding : Int, rightPadding : Int, bottomPadding : Int){
            roundCardX(view, null, overlay, leftPadding, topPadding, rightPadding, bottomPadding)
        }

        fun roundCardBottom(view : View, view2 : View? = null){
            val context = view.context
            roundCardX(view, view2, R.style.overlayRoundedCardBottom,0,context.resources.getDimensionPixelSize(R.dimen.card_margin),0,0)
        }

        fun roundCardBottomLeft(view : View, view2 : View? = null){
            val context = view.context
            roundCardX(view, view2, R.style.overlayRoundedCardBottomLeft,0,context.resources.getDimensionPixelSize(R.dimen.card_margin),context.resources.getDimensionPixelSize(R.dimen.card_margin),0)
        }

        fun roundCardBottomRight(view : View, view2 : View? = null){
            val context = view.context
            roundCardX(view, view2, R.style.overlayRoundedCardBottomRight,context.resources.getDimensionPixelSize(R.dimen.card_margin),context.resources.getDimensionPixelSize(R.dimen.card_margin),0,0)
        }

        fun roundCardTop(view : View, view2 : View? = null){
            val context = view.context
            roundCardX(view, view2, R.style.overlayRoundedCardTop,0,0,0,context.resources.getDimensionPixelSize(R.dimen.card_margin))
        }

        fun roundCardTopLeft(view : View, view2 : View? = null){
            val context = view.context
            roundCardX(view, view2, R.style.overlayRoundedCardTopLeft,0,0,context.resources.getDimensionPixelSize(R.dimen.card_margin),context.resources.getDimensionPixelSize(R.dimen.card_margin))
        }

        fun roundCardTopRight(view : View, view2 : View? = null){
            val context = view.context
            roundCardX(view, view2, R.style.overlayRoundedCardTopRight,context.resources.getDimensionPixelSize(R.dimen.card_margin),0,0,context.resources.getDimensionPixelSize(R.dimen.card_margin))
        }

        fun roundCardLeft(view : View, view2 : View? = null){
            val context = view.context
            roundCardX(view, view2, R.style.overlayRoundedCardLeft,0,0,context.resources.getDimensionPixelSize(R.dimen.card_margin),0)
        }

        fun roundCardRight(view : View, view2 : View? = null){
            val context = view.context
            roundCardX(view, view2, R.style.overlayRoundedCardRight, context.resources.getDimensionPixelSize(R.dimen.card_margin),0,0,0)
        }

        fun roundCard(view : View, view2 : View? = null){
            roundCardX(view, view2, R.style.overlayRoundedCard, 0,0,0,0)
        }

        fun deroundCardX(view : View, leftMargin : Int, topMargin : Int, rightMargin : Int, bottomMargin : Int){

            deroundCardX(view, null, leftMargin, topMargin, rightMargin, bottomMargin)
        }

        fun deroundCardX(view : View, view2 : View?, leftMargin : Int, topMargin : Int, rightMargin : Int, bottomMargin : Int){

            (view as MaterialCardView).shapeAppearanceModel = ShapeAppearanceModel()
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


        const val minLengthBeforeDismiss : Int = 3

        val shortMonthDayTimeFormat = "MMM d. HH:mm"
        val yearShortMonthDayFormat = "yyyy. MMM d."
        val monthDay = "MMMM d."
        val shortMonthDayFormat = "MMM d."
        val timeFormat = "HH:mm"
        val apiFormat = "YYYY-MM-dd"

        fun createUserDescription(userData : UserData): String{
            return userData.Group + " • " + userData.RID  + " • " + userData.Class?.Class
        }

        fun setupBottomSheet(dialog : Dialog){
            dialog.window!!.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN)
        }

        fun setupActivity(window : Window){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                window.setDecorFitsSystemWindows(false)
                window.isNavigationBarContrastEnforced = false
            }
        }

        fun setClipboard(context: Context, text: String) {

                val clipboard =
                    context.getSystemService(CLIPBOARD_SERVICE) as android.content.ClipboardManager
                val clip = ClipData.newPlainText("Koller", text)
                clipboard.setPrimaryClip(clip)

        }

        fun longEnoughToAsk(til : TextInputLayout): Boolean{
            return til.editText!!.text.toString().replace(" ", "").length>= minLengthBeforeDismiss
        }


        fun convertDpToPixel(dp: Int, context: Context): Int {
            return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp.toFloat(),
                context.resources.displayMetrics
            ).toInt()
        }

        fun convertSpToPixel(sp: Int, context: Context): Int {
            return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP,
                sp.toFloat(),
                context.resources.displayMetrics
            ).toInt()
        }

        fun isOnline(context: Context): Boolean {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (connectivityManager != null) {
                val capabilities =
                    connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
                if (capabilities != null) {
                    if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                        Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                        return true
                    } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                        Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                        return true
                    } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                        Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                        return true
                    }
                }
            }
            return false
        }

        fun focusEdittext(ctx : Context, edittext : TextInputLayout){
            edittext.isFocusableInTouchMode = true
            edittext.requestFocus()
            val imm = ctx.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(edittext, InputMethodManager.SHOW_IMPLICIT)
        }

        fun getAttributeColor(context: Context, attributeId: Int): Int {
            val typedValue = TypedValue()
            context.theme.resolveAttribute(attributeId, typedValue, true)
            val colorRes = typedValue.resourceId
            var color = -1
            try {
                color = ContextCompat.getColor(context, colorRes)
            } catch (e: Resources.NotFoundException) {
                Log.w("ERROR", "Not found color resource by id: $colorRes")
            }
            return color
        }

        fun timeTo(to : Int):String{
            return "-\n" + minuteToHourMinuteFormat(to)
        }

        fun timeFromTo(from : Int, to : Int):String{
            return minuteToHourMinuteFormat(from) + "\n-\n" + minuteToHourMinuteFormat(to)
        }

        fun timeFrom(from : Int):String{
            return minuteToHourMinuteFormat(from) + "\n-"
        }

        fun minuteToHourMinuteFormat(minute : Int):String{


            val hours : Int = ((minute) / 60)
            val minutesWithoutHours : Int = (minute - (hours * 60))

            return (hours.toString()+":"+minutesWithoutHours.toString().padStart(2, '0'))
        }


        fun getStringResourceByName(context : Context, stringName: String): String? {

                val resId = context.resources.getIdentifier(stringName, "string", context.packageName)
                return context.getString(resId)

        }

        fun camelToSnakeCase(string : String) = string.fold(StringBuilder(string.length)) { acc, c ->
            if (c in 'A'..'Z') (if (acc.isNotEmpty()) acc.append('_') else acc).append(c + ('a' - 'A'))
            else acc.append(c)
        }.toString()


        fun prefixLetterToSymbol(letter : String) : Char{
            if(letter == "n") return '-'
            return '+'
        }

        fun prefixIntigerToSymbol(index : Int) : Char{
            if(index == 0) return '+'
            return '-'
        }

        fun createApiSortString(chipGroup : ChipGroup, basedOn : String) : String{
            val chip : Chip = chipGroup.findViewById(chipGroup.checkedChipId)
            return prefixIntigerToSymbol((chip.parent as ViewGroup).indexOfChild(chip)) + basedOn
        }

        fun getApiSortString(chipGroup : ChipGroup?) : String{
            if(chipGroup == null) return ""
            val chip : Chip = chipGroup.findViewById(chipGroup.checkedChipId)
            return chip.tag.toString()
        }

        fun createApiFilter(vararg filterGroups : FiltersData) : String{

            return createApiFilter(filterGroups)
        }


        fun createApiFilter(filterGroups: Array<out FiltersData>, filterDateGroups: Array<out FilterDateData>? = null) : String{
            var finalString = ""

            for (filterGroup in filterGroups) {

                val filterName = filterGroup.filterName

                for (argument in filterGroup.filterTo) {

                        finalString += "${filterName}:${argument},"

                }
            }
            if(filterDateGroups!=null) {
                for (filterGroup in filterDateGroups) {


                    if(filterGroup.filterFrom != null) {

                        val filterName = filterGroup.filterName
                        finalString += "${filterName}[gte]:${SimpleDateFormat(apiFormat).format(filterGroup.filterFrom!!.first)+ "T00:00:00.000Z"},${filterName}[lte]:${SimpleDateFormat(apiFormat).format(filterGroup.filterFrom!!.second) + "T00:00:00.000Z"},"
                    }

                }
            }
            finalString.dropLast(1)

            return finalString
        }

        fun editChipBasedOnResponse(chip : Chip, values : ArrayList<String>, locNames : String, defaultNameID : Int){
            val hasValues = values.size != 0
            chip.checkByPass(hasValues)
            if(hasValues) {
                if(chip.text.toString() != locNames){
                    chip.text = locNames
                    addCloseOptionToFilterChip(chip, defaultNameID)
                }


            }
            else{
                val string = chip.context.getString(defaultNameID)
                if(chip.text.toString() != string) {
                    chip.text = string
                    chip.restoreDropDown()
                }
            }
        }

        fun setupActivityToolbar(backgroundCard : MaterialCardView, appBarLayout: AppBarLayout){

            appBarLayout.addOnOffsetChangedListener { _, verticalOffset ->
                val collapsedSize: Float = -570f
                backgroundCard.alpha = verticalOffset / collapsedSize
            }
        }

        fun createClassesText(context: Context, lesson : Int, length : Int) : String{

            var description = ""

            for (i in lesson..<lesson+length){

                val orderedNumber = MyApplication.orderSingleNumber(context, (i + 1).toString())

                if(i < length-1-1) {

                    description += "${orderedNumber}, "
                }
                else if (i < length-1){
                    description += "${orderedNumber} ${context.getString(R.string.and)} "
                }
                else{
                    description += "${orderedNumber} ${ context.getString(R.string.lesson).lowercase()}"
                }
            }

            return description
        }
    }
}

fun Chip.checkByPass(isChecked : Boolean){
    isCheckable = true
    this.isChecked = isChecked
    isCheckable = false
}

fun Chip.restoreDropDown(){
    closeIcon = AppCompatResources.getDrawable(context, R.drawable.arrow_drop)
    setOnCloseIconClickListener(null)
}