package com.example.littlebeachblog.ui.view.recycler_adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.littlebeachblog.ui.binding_adapter.CommonBindingAdapter

/**

 * @Auther: 杨景

 * @datetime: 2021/6/18

 * @desc:

 */
abstract class DataBindingListAdapter<T>(diffCallback: DiffUtil.ItemCallback<T>) :
    ListAdapter<T, DataBindingViewHolder<T>>(diffCallback) {
    protected lateinit var mOnItemClickListener: (ViewDataBinding, T, Int) -> Unit
    protected lateinit var mOnItemLongClickListener: (ViewDataBinding, T, Int) -> Unit

    fun setOnItemClickListener(onItemClickListener: (bind: ViewDataBinding, item: T, index: Int) -> Unit) {
        this.mOnItemClickListener = onItemClickListener
    }

    fun setOnItemLongClickListener(onItemLongClickListener: (bind: ViewDataBinding, item: T, index: Int) -> Unit) {
        this.mOnItemLongClickListener = onItemLongClickListener
    }

    abstract fun getLayoutId(): Int

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataBindingViewHolder<T> {
        val inflate = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context),
            getLayoutId(),
            parent,
            false
        )
        return DataBindingViewHolder(inflate)
    }

    override fun onBindViewHolder(holder: DataBindingViewHolder<T>, position: Int) {
        if (this::mOnItemClickListener.isInitialized) {
            CommonBindingAdapter.onClickWithDebouncing(holder.itemView) {
                mOnItemClickListener(
                    holder.binding,
                    getItem(position),
                    position
                )
            }
        }

        if (this::mOnItemLongClickListener.isInitialized) {
            holder.itemView.setOnLongClickListener {
                mOnItemLongClickListener(
                    holder.binding,
                    getItem(position),
                    position
                )
                true

            }
        }

        holder.bind(getItem(position))
    }
}