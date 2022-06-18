package com.example.littlebeachblog.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.littlebeachblog.R

/**
 * Created by AK on 2022/3/1 19:24.
 * God bless my code!
 */
class ValueView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {
    private val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ValueView)

    private val title = typedArray.getString(R.styleable.ValueView_text)
    private val value = typedArray.getString(R.styleable.ValueView_value)

    private var tvTitle: TextView
    private var tvValue: TextView

    init {
        LayoutInflater.from(context).inflate(R.layout.dirrorx_value_view_layout, this)
        tvTitle = findViewById(R.id.tvTitle)
        tvValue = findViewById(R.id.tvValue)
        tvTitle.text = title
        tvValue.text = value
    }

    fun setTitle(string: String) {
        tvTitle.text = string
    }

    fun setValue(string: String?) {
        tvValue.text = string
    }
}