package com.example.test_allen.viewmodel;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.databinding.Bindable;
import androidx.databinding.BindingAdapter;
import androidx.databinding.InverseBindingAdapter;
import androidx.databinding.InverseBindingListener;
import androidx.databinding.ObservableField;
import androidx.databinding.adapters.TextViewBindingAdapter;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.test_allen.data_Format.Data;
import com.example.test_allen.model.RvModel;

import java.util.ArrayList;
import java.util.Observable;

public class RvViewModel extends ViewModel {
    //RecycleView須放入的資料（equippedData）
    private final MutableLiveData<ArrayList<Data>> equippedData = new MutableLiveData<>();
    //判斷是否在抓取Response
    public final ObservableField<Boolean> isLoading = new ObservableField<>(false);
    //搜尋輸入的資料
    private final MutableLiveData<String> inputSearchColor = new MutableLiveData<>("");

    private final MutableLiveData<Data> dialogAddAndReviseData = new MutableLiveData<>();

    private ArrayList<String> spinnerArrayList ;

    private MutableLiveData<Boolean> dialogDisplay = new MutableLiveData<>(false);

    private String event ;
    private int position;


    private RvModel rvModel = new RvModel();



    //與Model索取資料
    public void getRvData(){
        isLoading.set(true);
        Log.e("ViewModel",isLoading.get().toString());
        rvModel.fetchData(data -> {
            equippedData.setValue(data);
            isLoading.set(false);
            Log.e("Loading Complete",isLoading.get().toString());
        });
    }


    public MutableLiveData<String> getInputSearchColor() {
        return inputSearchColor;
    }

    public LiveData<ArrayList<Data>> getEquippedData(){
        return equippedData;
    }

    public void setEquippedData(ArrayList<Data> data){
        equippedData.setValue(data);
    }
    //新增至最前面的equipped資料
    public void addEquippedData(){
        equippedData.getValue().add(0,getDialogAddAndReviseData().getValue());
        setEquippedData(getEquippedData().getValue());
    }
    //刪除position位置的equipped資料
    public void removeEquippedData(int position){
        equippedData.getValue().remove(position);
        setEquippedData(equippedData.getValue());
    }

    public void reviseEquippedData(int position){
        equippedData.getValue().remove(position);
        equippedData.getValue().add(position,dialogAddAndReviseData.getValue());
        equippedData.setValue(equippedData.getValue());
    }

    //搜尋資料
    public ArrayList<Data> searchColor(){
        ArrayList<Data> matchData =new ArrayList<>();
        if(inputSearchColor.getValue().equals("")){
            return equippedData.getValue();
        }else {
            equippedData.getValue().forEach((value)->{
                if (value.getColor().equals(inputSearchColor.getValue()) ){
                    matchData.add(matchData.size(),value);
                }
            });
            return matchData;
        }
    }
    public void onAddButtonClick(){
            resetDefaultData();
            setEvent("ADD");
            setDialogDisplay(true);
    }

//---------------------------分隔線---------------------------------------------
public MutableLiveData<Data> getDialogAddAndReviseData() {
    return dialogAddAndReviseData;
}

    public void setDialogDefaultData(Data data){
        dialogAddAndReviseData.setValue(data);
    }

    public void setPositionDefaultData(int position){
        dialogAddAndReviseData.setValue(equippedData.getValue().get(position));
    }
    public void resetDefaultData(){
        dialogAddAndReviseData.setValue(null);
    }

public void setSpinnerArrayList() {
    spinnerArrayList = new ArrayList<>();
    spinnerArrayList.add("13");
    spinnerArrayList.add("17");
    spinnerArrayList.add("5");
    spinnerArrayList.add("27");
    spinnerArrayList.add("1");
}
    public ArrayList<String> getSpinnerArrayList() {
        setSpinnerArrayList();
        return spinnerArrayList;
    }

    public MutableLiveData<Boolean> getDialogDisplay() {
        return dialogDisplay;
    }

    public void setDialogDisplay(Boolean display) {
        dialogDisplay.setValue(display);
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
