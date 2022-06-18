package com.example.littlebeachblog.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by AK on 2022/2/18 14:10.
 * God bless my code!
 */
public class NoDispatchRecyclerView extends RecyclerView {
    public NoDispatchRecyclerView(@NonNull Context context) {
        super(context);
    }

    public NoDispatchRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public NoDispatchRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return false;
    }
}
