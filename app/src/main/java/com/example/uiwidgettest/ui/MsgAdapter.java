package com.example.uiwidgettest.ui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.uiwidgettest.R;

import java.util.List;

/**
 * Created by 40774 on 2017/7/15.
 */

public class MsgAdapter extends RecyclerView.Adapter<MsgAdapter.ViewHolder>{
    private List<Message> mmmsglist;
    public MsgAdapter(List<Message> mmsglist)
    {
        this.mmmsglist=mmsglist;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.msg_item,parent,false);
        return new ViewHolder(view);
    }
    public void onBindViewHolder(ViewHolder viewHolder,int position)
    {
        Message message=mmmsglist.get(position);
        if(message.getCurrenttype()==message.TYPE_RECEIVED)
        {
            viewHolder.linearLayoutleft.setVisibility(View.VISIBLE);
            viewHolder.linearLayoutright.setVisibility(View.GONE);
            viewHolder.leftmsg.setText(message.getContent());
        }
           else if(message.getCurrenttype()==message.TYPE_SEND)
            {
                viewHolder.linearLayoutleft.setVisibility(View.GONE);
                viewHolder.linearLayoutright.setVisibility(View.VISIBLE);
                viewHolder.rightmsg.setText(message.getContent());
            }
    }
    public int getItemCount()
    {
        return mmmsglist.size();
    }
    static class ViewHolder extends RecyclerView.ViewHolder
    {
        LinearLayout linearLayoutleft;
        LinearLayout linearLayoutright;
        TextView leftmsg;
        TextView rightmsg;
        public ViewHolder(View view)
        {
            super(view);
            linearLayoutleft=(LinearLayout)view.findViewById(R.id.Left_layout);
            linearLayoutright=(LinearLayout)view.findViewById(R.id.right_layout);
            leftmsg=(TextView)view.findViewById(R.id.Left_Text);
            rightmsg=(TextView)view.findViewById(R.id.right_Text);
        }

    }

}
