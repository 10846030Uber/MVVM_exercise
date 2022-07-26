package com.example.test_allen.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class RvViewModelFactory implements ViewModelProvider.Factory {

    public RvViewModelFactory(){

    }
    @SuppressWarnings("UnChecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
       if(modelClass.isAssignableFrom(RvViewModel.class)){
           return (T)new RvViewModel();
       }
       throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
