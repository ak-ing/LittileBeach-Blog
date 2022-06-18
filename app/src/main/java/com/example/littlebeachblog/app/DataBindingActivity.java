package com.example.littlebeachblog.app;

import android.os.Bundle;
import android.util.SparseArray;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.example.littlebeachblog.app.utils.ActivityController;
import com.example.littlebeachblog.app.utils.UILoader;

/**
 * Created by AK on 2022/2/8 8:32.
 * God bless my code!
 */
public abstract class DataBindingActivity extends AppCompatActivity {
    private ViewDataBinding mBinding;
    private TextView mTvStrictModeTip;

    public DataBindingActivity() {
    }

    protected abstract void initViewModel();

    protected abstract DataBindingConfig getDataBindingConfig();

    protected ViewDataBinding getBinding() {
//        if (this.isDebug() && this.mBinding != null && this.mTvStrictModeTip == null) {
//            this.mTvStrictModeTip = new TextView(this.getApplicationContext());
//            this.mTvStrictModeTip.setAlpha(0.4F);
//            this.mTvStrictModeTip.setPadding(this.mTvStrictModeTip.getPaddingLeft() + 24, this.mTvStrictModeTip.getPaddingTop() + 64, this.mTvStrictModeTip.getPaddingRight() + 24, this.mTvStrictModeTip.getPaddingBottom() + 24);
//            this.mTvStrictModeTip.setGravity(1);
//            this.mTvStrictModeTip.setTextSize(10.0F);
//            this.mTvStrictModeTip.setBackgroundColor(-1);
//            String tip = this.getString(string.debug_databinding_warning, new Object[]{this.getClass().getSimpleName()});
//            this.mTvStrictModeTip.setText(tip);
//            ((ViewGroup)this.mBinding.getRoot()).addView(this.mTvStrictModeTip);
//        }

        return this.mBinding;
    }

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityController.INSTANCE.addActivity(this);
        this.initViewModel();
        DataBindingConfig dataBindingConfig = this.getDataBindingConfig();
        ViewDataBinding binding = DataBindingUtil.setContentView(this, dataBindingConfig.getLayout());
        binding.setLifecycleOwner(this);
        if (dataBindingConfig.getVmVariableId() != -1) {
            binding.setVariable(dataBindingConfig.getVmVariableId(), dataBindingConfig.getStateViewModel());
        }
        SparseArray<Object> bindingParams = dataBindingConfig.getBindingParams();
        int i = 0;

        for (int length = bindingParams.size(); i < length; ++i) {
            binding.setVariable(bindingParams.keyAt(i), bindingParams.valueAt(i));
        }
        binding.executePendingBindings();

        this.mBinding = binding;
    }

    public boolean isDebug() {
        return this.getApplicationContext().getApplicationInfo() != null && (this.getApplicationContext().getApplicationInfo().flags & 2) != 0;
    }

    protected void onDestroy() {
        super.onDestroy();
        ActivityController.INSTANCE.removeActivity(this);
        this.mBinding.unbind();
        this.mBinding = null;
    }
}
