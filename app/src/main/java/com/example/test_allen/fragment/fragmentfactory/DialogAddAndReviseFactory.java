package com.example.test_allen.fragment.fragmentfactory;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentFactory;

import com.example.test_allen.data_Format.Data;
import com.example.test_allen.fragment.DialogAddAndRevise;

public class DialogAddAndReviseFactory extends FragmentFactory {
    private int position;
    public DialogAddAndReviseFactory(int position){
        super();
        this.position = position;
    }

    @NonNull
    @Override
    public Fragment instantiate(@NonNull ClassLoader classLoader, @NonNull String className) {
    Class<? extends Fragment> fragmentClass = loadFragmentClass(classLoader,className);
    if(fragmentClass == DialogAddAndRevise.class){
        return new DialogAddAndRevise(position);
    }else {
        return super.instantiate(classLoader, className);
    }
    }
}
