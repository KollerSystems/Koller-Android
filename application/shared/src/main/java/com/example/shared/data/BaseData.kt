package com.example.shared.data

abstract class BaseData (
    val ID : Int = -1,
){
    abstract fun diffrentDecider() : String
}