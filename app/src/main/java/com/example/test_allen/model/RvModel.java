package com.example.test_allen.model;

import android.os.Handler;

import com.example.test_allen.data_Format.Data;

import java.util.ArrayList;

public class RvModel {
    private ArrayList<Data> data = new ArrayList<>();

    public RvModel() {

    }


    public void fetchData(onDataReceiveCallBack callBack) {
        final Handler handler = new Handler();
        handler.postDelayed(() -> {
            data.clear();
            data.add(new Data(43, 13, "2022-05-13 18:15:10", "", false, true,true));
            data.add(new Data(11, 17, "2022-06-07 02:14:07", "綠", true, false,true));
            data.add(new Data(25, 5, "2022-06-07 06:16:35", "綠", false, true,false));
            data.add(new Data(43, 13, "2022-05-13 18:15:10", "藍", false, true,false));
            data.add(new Data(43, 13, "2022-05-13 18:15:10", "紅", true, false,true));
            callBack.onDataReceive(data);
        }, 1000);
    }

    public interface onDataReceiveCallBack {
        void onDataReceive(ArrayList<Data> data);
    }

}
