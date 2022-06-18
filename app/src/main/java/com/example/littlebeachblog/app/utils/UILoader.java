package com.example.littlebeachblog.app.utils;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

import com.example.littlebeachblog.R;
import com.example.littlebeachblog.app.App;
import com.example.littlebeachblog.data.config.ApiResponse;

/**
 * Created by AK on 2021/1/14 23:03.
 * God bless my code!
 */
public abstract class UILoader extends FrameLayout {

    private View mLoadingView;
    private View mSuccessView;
    private View mNetworkErrorView;
    private View mEmptyView;
    private OnRetryClickListener mOnRetryClickListener = null;

    public enum UIStatus {
        LOADING, SUCCESS, NETWORK_ERROR, EMPTY, NONE
    }

    private UIStatus mCurrentStatus = UIStatus.NONE;

    public UILoader(@NonNull Context context) {
        this(context, null);
    }

    public UILoader(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public UILoader(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    /**
     * 获取当前状态
     *
     * @return
     */
    public UIStatus getCurrentStatus() {
        return mCurrentStatus;
    }

    /**
     * 根据请求结果选择状态
     *
     * @param it
     * @return 成功且有数据 -> true , 否则为空
     */
    public Boolean with(ApiResponse it) {
        if (it.getOrNull() == null || !it.isSuccess()) {
            error();
            return null;
        } else if (it.isNullOrEmpty()) {
            empty();
            return null;
        } else {
            show();
            return true;
        }
    }

    /**
     * 显示成功页面
     */
    public void show() {
        updateStatus(UIStatus.SUCCESS);
    }

    /**
     * 显示错误页面
     */
    public void error() {
        updateStatus(UIStatus.NETWORK_ERROR);
    }

    /**
     * 显示空数据页面
     */
    public void empty() {
        updateStatus(UIStatus.EMPTY);
    }

    /**
     * 显示加载中
     */
    public void loading() {
        updateStatus(UIStatus.LOADING);
    }

    /**
     * 更新UI
     *
     * @param status
     */
    public void updateStatus(UIStatus status) {
        mCurrentStatus = status;
        //更新UI
        App.getHandler().post(() -> {
            switchUIByCurrentStatus();
            loadAnim();
        });
    }

    /**
     * 启动/停止加载动画
     */
    private void loadAnim() {
        AppCompatImageView img = mLoadingView.findViewById(R.id.loading_img);
        AnimationDrawable anima = (AnimationDrawable) img.getDrawable();
        if (mCurrentStatus == UIStatus.LOADING) {
            anima.start();
        } else if (anima.isRunning()) {
            anima.stop();
        }
    }

    /**
     * 初始化UI
     */
    private void init() {
        switchUIByCurrentStatus();
    }

    private void switchUIByCurrentStatus() {
        //加载中
        if (mLoadingView == null) {
            mLoadingView = getLoadingView();
            addView(mLoadingView);
        }
        mLoadingView.setVisibility(mCurrentStatus == UIStatus.LOADING ? VISIBLE : GONE);

        //成功
        if (mSuccessView == null) {
            mSuccessView = getSuccessView(this);
            addView(mSuccessView);
        }
        mSuccessView.setVisibility(mCurrentStatus == UIStatus.SUCCESS ? VISIBLE : GONE);

        //网络错误页面
        if (mNetworkErrorView == null) {
            mNetworkErrorView = getNetworkErrorView();
            addView(mNetworkErrorView);
        }
        mNetworkErrorView.setVisibility(mCurrentStatus == UIStatus.NETWORK_ERROR ? VISIBLE : GONE);

        //数据为空的界面
        if (mEmptyView == null) {
            mEmptyView = getEmptyView();
            addView(mEmptyView);
        }
        mEmptyView.setVisibility(mCurrentStatus == UIStatus.EMPTY ? VISIBLE : GONE);
    }

    private View getEmptyView() {
        View emptyView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_empty_view, this, false);
        emptyView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // 重新加载数据
                if (mOnRetryClickListener != null) {
                    mOnRetryClickListener.onRetryClick();
                }
            }
        });
        return emptyView;
    }

    private View getNetworkErrorView() {
        View networkErrorView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_error_view, this, false);
        networkErrorView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // 重新加载数据
                if (mOnRetryClickListener != null) {
                    mOnRetryClickListener.onRetryClick();
                }
            }
        });
        return networkErrorView;
    }

    protected abstract View getSuccessView(ViewGroup container);

    private View getLoadingView() {
        return LayoutInflater.from(getContext()).inflate(R.layout.fragment_loading_view, this, false);
    }

    /**
     * 设置重新加载事件
     *
     * @param listener
     */
    public void setOnRetryClickListener(OnRetryClickListener listener) {
        this.mOnRetryClickListener = listener;
    }

    public interface OnRetryClickListener {
        void onRetryClick();
    }
}
