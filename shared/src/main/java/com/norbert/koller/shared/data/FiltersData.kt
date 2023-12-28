package com.norbert.koller.shared.data

class FiltersData(filterName: String, var filterTo : ArrayList<String>) : FilterBaseData(filterName)

class FilterDateData(filterName: String, var filterFrom : androidx.core.util.Pair<Long, Long>?) : FilterBaseData(filterName)

open class FilterBaseData(var filterName : String)

