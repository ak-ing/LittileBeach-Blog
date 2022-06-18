package com.example.littlebeachblog.ui.view.recycler_adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.littlebeachblog.ui.binding_adapter.CommonBindingAdapter

abstract class DataBindingPagingAdapter<T : Any>(diffCallback: DiffUtil.ItemCallback<T>) :
    PagingDataAdapter<T, DataBindingViewHolder<T>>(diffCallback) {

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
                getItem(position)?.let { it1 ->
                    mOnItemClickListener(
                        holder.binding,
                        it1,
                        position
                    )
                }
            }
        }

        if (this::mOnItemLongClickListener.isInitialized) {
            holder.itemView.setOnLongClickListener {
                getItem(position)?.let { it1 ->
                    mOnItemLongClickListener(
                        holder.binding,
                        it1,
                        position
                    )
                }
                true

            }
        }
        getItem(position)?.let { holder.bind(it) }
    }
}