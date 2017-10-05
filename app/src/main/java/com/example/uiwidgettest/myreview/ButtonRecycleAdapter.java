package com.example.uiwidgettest.myreview;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uiwidgettest.R;

import java.util.List;

/**
 * Created by 40774 on 2017/9/30.
 */

public class ButtonRecycleAdapter extends RecyclerView.Adapter<ButtonRecycleAdapter.ViewHolder> {
    private OnClickListener onClickListener;
    private List<ButtonAndText> buttonAndTexts;
    private Context context;
    public ButtonRecycleAdapter(Context context,OnClickListener onClickListener,
                                List<ButtonAndText> buttonAndTexts)
    {
        this.context=context;
        this.buttonAndTexts=buttonAndTexts;
        this.onClickListener=onClickListener;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(context).inflate(R.layout.item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return buttonAndTexts.size();
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.buttonname.setText(buttonAndTexts.get(position).getButtonName());
        holder.text.setText(buttonAndTexts.get(position).getText());
        holder.buttonname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.OnClick(v,position);
            }
        });
        holder.buttonname.setOnLongClickListener(new View.OnLongClickListener() {
            private boolean ChangeColor=false;
            @Override
            public boolean onLongClick(View v) {
                if(!ChangeColor) {
                    holder.buttonname.setBackgroundColor(Color.RED);
                    ChangeColor=true;
                }
                else{
                    holder.buttonname.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
                    ChangeColor=false;
                }
                    onClickListener.OnLongClick(v,position);
                return true;
            }
        });
        holder.text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,holder.text.getText(),Toast.LENGTH_SHORT).show();
//                onClickListener.OnClick(v,position);
            }
        });
    }
    class ViewHolder extends RecyclerView.ViewHolder
   {
       public TextView text;
       public Button buttonname;
       public ViewHolder(View view)
       {
           super(view);
           text=(TextView)view.findViewById(R.id.introduce);
           buttonname=(Button)view.findViewById(R.id.fulfill);
       }
   }
}
