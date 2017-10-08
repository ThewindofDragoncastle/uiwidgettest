package com.example.uiwidgettest.myreview.media;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.uiwidgettest.R;
import com.example.uiwidgettest.myreview.service.GetConnectionService;
import com.example.uiwidgettest.myreview.service.PlayMusicWithService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 40774 on 2017/10/6.
 */

public class MovieSongitem extends RecyclerView.Adapter<MovieSongitem.ViewHolder> {

    private final int MOVIE=2;
    private final int SONG=1;
    private int SONGS=-1;
//    private int MOVIES=-1;
//    private int SONGSMOVIES=-1;
//    private List<SongorMovie> songs=new ArrayList<>();
//    private List<SongorMovie> movies=new ArrayList<>();
    private List<SongorMovie> songorMovies;
    private Context context;
    private SongorMovie songorMovie;
    public MovieSongitem(Context context, List<SongorMovie> songorMovies)
    {
        this.context=context;
        this.songorMovies=songorMovies;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.songmovieitem,parent,false);
//        //暂时发现放在这里最好 一次改变只会被执行一次
//        if(songorMovies.size()!=SONGSMOVIES) {
//            SONGSMOVIES=songorMovies.size();
//            for (SongorMovie songorMovie : songorMovies) {
//                //将电影和音乐分开
//                if (songorMovie.getSongorMovie() == 1)
//                    songs.add(songorMovie);
//                else
//                    movies.add(songorMovie);
//            }
//            SONGS = songs.size();
//            MOVIES = movies.size();
//        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        songorMovie = songorMovies.get(position);
        holder.tab.setText("");
        if (position == 0)
             holder.tab.setText("音乐列表：");
       if(songorMovie.islast()) {
               holder.noborder.setVisibility(View.GONE);
                holder.border.setVisibility(View.VISIBLE);
                holder.tab.setText("视频列表：");
        }

          holder.name.setText(songorMovie.getName());
         final int longonclickpostion=position;
          holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                String type=null;
                songorMovie=songorMovies.get(longonclickpostion);
                if(songorMovie.getSongorMovie()==MOVIE)
                    type="电影";
                else
                    type="音乐";
                 new AlertDialog.Builder(context)
                         .setMessage("文件名：\n"+songorMovie.getName()
                                 +"\n文件路径:\n"+songorMovie.getPath()
                         +"\n文件类型:\n"+type)
                         .setCancelable(true)
                         .setTitle("详细信息")
                         .show();
                return true;
            }
        });
        holder.play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                songorMovie=songorMovies.get(longonclickpostion);
               if(GetConnectionService.binder!=null)
               {
                   GetConnectionService.binder.startPlay(songorMovie);
               }
            }
        });
    }

    @Override
    public int getItemCount() {
        return songorMovies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView tab;
        View border;
        View noborder;
        Button play;
        public ViewHolder(View itemView) {
            super(itemView);
            name=(TextView) itemView.findViewById(R.id.displayitem);
            tab=(TextView) itemView.findViewById(R.id.displaytab);
            border=itemView.findViewById(R.id.border);
            noborder=itemView.findViewById(R.id.noborder);
            play=(Button)itemView.findViewById(R.id.play);
        }
    }
    public void setSongsSize(int size)
    {
        SONGS=size;
    }
}
