package com.example.littlebeachblog.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import net.mikaelzero.mojito.tools.dp2px
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.littlebeachblog.R
import com.google.android.material.card.MaterialCardView

/**
 * Copy by DsoMusic on 2022/2/25 16:24.
 * God bless my code!
 */
class ItemLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : MaterialCardView(context, attrs) {
    private val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ItemLayout)
    private val text = typedArray.getString(R.styleable.ItemLayout_text)
    private val itemType = typedArray.getInt(R.styleable.ItemLayout_itemType, 1)

    init {
        cardElevation = 0f
        radius = 0f
        LayoutInflater.from(context).inflate(R.layout.dirrorx_item_layout, this)
        val tvItem = findViewById<TextView>(R.id.tvItem)
        val ivGoto = findViewById<ImageView>(R.id.ivGoto)

        tvItem.text = text

        when (itemType) {
            // no
            0 -> ivGoto.visibility = View.INVISIBLE
            // goto
            1 -> ivGoto.visibility = View.VISIBLE
            //
            2 -> {
                (tvItem.layoutParams as ConstraintLayout.LayoutParams).apply {
                    marginStart = dp2px(54)
                }
                ivGoto.alpha = 0f
            }
        }
    }

}