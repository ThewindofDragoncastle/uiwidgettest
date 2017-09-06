package com.example.uiwidgettest.news;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.uiwidgettest.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by 40774 on 2017/7/19.
 */

public class Title extends Fragment {
    @Nullable
            private boolean isTwoPane;
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.title111,container,false);
        RecyclerView recyclerView=(RecyclerView)view.findViewById(R.id.news_reView);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        NewsViewAdapter adapter=new NewsViewAdapter(initNews());
        recyclerView.setAdapter(adapter);
        System.out.println("标题循环视图已经被建立");
        return view;
    }
private List<News> initNews()
{
    List<News> newlist=new ArrayList<>();
//    News news=new News();
//    news.setContent("hhhhhhhafaf");
//    news.setTitle("fhkjashflaklfj");
//    newlist.add(news);
    News news[]=new News[50];
    news[0]=new News();
    news[0].setTitle("震惊了！22岁男子当街和狗发生这种事！");
    news[0].setContent("22岁男子河北秦皇岛市李某，在2017年7月19日，当街喂狗。");
    news[1]=new News();
    news[1].setTitle("孩子只干了这一件事，成绩蹭蹭蹭的往上涨");
    news[1].setContent("家住四川南充的陈某通过作弊成功的考上了班上第一。");
    newlist.add(news[1]);
    newlist.add(news[0]);
    Random rd=new Random();
    StringBuffer sbtitle=new StringBuffer("");
    StringBuffer sbcontent=new StringBuffer("");
    for(int i=2;i<50;i++)
    {
        news[i]=new News();
        for (int j=0;j<rd.nextInt(10)+1;j++)
        {
            sbtitle.append(" 标题 ");
        }
        for (int z=0;z<rd.nextInt(10)+1;z++)
        {
            sbcontent.append(" 内容 ");
        }
        news[i].setTitle(sbtitle.toString());
        news[i].setContent(sbcontent.toString());
        sbcontent.replace(0,sbcontent.length(),"");
        sbtitle.replace(0,sbtitle.length(),"");
        newlist.add(news[i]);
        System.out.println(news[i].getContent()+i);
    }
    return newlist;
}
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(getActivity().findViewById(R.id.news_layout)!=null)
        isTwoPane=true;
        else isTwoPane=false;
    }
    class NewsViewAdapter extends RecyclerView.Adapter<NewsViewAdapter.ViewHolder>
    {
        private List<News> mNewsList;
       class ViewHolder extends RecyclerView.ViewHolder
        {
            TextView newtitle;
            public ViewHolder(View view)
            {
                super(view);
                newtitle=(TextView) view.findViewById(R.id.news_title);
            }
        }
        public NewsViewAdapter(List<News> list)
        {
            mNewsList=list;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
         View view1=LayoutInflater.from(parent.getContext()).inflate(R.layout.title_item,parent,false);
            final ViewHolder viewHolder=new ViewHolder(view1);
            view1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    News news=mNewsList.get(viewHolder.getAdapterPosition());
                    if(isTwoPane)
                    {
                        Content newsContentFragment =
                       (Content) getFragmentManager().findFragmentById(R.id.content_fragment);
                        newsContentFragment.refresh(news.getTitle(),news.getContent());
                    }
                    else
                    {
                        IntoContentActivity.actionStart(getActivity(),news.getTitle(),news.getContent());
                    }
                }
            });
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            News news=mNewsList.get(position);
            holder.newtitle.setText(news.getTitle());
        }

        @Override
        public int getItemCount() {
            return mNewsList.size();
        }
    }

}
