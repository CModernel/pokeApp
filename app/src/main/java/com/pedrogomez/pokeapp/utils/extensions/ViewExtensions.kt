package com.pedrogomez.pokeapp.utils.extensions

import android.content.Context
import android.content.res.Resources
import android.os.Build
import android.view.View

fun View.show(){
    this.visibility = View.VISIBLE
}

fun View.remove(){
    this.visibility = View.GONE
}

fun View.hide(){
    this.visibility = View.INVISIBLE
}

fun View.getColor(color:Int):Int{
    return if(Build.VERSION.SDK_INT>=23){
        this.resources.getColor(color,null)
    }else{
        this.resources.getColor(color)
    }
}