package com.example.uiwidgettest.ui.Sendorrecieve;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uiwidgettest.MyApplication;
import com.example.uiwidgettest.MyLog;
import com.example.uiwidgettest.R;
import com.example.uiwidgettest.ui.chatgui;

import java.util.List;



/**
 * Created by 40774 on 2017/9/5.
 */

public class IntactAdapter extends RecyclerView.Adapter<IntactAdapter.ViewHolder> {
    public List<Intact> intactList;
    private Context context;
    private onItemListener onItemListener;
    class ViewHolder extends RecyclerView.ViewHolder
    {
        View intact;
        ImageView intactimage;
        ImageView online;
        TextView intactname;
        TextView intactunread;
        TextView intacttime;
        public ViewHolder(View view)
        {
            super(view);
            intact=view;
            online=(ImageView)view.findViewById(R.id.online);
            intactname=(TextView)view.findViewById(R.id.intactname);
            intactunread=(TextView)view.findViewById(R.id.intactunread);
            intacttime=(TextView)view.findViewById(R.id.intacttime);
        }
    }
public IntactAdapter(List<Intact> Intact)
{
    //添加回调实例

    intactList=Intact;
}
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context=parent.getContext();
        View view= LayoutInflater.from(context).inflate(R.layout.intactitem,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Intact intact=intactList.get(position);

        holder.intactname.setText(intact.getName());
        holder.intacttime.setText(intact.getTime());
        holder.online.setImageResource(intact.getOnline());
        if(intact.getUnread())
        holder.intactunread.setText("[你有未读消息]");
        if(onItemListener!=null)
        {
            holder.intact.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
//                    if(event.getAction()==MotionEvent.ACTION_MOVE) {
//                        Toast.makeText(context,"你摸了人家啊",Toast.LENGTH_SHORT).show();
//                        MyLog.d("IntactAdapter:","你摸了人家啊");
//                        return true;
//                    }
//                   else if(event.getAction()==MotionEvent.ACTION_DOWN)
//                    {
//                        Toast.makeText(context,"你离开了此键",Toast.LENGTH_SHORT).show();
//                        MyLog.d("IntactAdapter:","你离开了此键");
//                        return true;
//                    }
//                   else if(event.getAction()==MotionEvent.ACTION_UP)
//                    {
//                        Toast.makeText(context,"你按下了此键",Toast.LENGTH_SHORT).show();
//                        MyLog.d("IntactAdapter:","你按下了此键");
//                        return true;
//                    }
//                  else  if(event.getAction()==MotionEvent.ACTION_OUTSIDE)
//                    {
//                        Toast.makeText(context,"你点击在此键之外",Toast.LENGTH_SHORT).show();
//                        MyLog.d("IntactAdapter:","你点击在此键之外");
//                        return true;
//                    }
//                   else if(event.getAction()==MotionEvent.ACTION_BUTTON_RELEASE)
//                    {
//                        Toast.makeText(context,"你释放了此键",Toast.LENGTH_SHORT).show();
//                        MyLog.d("IntactAdapter:","你释放了此键");
//                        return true;
//                    }
//                    else
                        return true;
                }
            });
            holder.intact.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemListener.onClick(v,position);
                }
            });
            holder.intact.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    onItemListener.onLongClick(v,position);
                    return true;
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return intactList.size();
    }
    //回调接口
    public interface onItemListener
    {
        void onClick(View view,int position);
        void onLongClick(View view,int position);
    }
    //外部传入接口实例
   public void setOnItemListener(onItemListener onItemListener)
    {
        this.onItemListener=onItemListener;
    }
}
