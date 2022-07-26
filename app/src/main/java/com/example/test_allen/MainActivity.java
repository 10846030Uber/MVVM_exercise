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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
    //RvModel的Factory，使ViewModel可接受建構傳入值
    private RvViewModelFactory rvViewModelFactory;
    private RvViewModel rvViewModel;
    private Fragment1 fragment1;
//    private Fragment currentFragment;
    private String tag = Thread.currentThread().getStackTrace()[2].getClassName();
    private ActivityMainBinding activityMainBinding;
    private ClickListenerRv clickListenerRv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e("MainActivity","Create_MainActivity");
        getSupportFragmentManager().setFragmentFactory(new DialogAddAndReviseFactory(0));
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        if (savedInstanceState ==null){
            addFragment1();
//        currentFragment = fragment1;
        }
        //建立ViewModel
        setRvViewModel();
        //在OnCreate時就開始監聽
        rvViewModel.getEquippedData().observe(this, data -> {
            clickListenerRv.onDataChanged(rvViewModel.getEquippedData().getValue());
        });
        activityMainBinding.AddBtn.setOnClickListener(v -> {
            rvViewModel.resetDefaultData();
            DialogAddAndRevise dialogAddAndRevise = new DialogAddAndRevise();
            dialogAddAndRevise.show(manager,"dfAdd");
        });
        activityMainBinding.SearchBtn.setOnClickListener(v -> onSearchButtonClick());
        activityMainBinding.SearchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                onSearchButtonClick();
            }
        });

    }

    public void addFragment1(){
        fragment1 = new Fragment1();
        //FragmentManager與transaction
        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();
//        Log.e("MainActivityCreateFragment",String.valueOf(manager.findFragmentByTag("fragment1").isAdded()));
        //Fragment初始設立
        transaction.add(R.id.center,fragment1,"fragment1").setReorderingAllowed(true).commit();
    }

    public void setRvViewModel(){
        clickListenerRv = fragment1;
        rvViewModelFactory = new RvViewModelFactory();
        rvViewModel = new ViewModelProvider(this, rvViewModelFactory).get(RvViewModel.class);
        activityMainBinding.setRvViewModel(rvViewModel);
        activityMainBinding.setLifecycleOwner(this);
    }

        public void onSearchButtonClick(){
        ArrayList<Data> matchData = rvViewModel.searchColor();
        if(matchData.isEmpty()){
            Log.d(tag,"沒有符合");
//            Toast.makeText(,"沒有符合" + rvViewModel.getInputSearchColor() + "的資料",Toast.LENGTH_LONG).show();
        }
        clickListenerRv.onDataChanged(matchData);
    }
}



