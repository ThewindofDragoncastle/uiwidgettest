package com.example.uiwidgettest.ui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uiwidgettest.R;

import java.util.List;

/**
 * Created by 40774 on 2017/7/12.
 */

public class RecycleHeroAdapter extends RecyclerView.Adapter<RecycleHeroAdapter.ViewHolder>
{
    private List<Hero> mHeroList;
    public RecycleHeroAdapter(List<Hero> mHeroList) {
        this.mHeroList=mHeroList;
    }

    public ViewHolder onCreateViewHolder(ViewGroup vg,int vt) {
        View v= LayoutInflater.from(vg.getContext()).inflate(R.layout.falllayout,vg,false);
        //从layout布局中填充hero布局
        final ViewHolder vh=new ViewHolder(v);
        vh.heroview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position=vh.getAdapterPosition();
                Hero hero=mHeroList.get(position);
                Toast.makeText(v.getContext(),"你点击了"+hero.getName()+"的视图",Toast.LENGTH_SHORT).show();
            }
        });
        vh.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position=vh.getAdapterPosition();
                Hero hero=mHeroList.get(position);
                Toast.makeText(v.getContext(),"你点击了"+hero.getName()+"的图片",Toast.LENGTH_SHORT).show();
            }
        });
        return vh;
    }
    public void onBindViewHolder(ViewHolder holder,int position)
    {
        //滚到屏幕内时被执行
        Hero hero=mHeroList.get(position);
        holder.image.setImageResource(hero.getImage());
        holder.name.setText(hero.getName());
    }
    public int getItemCount()
    {
        return mHeroList.size();
    }
    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView  name;
        View heroview;
        public ViewHolder(View v)
        {
            super(v);
            heroview=v;
            image = (ImageView) v.findViewById(R.id.hero_1);
            name = (TextView) v.findViewById(R.id.hero_2);
        }
    }
}
