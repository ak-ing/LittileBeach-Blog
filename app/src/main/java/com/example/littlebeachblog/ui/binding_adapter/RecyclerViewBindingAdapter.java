package com.example.littlebeachblog.ui.binding_adapter;

import android.graphics.Rect;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.littlebeachblog.app.utils.TopLevelKt;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;

/**
 * Created by AK on 2022/1/31 15:49.
 * God bless my code!
 */
public class RecyclerViewBindingAdapter {
    public RecyclerViewBindingAdapter() {
    }

    @BindingAdapter(value = {"itemViewCacheSize"}, requireAll = false)
    public static void itemViewCacheSize(RecyclerView recyclerView, int size) {
        recyclerView.setItemViewCacheSize(size);
    }

    @BindingAdapter(value = {"bottomDecoration"}, requireAll = false)
    public static void addBottomDecoration(RecyclerView recyclerView, int dp) {
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                outRect.bottom = TopLevelKt.dp2px(view.getContext(), dp);
            }
        });
    }

    @BindingAdapter(value = {"itemDecoration"}, requireAll = false)
    public static void addItemDecoration(RecyclerView recyclerView, int dp) {
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                outRect.bottom = TopLevelKt.dp2px(view.getContext(), dp);
                outRect.top = TopLevelKt.dp2px(view.getContext(), dp);
                outRect.right = TopLevelKt.dp2px(view.getContext(), dp);
                outRect.left = TopLevelKt.dp2px(view.getContext(), dp);
            }
        });
    }

    @BindingAdapter(value = {"adapter"}, requireAll = false)
    public static void setAdapter(RecyclerView recyclerView, RecyclerView.Adapter adapter) {
        recyclerView.setAdapter(adapter);
    }

    @BindingAdapter(value = {"submitList"}, requireAll = false)
    public static void submitList(RecyclerView recyclerView, List list) {

        if (recyclerView.getAdapter() != null) {
            ListAdapter adapter = (ListAdapter) recyclerView.getAdapter();
            adapter.submitList(list);
        }

    }

    @BindingAdapter(
            value = {"notifyCurrentListChanged"},
            requireAll = false
    )
    public static void notifyCurrentListChanged(RecyclerView recyclerView, boolean notifyCurrentListChanged) {
        if (notifyCurrentListChanged) {
            recyclerView.getAdapter().notifyDataSetChanged();
        }

    }

    @BindingAdapter(
            value = {"autoScrollToTopWhenInsert", "autoScrollToBottomWhenInsert"},
            requireAll = false
    )
    public static void autoScroll(RecyclerView recyclerView, boolean autoScrollToTopWhenInsert, boolean autoScrollToBottomWhenInsert) {
        if (recyclerView.getAdapter() != null) {
            recyclerView.getAdapter().registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
                public void onItemRangeInserted(int positionStart, int itemCount) {
                    if (autoScrollToTopWhenInsert) {
                        recyclerView.scrollToPosition(0);
                    } else if (autoScrollToBottomWhenInsert) {
                        recyclerView.scrollToPosition(recyclerView.getAdapter().getItemCount());
                    }

                }
            });
        }

    }
}
