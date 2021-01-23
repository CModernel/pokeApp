package com.pedrogomez.pokeapp.pokedetail.typesadapter

import android.content.Context
import android.util.DisplayMetrics
import android.widget.LinearLayout
import com.pedrogomez.pokeapp.models.PokeType
import com.pedrogomez.pokeapp.pokedetail.views.TitleSeccion
import com.pedrogomez.pokeapp.utils.extensions.print
import com.pedrogomez.pokeapp.utils.getDrawableResByType

class TypesHelper(){

    private val listTypes = ArrayList<TitleSeccion>()

    fun inflateTypes(pokeTypes: List<PokeType>, context: Context): List<TitleSeccion>{
        "hay ${pokeTypes.size} tipos".print()
        val widthItem = getWidthDisplay(context)/pokeTypes.size
        pokeTypes.map {
            val titleSeccion = TitleSeccion(context)
            val params = LinearLayout.LayoutParams(
                widthItem,
                dpToPx(40,context)
            )
            params.topMargin = dpToPx(4,context)
            params.marginEnd = dpToPx(4,context)
            params.marginStart = dpToPx(4,context)
            params.bottomMargin = dpToPx(4,context)
            titleSeccion.layoutParams = params
            titleSeccion.setTitle(it.name)
            if(it!=pokeTypes[0]){
                titleSeccion.setBackground(
                    getDrawableResByType(
                        it
                    )
                )
            }
            listTypes.add(titleSeccion)
        }
        return listTypes.toList()
    }

    fun getWidthDisplay(context: Context): Int {
        return context.resources.displayMetrics.widthPixels
    }

    fun dpToPx(dp: Int, context: Context): Int {
        val displayMetrics = context.resources.displayMetrics
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT))
    }

}