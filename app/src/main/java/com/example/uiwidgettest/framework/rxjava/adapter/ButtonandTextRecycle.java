package com.example.uiwidgettest.framework.rxjava.adapter;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.uiwidgettest.R;
import com.example.uiwidgettest.framework.rxjava.useinterface.CallBackButton;

import java.util.List;

/**
 * Created by 40774 on 2017/10/10.
 */

public class ButtonandTextRecycle extends RecyclerView.Adapter<ButtonandTextRecycle.ViewHolder>
       {
    private ViewHolder viewHolder;
    private Context context;
    private List<String> name;
    //掉用活动接口
    private CallBackButton backButton;
    public ButtonandTextRecycle(Context context,CallBackButton button,List<String> name) {
           this.context=context;
           this.backButton=button;
           this.name=name;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.buttonandtextrecycle,parent,false);
        viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder,int position) {
        final int site=position;
         holder.implement.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 backButton.onClick(v,site);
             }
         });
         holder.displayButton.setText(name.get(position));
         holder.displayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               new AlertDialog.Builder(context)
                       .setTitle("查看信息")
                       .setMessage(name.get(site))
                       .show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return name.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder
    {
        Button implement;
        TextView displayButton;
        public ViewHolder(View itemView) {
            super(itemView);
            displayButton=(TextView)itemView.findViewById(R.id.displayButton);
            implement=(Button) itemView.findViewById(R.id.implement);
        }
    }
}
