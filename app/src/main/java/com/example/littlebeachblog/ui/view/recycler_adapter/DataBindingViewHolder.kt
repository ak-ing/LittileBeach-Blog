package com.example.littlebeachblog.ui.view.recycler_adapter

import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.RecyclerView


/**

 * @Auther: 杨景

 * @datetime: 2021/6/18

 * @desc:

 */
open class DataBindingViewHolder<T>(val binding: ViewDataBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: T) {
        binding.apply {
            setVariable(BR.item, item)
            executePendingBindings()
        }
    }
}