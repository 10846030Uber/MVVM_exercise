package com.example.test_allen.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.BindingAdapter;
import androidx.databinding.InverseBindingAdapter;
import androidx.databinding.InverseBindingListener;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.test_allen.ClickListenerRv;
import com.example.test_allen.R;
import com.example.test_allen.data_Format.Data;
import com.example.test_allen.RvAdapter;
import com.example.test_allen.databinding.Fragment1Binding;
import com.example.test_allen.viewmodel.RvViewModel;

import java.util.ArrayList;
import java.util.Observable;


public class Fragment1 extends Fragment implements ClickListenerRv{
    private Fragment1Binding fragment1Binding;
    private RvViewModel rvViewModel ;
    private DialogAddAndRevise dialogAddAndRevise;
    private RvAdapter rvAdapter = new RvAdapter(new ArrayList<>(),this);
    private final static String tag =Thread.currentThread().getStackTrace()[2].getClassName();
    public Fragment1(){

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("Fragment1","Create_Fragment1");

    }

    //建立Fragment以及RecycleView
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         //將fragment.xml放進activity的放fragment容器//
        fragment1Binding = Fragment1Binding.inflate(inflater,container,false);
        //建立RecycleView
        fragment1Binding.RecycleViewData.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        fragment1Binding.RecycleViewData.setAdapter(rvAdapter);
        setSearchButtonOnClick();
        setSearchListener();
        //回傳view給activity
        return fragment1Binding.getRoot();
    }

    //建立完View後將資料丟入各個View
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //用VIewModelProvider尋找目前現有的ViewModel， 在Fragment1消失前ViewModel都不會重建
        rvViewModel = new ViewModelProvider(requireActivity()).get(RvViewModel.class);
        //binding綁定viewModel
        fragment1Binding.setRvViewModel(rvViewModel);
        rvViewModel.getEquippedData().observe(requireActivity(), this::onDataChanged);
        rvViewModel.getDialogDisplay().observe(requireActivity(),aBoolean -> {
            if(aBoolean){
                dialogAddAndRevise = new DialogAddAndRevise();
                dialogAddAndRevise.show(getParentFragmentManager(),"dfAdd");
            }
        });
        if (savedInstanceState == null){
            rvViewModel.getRvData();
        }
    }



    public void setSearchButtonOnClick(){
        fragment1Binding.SearchBtn.setOnClickListener(v -> {
            searchEvent();
            Toast.makeText(getContext(),"沒有符合" + rvViewModel.getInputSearchColor().getValue() + "的資料",Toast.LENGTH_LONG).show();
        });
    }

    public void setSearchListener(){
        fragment1Binding.SearchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                searchEvent();
            }
        });
    }
    public void searchEvent(){
        ArrayList<Data> matchData = rvViewModel.searchColor();
        if(matchData.isEmpty()){
            Log.d(tag,"沒有符合");

        }
        onDataChanged(matchData);
    }

    //刪除按鈕事件
    @Override
    public void onRemoveButtonClick(View view, int position) {
        rvViewModel.removeEquippedData(position);
    }


    //修改按鈕事件
    @Override
    public void onReviseButtonClick(View view,int position) {
        rvViewModel.setPosition(position);
        rvViewModel.setEvent("REVISE");
        rvViewModel.setPositionDefaultData(position);
        rvViewModel.setDialogDisplay(true);
    }

    public void onDataChanged(ArrayList<Data> data) {
        rvAdapter.eventJudge(data);
    }


}
