package com.example.test_allen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import com.example.test_allen.data_Format.Data;
import com.example.test_allen.databinding.ActivityMainBinding;
import com.example.test_allen.fragment.DialogAddAndRevise;
import com.example.test_allen.fragment.Fragment1;
import com.example.test_allen.fragment.fragmentfactory.DialogAddAndReviseFactory;
import com.example.test_allen.viewmodel.RvViewModel;
import com.example.test_allen.viewmodel.RvViewModelFactory;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private FragmentManager manager;
    private FragmentTransaction transaction;
    private Fragment1 fragment1;
    private String tag = Thread.currentThread().getStackTrace()[2].getClassName();
    private ActivityMainBinding activityMainBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e("MainActivity","Create_MainActivity");
        getSupportFragmentManager().setFragmentFactory(new DialogAddAndReviseFactory(0));
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        if (savedInstanceState ==null){
            addFragment1();
        }else {
            manager = getSupportFragmentManager();
            transaction = manager.beginTransaction();
            fragment1 = (Fragment1) manager.findFragmentByTag("fragment1");
        }
        setRvViewModel();



    }

    public void addFragment1(){
        fragment1 = new Fragment1();
        //FragmentManager與transaction
        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();
        //Fragment初始設立
        transaction.add(R.id.center,fragment1,"fragment1").setReorderingAllowed(true).commitAllowingStateLoss();
    }

    public void setRvViewModel(){
        //RvModel的Factory，使ViewModel可接受建構傳入值
        RvViewModelFactory rvViewModelFactory = new RvViewModelFactory();
        RvViewModel rvViewModel = new ViewModelProvider(this, rvViewModelFactory).get(RvViewModel.class);
        activityMainBinding.setRvViewModel(rvViewModel);
        activityMainBinding.setLifecycleOwner(this);
    }




    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}



