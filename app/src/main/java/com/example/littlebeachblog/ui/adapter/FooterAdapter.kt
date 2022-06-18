package com.example.littlebeachblog.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.littlebeachblog.databinding.FooterItemBinding

/**
 * Created by AK on 2022/2/7 18:57.
 * God bless my code!
 */
class FooterAdapter(val retry: () -> Unit) : LoadStateAdapter<FooterAdapter.ViewHolder>() {

    class ViewHolder(val bind: FooterItemBinding) : RecyclerView.ViewHolder(bind.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): ViewHolder {
        val bind = FooterItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        bind.retryTv.setOnClickListener { retry() }
        return ViewHolder(bind)
    }

    override fun onBindViewHolder(holder: ViewHolder, loadState: LoadState) {
        holder.bind.retryTv.isVisible = loadState is LoadState.Error
        holder.bind.group.isVisible = loadState is LoadState.Loading
    }

}
