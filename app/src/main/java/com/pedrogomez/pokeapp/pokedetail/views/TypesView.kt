package com.pedrogomez.pokeapp.pokedetail.views

import android.content.Context
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.pedrogomez.pokeapp.R
import com.pedrogomez.pokeapp.databinding.ViewTypesBinding
import com.pedrogomez.pokeapp.models.PokeType
import com.pedrogomez.pokeapp.utils.extensions.print
import com.pedrogomez.pokeapp.utils.getDrawableResByType

class TypesView : LinearLayout {

    lateinit var binding : ViewTypesBinding

    constructor(context: Context) : super(context) {
        init(null, 0)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init(attrs, defStyle)
    }

    private val listTypes = ArrayList<TitleSeccion>()

    private fun init(attrs: AttributeSet?, defStyle: Int) {
        binding = ViewTypesBinding.inflate(
            LayoutInflater.from(context),
            this
        )
        val a = context.obtainStyledAttributes(
            attrs,
            R.styleable.TypesView,
            defStyle,
            0
        )

        a.recycle()

    }

    fun setTypes(
        pokeTypes: List<PokeType>
    ){
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
        listTypes.map {
            binding.lyTypes.addView(
                it
            )
        }
    }

    fun getWidthDisplay(context: Context): Int {
        return context.resources.displayMetrics.widthPixels
    }

    fun dpToPx(dp: Int, context: Context): Int {
        val displayMetrics = context.resources.displayMetrics
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT))
    }

}