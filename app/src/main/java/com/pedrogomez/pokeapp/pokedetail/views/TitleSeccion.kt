package com.pedrogomez.pokeapp.pokedetail.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.pedrogomez.pokeapp.R
import com.pedrogomez.pokeapp.databinding.TitleSeccionBinding
import com.pedrogomez.pokeapp.utils.extensions.getColor
import com.pedrogomez.pokeapp.utils.extensions.getDrawable
import com.pedrogomez.pokeapp.utils.getDrawableResByType

/**
 * TODO: document your custom view class.
 */
class TitleSeccion : ConstraintLayout {

    lateinit var binding : TitleSeccionBinding

    constructor(context: Context) : super(context) {
        init(null, 0)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        init(attrs, defStyle)
    }

    private fun init(attrs: AttributeSet?, defStyle: Int) {

        binding = TitleSeccionBinding.inflate(
            LayoutInflater.from(context),
            this,
            true
        )

        val a = context.obtainStyledAttributes(
            attrs, R.styleable.TitleSeccion, defStyle, 0
        )

        val titleSection = a.getString(
            R.styleable.TitleSeccion_tsTitle
        )

        val titleColor = a.getColor(
            R.styleable.TitleSeccion_tsColorTitle,
            getColor(R.color.black)
        )

        binding.tvTitleSection.text = titleSection?:""

        binding.tvTitleSection.setTextColor(titleColor)

        a.recycle()

    }

    fun setTitle(title:String){
        binding.tvTitleSection.text = title
    }

    fun setBackground(bg:Int){
        background = getDrawable(bg)
    }

}