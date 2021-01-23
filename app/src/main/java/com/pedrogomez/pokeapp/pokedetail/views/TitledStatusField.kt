package com.pedrogomez.pokeapp.pokedetail.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.pedrogomez.pokeapp.R
import com.pedrogomez.pokeapp.databinding.TitledStatusFieldBinding

class TitledStatusField : ConstraintLayout {

    lateinit var binding : TitledStatusFieldBinding

    constructor(context: Context) : super(context) {
        init(null, 0)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init(attrs, defStyle)
    }

    private fun init(attrs: AttributeSet?, defStyle: Int) {
        binding = TitledStatusFieldBinding.inflate(
            LayoutInflater.from(context),
            this,
            true
        )
        val a = context.obtainStyledAttributes(
                attrs,
                R.styleable.TitledStatusField,
                defStyle,
                0
        )

        val titleStatus = a.getString(
            R.styleable.TitledStatusField_tsfTitle
        )

        binding.tvTitleStatus.text = titleStatus?:""

        a.recycle()

    }

    fun setValue(value:Int){
        binding.tvIValueStatus.text = "$value"
    }

}