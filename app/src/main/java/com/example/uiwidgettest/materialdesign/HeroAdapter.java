package com.example.uiwidgettest.materialdesign;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.uiwidgettest.MyApplication;
import com.example.uiwidgettest.MyLog;
import com.example.uiwidgettest.R;

import java.io.File;
import java.net.URL;
import java.util.List;

/**
 * Created by 40774 on 2017/8/23.
 */

public class HeroAdapter extends RecyclerView.Adapter<HeroAdapter.ViewHolder> {
    private Context context;
    private final int IMAGEUSEURL=1;

    static  class ViewHolder extends RecyclerView.ViewHolder
    {
        CardView cardView;
        ImageView imageView;
        TextView textView;
        public ViewHolder(View view)
        {
            super(view);
            cardView=(CardView)view;
            imageView=(ImageView)view.findViewById(R.id.hero_image);
            textView=(TextView)view.findViewById(R.id.hero_text);
        }
    }
    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        if (context==null)
            context=parent.getContext();
        final View view= LayoutInflater.from(context).inflate(R.layout.hero_item,parent,false);
        final ViewHolder viewHolder=new ViewHolder(view);
        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,Hero_Activity.class);
                Hero hero=mHeroList.get(viewHolder.getAdapterPosition());
                intent.putExtra("hero",hero);
                intent.putExtra("hero_image",hero.getImage());
                intent.putExtra("hero_textview",hero.getName());
                context.startActivity(intent);
            }
        });
        return viewHolder;
    }
private List<Hero> mHeroList;
    public HeroAdapter(List<Hero> mHeroList)
    {
        this.mHeroList=mHeroList;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Hero hero=mHeroList.get(position);
        holder.textView.setText(hero.getName());
        RequestOptions options=new RequestOptions().placeholder(R.drawable.loading)
                .error(R.drawable.noload);
//        if(hero.getImage()!=IMAGEUSEURL)   //如果image为1 使用网络图片
//        Glide.with(context).load(hero.getImage()).apply(options).into(holder.imageView);
//        else {
              final URL url=hero.getUrl();
              File file=new File( Environment.getExternalStorageDirectory()+File.separator
                      +"开发专用文件夹"+File.separator+"app使用图片"+url.toString().substring(url.toString().lastIndexOf("/")));
              new Thread(new Runnable() {
                  @Override
                  public void run() {
                      ForDownload forDownload=new ForDownload();
                      {
                          if(forDownload.Download(url)==0)
                          Toast.makeText(MyApplication.getContext(), "从网络上获取资源失败！", Toast.LENGTH_SHORT).show();
                      }
                  }
              }).start();

        MyLog.d("Heroadaper:","url文件名："+url.toString().substring(url.toString().lastIndexOf("/")));
//               if(forDownload.Download(url)==0)
//
               Glide.with(context).load(file).apply(options)
                       .into(holder.imageView);
//        }
    }

    @Override
    public int getItemCount() {
        return mHeroList.size();
    }

}
