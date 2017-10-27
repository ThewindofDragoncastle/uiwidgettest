package com.example.uiwidgettest.mvpupdatechat.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.uiwidgettest.R;
import com.example.uiwidgettest.framework.mvp.useinterface.DatainfoContract;
import com.example.uiwidgettest.thelight.tab.MyPlan;
import com.example.uiwidgettest.thelight.tab.RecyclerViewAdapter;
import com.example.uiwidgettest.thelight.tab.RecyclerViewItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 40774 on 2017/10/18.
 */

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {
private Context context;
private List<Message> messageList;
    public MessageAdapter(Context context,List<Message> messageList)
{
    this.context=context;
    this.messageList=messageList;
}
    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.msg_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MessageAdapter.ViewHolder holder, int position) {
        Message message=messageList.get(position);
        if (message.isRepient()) {
            holder.righttext.setText(message.getMessage());
            holder.rightlayout.setVisibility(View.VISIBLE);
            holder.Leftlayout.setVisibility(View.GONE);
        }
        else {
            holder.Lefttext.setText(message.getMessage());
            holder.Leftlayout.setVisibility(View.VISIBLE);
            holder.rightlayout.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder
    {
        @BindView(R.id.right_layout)
        LinearLayout rightlayout;
        @BindView(R.id.Left_layout)
        LinearLayout Leftlayout;
        @BindView(R.id.Left_Text)
        TextView Lefttext;
        @BindView(R.id.right_Text)
        TextView righttext;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
