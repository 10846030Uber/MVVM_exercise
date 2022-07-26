package com.example.test_allen;

import android.view.View;

import com.example.test_allen.data_Format.Data;

import java.util.ArrayList;

public interface ClickListenerRv {
    void onRemoveButtonClick(View view,int position);
    void onReviseButtonClick(View view,int position);
    void onDataChanged(ArrayList<Data> data);

}
