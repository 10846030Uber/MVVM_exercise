package com.example.test_allen.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.ViewModelProvider;

import com.example.test_allen.R;
import com.example.test_allen.data_Format.Data;
import com.example.test_allen.databinding.DfItemBinding;
import com.example.test_allen.viewmodel.RvViewModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;

public class DialogAddAndRevise extends androidx.fragment.app.DialogFragment {
    private ArrayList<String> spinnerArrayList;
    private int deviceId;
    private String captureAt;
    private String color;
    private int channelId;
    private Boolean hasHelmet;
    private DfItemBinding dfItemBinding;
    private Boolean hasMask;
    private Boolean hasVest;
    private int position;
    private String event;
    private RvViewModel rvViewModel;
    private final static String tag = Thread.currentThread().getStackTrace()[2].getClassName();
    private AlertDialog.Builder builder;

    public DialogAddAndRevise() {}

    public DialogAddAndRevise(int position) {
        this.position = position;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Log.e(tag, "DialogCreate");
        rvViewModel = new ViewModelProvider(requireActivity()).get(RvViewModel.class);
        spinnerArrayList = rvViewModel.getSpinnerArrayList();
        event = rvViewModel.getEvent();

        //創建一個LayoutInflater
        dfItemBinding = DfItemBinding.inflate(LayoutInflater.from(getContext()));
        dfItemBinding.setRvViewModel(rvViewModel);
        if (event.equals("REVISE")) position = rvViewModel.getPosition();
        DefaultData();
        //將View設至AlertDialog
        setSpinnerAdapter();
        setSpinnerListener();
        initAlertBuilder();
//        return super.onCreateDialog(savedInstanceState);
        return builder.create();
    }

    public void initAlertBuilder(){
        builder = new AlertDialog.Builder(getActivity());
        builder.setView(dfItemBinding.getRoot());
        //PositiveButton建立與onClick事件
        builder.setPositiveButton("Confirm", (dialogInterface, i) -> {
            setDeviceId();
            setCaptureTimeFromTimePicker();
            setColorFromRadioButton();
            setHelmetFromSwitch();
            setMaskFromCheckButton();
            setVestFromCheckButton();
            Data data = new Data(deviceId, channelId, captureAt, color, hasHelmet, hasMask, hasVest);
            rvViewModel.setDialogDefaultData(data);
            if (event.equals("ADD")) {
                rvViewModel.addEquippedData();
            } else if (event.equals("REVISE")) {
                rvViewModel.reviseEquippedData(position);
            }
            rvViewModel.setDialogDisplay(false);
        }).setNegativeButton("cancel", (dialogInterface, i) -> {
            //NegativeButton建立與onClick事件
        });
    }

    public void setSpinnerAdapter() {
        ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, spinnerArrayList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dfItemBinding.channelIDSpinnerDL.setAdapter(adapter);

    }

    public void setSpinnerListener() {
        dfItemBinding.channelIDSpinnerDL.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                channelId = Integer.parseInt(adapterView.getItemAtPosition(i).toString());
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void setDeviceId() {
        deviceId = Integer.parseInt(dfItemBinding.deviceEditTextDL.getText().toString());

    }

    @SuppressLint("DefaultLocale")
    public void setCaptureTimeFromTimePicker() {
        LocalDateTime currentTime = LocalDateTime.now();
        if (rvViewModel.getDialogAddAndReviseData().getValue() != null) {
            String defaultTime = rvViewModel.getDialogAddAndReviseData().getValue().getTime().split(" ")[0];
            captureAt = String.format("%s %d:%d:%d", defaultTime, dfItemBinding.timeTimePicker.getHour(), dfItemBinding.timeTimePicker.getMinute(), currentTime.getSecond());
        }else  captureAt = String.format("%d-%02d-%d %d:%02d:%02d", currentTime.getYear(), currentTime.getMonthValue(), currentTime.getDayOfMonth(), dfItemBinding.timeTimePicker.getHour(), dfItemBinding.timeTimePicker.getMinute(), currentTime.getSecond());

    }

    public void setColorDefaultPosition() {
        String defaultColor = rvViewModel.getDialogAddAndReviseData().getValue().getColor();
        switch (defaultColor) {
            case "紅":
                dfItemBinding.colorRBRed.setChecked(true);
                break;
            case "黃":
                dfItemBinding.colorRBYellow.setChecked(true);
                break;
            case "藍":
                dfItemBinding.colorRBBlue.setChecked(true);
                break;
            case "綠":
                dfItemBinding.colorRBGreen.setChecked(true);
                break;
        }
    }

    public void setColorFromRadioButton() {
        int checkedId = dfItemBinding.colorRG.getCheckedRadioButtonId();
        if (checkedId == R.id.color_RB_red) {
            color = "紅";
        } else if (checkedId == R.id.color_RB_blue) {
            color = "藍";
        } else if (checkedId == R.id.color_RB_green) {
            color = "綠";
        } else if (checkedId == R.id.color_RB_yellow) {
            color = "黃";
        } else color = "";
    }

    public void setHelmetFromSwitch() {
        hasHelmet = dfItemBinding.helmetSwitch.isChecked();
    }

    public void setMaskFromCheckButton() {
        hasMask = dfItemBinding.maskCB.isChecked();
    }

    public void setVestFromCheckButton() {
        hasVest = dfItemBinding.vestCB.isChecked();
    }

    public void DefaultData() {
        if (rvViewModel.getDialogAddAndReviseData().getValue() != null) {
            dfItemBinding.setData(rvViewModel.getDialogAddAndReviseData().getValue());
            int position = 0;
            int hour = Integer.parseInt(rvViewModel.getDialogAddAndReviseData().getValue().getTime().split(" ")[1].substring(0, 2));
            int min = Integer.parseInt(rvViewModel.getDialogAddAndReviseData().getValue().getTime().split(":")[1]);
            if (rvViewModel.getDialogAddAndReviseData().getValue() != null) {
                for (int i = 0; i < spinnerArrayList.size(); i++) {
                    if (spinnerArrayList.get(i).equals(String.valueOf(rvViewModel.getDialogAddAndReviseData().getValue().getChannelID()))) {
                        position = i;
                    }
                }
            }
            dfItemBinding.channelIDSpinnerDL.setSelection(position);
            dfItemBinding.timeTimePicker.setHour(hour);
            dfItemBinding.timeTimePicker.setMinute(min);
            setColorDefaultPosition();
        } else {
            dfItemBinding.deviceEditTextDL.setText("");
            dfItemBinding.colorRBRed.setChecked(true);
        }
    }


}
