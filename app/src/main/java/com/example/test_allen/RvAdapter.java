package com.example.test_allen;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test_allen.data_Format.Data;
import com.example.test_allen.databinding.RvItemBinding;


import java.util.ArrayList;

public class RvAdapter extends RecyclerView.Adapter<RvAdapter.ViewHolder> {
    //測試Log的tag
    public final static String tag = Thread.currentThread().getStackTrace()[2].getClassName();
    //RecycleView需要的資料
    private ArrayList<Data> listData;
    //按鈕事件interface
    private ClickListenerRv listenerRv;
    private int position;
    //建構RvAdapter
    public RvAdapter(ArrayList<Data> listData,ClickListenerRv listenerRv) {
        this.listData = listData;
        this.listenerRv = listenerRv;
        Log.e("RvAdapter", "Create_RvAdapter");
    }

    //獲得ListData
    public ArrayList<Data> getListData() {
        return listData;
    }
    //設置ListData
    public void setListData(ArrayList<Data> listData) {
        this.listData = listData;
    }
    public void eventJudge(ArrayList<Data> changedData){
        if (changedData.size() == listData.size()){
            listData = changedData;
            notifyDataSetChanged();
        }else if(changedData.size() > listData.size()){
            listData = changedData;
            notifyItemInserted(0);
        }else {
            listData = changedData;
            notifyItemRemoved(position);
        }
    }
    //ViewHolder
    class ViewHolder extends RecyclerView.ViewHolder {
        private final RvItemBinding rvItemBinding;
        //ViewHolder建立
        public ViewHolder(@NonNull RvItemBinding rvItemBinding)  {
            super(rvItemBinding.getRoot());
            this.rvItemBinding = rvItemBinding;
            //刪除按鈕OnClick建立
            rvItemBinding.buttoDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    position = getBindingAdapterPosition();
                    listenerRv.onRemoveButtonClick(rvItemBinding.buttoDelete,getBindingAdapterPosition());
                }
            });
            //修改按鈕OnClick建立
            rvItemBinding.buttonRevise.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listenerRv.onReviseButtonClick(rvItemBinding.buttonRevise,getBindingAdapterPosition());
                }
            });

        }

        //設定ListData值
        void bind(Data data) {
            rvItemBinding.setData(data);
            //立即更新RecycleView
            rvItemBinding.executePendingBindings();
        }
    }
    //建立RecycleView的ViewHolder(放入Item)
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        RvItemBinding rvItemBinding = RvItemBinding.inflate(layoutInflater, parent, false);
        return new ViewHolder(rvItemBinding);
    }
    //將RecycleView資料丟入各個item
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Data data = listData.get(position);
        holder.bind(data);

    }

    @Override
    public int getItemCount() {
        return listData.size();
    }


}






