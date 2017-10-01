package com.example.uiwidgettest.thelight.tab;

import android.content.Context;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.uiwidgettest.MyLog;
import com.example.uiwidgettest.R;

import java.io.File;
import java.util.List;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private Context context;
    private View view;
    private List<MyPlan> myPlen;
    private RecyclerViewItem listener;
    private MyPlan myPlan;
    private StringBuffer data=new StringBuffer("");
    public  class ViewHolder extends RecyclerView.ViewHolder
    {
        public final View v;
        private TextView text;
        public ViewHolder(View itemView) {
            super(itemView);
            text=(TextView)itemView.findViewById(R.id.mytext);
            v=itemView;
        }
    }
    public RecyclerViewAdapter(Context context, List<MyPlan> myPlen, RecyclerViewItem listener)
    {
        this.myPlen=myPlen;
        this.context=context;
        this.listener=listener;
    }
    @Override
       public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view= LayoutInflater.from(context).inflate(R.layout.myrecycleritem,parent,false);
        MyLog.i("循环视图适配器","绑定器被创建");
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerViewAdapter.ViewHolder holder, int position) {
            myPlan=myPlen.get(position);
            holder.text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   data.append(holder.text.getText().toString());
                   // listener.ReturnData(Filesname(data.toString()));
                }
            });
            holder.text.setText(myPlan.getMyplan());//当前情况只有一个应该说
    }

    @Override
    public int getItemCount() {
        return myPlen.size();
    }
    private String[] Filesname(String data)
    {
        File file=new File(Environment.getExternalStorageDirectory()+File.separator+data);
        return file.list();
    }
}
