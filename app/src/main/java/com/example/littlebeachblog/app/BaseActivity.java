package com.example.littlebeachblog.app;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.littlebeachblog.R;
import com.example.littlebeachblog.app.utils.AdaptScreenUtils;
import com.example.littlebeachblog.app.utils.ScreenUtils;
import com.example.littlebeachblog.app.utils.TopLevelKt;
import com.example.littlebeachblog.app.utils.UILoader;

/**
 * Created by AK on 2022/2/8 8:34.
 * God bless my code!
 */
public abstract class BaseActivity extends DataBindingActivity {

    private ViewModelProvider mActivityProvider;
    private ViewModelProvider mApplicationProvider;
    //加载框
    private Dialog loadDialog;
    //状态加载
    private UILoader mUiLoader = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadDialog = new Dialog(this, R.style.loadDialog);
        loadDialog.setContentView(View.inflate(this, R.layout.dialog_loading, null));
        loadDialog.setCanceledOnTouchOutside(false);
        loadDialog.setCancelable(false);
    }

    protected void isShowLoad(boolean isShow) {
        if (isShow) loadDialog.show();
        else loadDialog.hide();
        //注意要再Activity销毁之前关闭
    }

    /**
     * 如果存在，获得UILoader对象；否则为null
     *
     * @return
     */
    public UILoader getUiLoader() {
        return mUiLoader;
    }

    /**
     * 初始化UI加载器
     *
     * @param successViewId -> 目标view
     */
    public void initUILoader(int successViewId) {
        this.mUiLoader = TopLevelKt.insertUiLoader(getBinding().getRoot(), successViewId);
    }

    protected void setToolBar(int id) {
        setSupportActionBar(findViewById(id));
    }

    protected void initToolbar(int id, String title) {
        setSupportActionBar(findViewById(id));
        if (TextUtils.isEmpty(title)) {
            initActionBar("");
        } else {
            initActionBar(title);
        }
    }

    protected void initActionBar(String title) {
        ActionBar ab = getSupportActionBar();
        if (ab == null) return;
        ab.setTitle(title);
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setHomeButtonEnabled(true);
    }

    @Override
    protected void onDestroy() {
        if (loadDialog != null) {
            loadDialog.dismiss();
        }
        super.onDestroy();
    }

    protected <T extends ViewModel> T getActivityScopeViewModel(@NonNull Class<T> modelClass) {
        if (mActivityProvider == null) {
            mActivityProvider = new ViewModelProvider(this);
        }
        return mActivityProvider.get(modelClass);
    }

    protected <T extends ViewModel> T getApplicationScopeViewModel(@NonNull Class<T> modelClass) {
        if (mApplicationProvider == null) {
            mApplicationProvider = new ViewModelProvider((App) this.getApplicationContext());
        }
        return mApplicationProvider.get(modelClass);
    }

    @Override
    public Resources getResources() {
        if (ScreenUtils.isPortrait()) {
            return AdaptScreenUtils.adaptWidth(super.getResources(), 360);
        } else {
            return AdaptScreenUtils.adaptHeight(super.getResources(), 640);
        }
    }

    protected void hideSoftKeyboard() {
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0); //获取当前焦点
        }
    }

    protected void toggleSoftInput() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    protected void openUrlInBrowser(String url) {
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

}
