package com.example.littlebeachblog.app;

import android.util.SparseArray;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

/**
 * Created by AK on 2022/1/31 14:21.
 * God bless my code!
 */
public class DataBindingConfig {
    private final int layout;
    private final int vmVariableId;
    private final ViewModel stateViewModel;
    private final SparseArray<Object> bindingParams = new SparseArray();

    public DataBindingConfig(@NonNull Integer layout) {
        this.layout = layout;
        vmVariableId = -1;
        stateViewModel = null;
    }

    public DataBindingConfig(@NonNull Integer layout, @NonNull Integer vmVariableId, @NonNull ViewModel stateViewModel) {
        this.layout = layout;
        this.vmVariableId = vmVariableId;
        this.stateViewModel = stateViewModel;
    }

    public int getLayout() {
        return this.layout;
    }

    public int getVmVariableId() {
        return this.vmVariableId;
    }

    public ViewModel getStateViewModel() {
        return this.stateViewModel;
    }

    public SparseArray<Object> getBindingParams() {
        return this.bindingParams;
    }

    public DataBindingConfig addBindingParam(@NonNull Integer variableId, @NonNull Object object) {
        if (this.bindingParams.get(variableId) == null) {
            this.bindingParams.put(variableId, object);
        }

        return this;
    }
}
