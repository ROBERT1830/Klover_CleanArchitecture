package com.robertconstantindinescu.core

import android.content.Context

sealed class UiText{
    data class DynamicString(val text:String ):UiText()
    //pass the string id from resrouces
    data class StringResource(val resId: Int): UiText()

    //function to retrieve the string
    fun asString(context: Context): String{
        //deppending on which child class from the parent class we have
        return when(this){
            is DynamicString -> text
            //read the string resoruces and return that.
            is StringResource -> context.getString(resId)
        }
    }
}
