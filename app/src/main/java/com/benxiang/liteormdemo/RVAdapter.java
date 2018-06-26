package com.benxiang.liteormdemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * recyclerview 适配器
 * Created by ZeQiang Fang on 2018/6/26.
 */

public class RVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private LayoutInflater mLayout;
    private List<Person> list;

    public RVAdapter(Context context,List<Person> list){
        this.context = context;
        this.mLayout = LayoutInflater.from(context);
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayout.inflate(R.layout.activity_rv_item,null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((MyViewHolder)holder).tv_rv_id.setText("   id:"+String.valueOf(list.get(position).getId())+"   ");
        ((MyViewHolder)holder).tv_rv_name.setText("name:"+list.get(position).getName()+"   ");
        ((MyViewHolder)holder).tv_rv_age.setText("age:"+String.valueOf(list.get(position).getAge()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tv_rv_id,tv_rv_name,tv_rv_age;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_rv_id = itemView.findViewById(R.id.tv_rv_id);
            tv_rv_name = itemView.findViewById(R.id.tv_rv_name);
            tv_rv_age = itemView.findViewById(R.id.tv_rv_age);
        }
    }
}
