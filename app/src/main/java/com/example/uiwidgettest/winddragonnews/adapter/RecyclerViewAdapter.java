package com.example.uiwidgettest.winddragonnews.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.uiwidgettest.MyLog;
import com.example.uiwidgettest.R;
import com.example.uiwidgettest.winddragonnews.HandleData.Data;
import com.example.uiwidgettest.winddragonnews.HandleData.DbData;
import com.example.uiwidgettest.winddragonnews.presenter.ActivityDataSupport;
import com.example.uiwidgettest.winddragonnews.useinterface.ForFragmetAdapter.RecyclerviewToNewsFragment;
import com.example.uiwidgettest.winddragonnews.useinterface.HolderOnclickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 40774 on 2017/11/7.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>
        implements HolderOnclickListener{
    private Context mcontext;
//    点赞和不点赞图片
    private final int REDFA;
    private final int GREYFA;

    private List<DbData> mdata=null;
    private final String TAG=getClass().getName();
//    向碎片回调数据
    private RecyclerviewToNewsFragment mcallback;
    public RecyclerViewAdapter(Context context, RecyclerviewToNewsFragment callback, List<DbData> dataList)
    {
        this.mcontext =context;
        this.mcallback =callback;
//        listview传入的是地址
        this.mdata=dataList;
//获取图片实例
            REDFA = R.drawable.redfa;
            GREYFA= R.drawable.greyfa;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(mcontext).inflate(R.layout.newsitem,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
       OnClickShareListener(holder);
       final  DbData data= mdata.get(position);
//       根据data的是否点赞来对图片显示进行显示
       if(!data.islike) {
           holder.like.setBackgroundResource(GREYFA);
       }
       else {
           holder.like.setBackgroundResource(REDFA);
       }
        OnClickLikeListener(holder,data,position);
       if(mdata!=null) {
//           如果有数据传入就设置
//           标题
           holder.title.setText(data.getTitle());
//           来源
           holder.resource.setText(data.getPosterScreenName());
//           时间戳
           holder.publishdate.setText(data.getPublishDateStr());
//           图片链接
           String[] urls=data.getImageUrls();
           if(urls!=null) {
               if(urls.length!=0) {
                   String url = data.getCoverpath();
                   MyLog.d(TAG,"图片地址："+url);
                 {
//               如果链接存在则加载图片
                       RequestOptions options = new RequestOptions().placeholder(R.drawable.loading)
                               .error(R.drawable.csyt);
                       Glide.with(mcontext).load(url).apply(options).into(holder.cover);
                   }
               }
           }
       }
       holder.cardView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
//               利用两层回调将数据回调至获得修改碎片操作
               MyLog.d(TAG,"点击全图响应");
               mcallback.ChangeFragmentCallback(data);
           }
       });
//       设置网络数据视图
    }

    @Override
    public int getItemCount() {
        if(mdata==null)
        return 0;
        else
            return mdata.size();
    }


    @Override
    public void OnClickLikeListener(final ViewHolder holder, final DbData data, final int position) {
//        设置监听
        holder.like.setOnClickListener(new View.OnClickListener() {
            //        由于Dbdata是确定的，所以只有收藏按钮只有两种值
            boolean islike=data.islike();
            @Override
            public void onClick(View v) {
//                   如果图片点赞为灰色切换回红色 红色切换为灰色弹出警示框
                    if (!islike) {
                        holder.like.setBackgroundResource(REDFA);
//                        存入数据库
                        ActivityDataSupport.getInstance().SaveFavorite(data);
                        islike=true;
                    }
                    else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(mcontext);
                        builder.setMessage("你确定要取消收藏？");
                        builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                    holder.like.setBackgroundResource(GREYFA);
//                                从数据库删除
                                ActivityDataSupport.getInstance().DeleteFavorite(data);
                                islike=false;
                            }
                        }).setNegativeButton("否", null).show();
                    }
            }
        });
    }

    @Override
    public void OnClickShareListener(final ViewHolder holder) {
        //        设置监听
    holder.share.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mcontext);
        builder.setTitle("选择分享对象。");
        String[] method={"QQ","微信","短信"};
        builder.setSingleChoiceItems(method, 0, null);
        builder.setNegativeButton("取消",null);
        builder.setPositiveButton("跳转", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(mcontext,"暂时无法打开",Toast.LENGTH_SHORT).show();
            }
        }).show();
        }
    });
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
//        文章标题
        @BindView(R.id.title)
        TextView title;
//        阅读人数
        @BindView(R.id.reactions)
        TextView reactions;
//        封面
        @BindView(R.id.coverpath)
        ImageView cover;
//        分享
        @BindView(R.id.share)
        Button share;
//        收藏点赞
        @BindView(R.id.like)
        Button like;
//        热点
        @BindView(R.id.moment)
        TextView moment;
//        卡片视图
        @BindView(R.id.cardview)
        CardView cardView;
//        来源
        @BindView(R.id.resource)
        TextView resource;
//        发布时间
        @BindView(R.id.publishdate)
        TextView publishdate;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            title.setText("震惊了，河北男子竟然...");
            reactions.setText("浏览人数：1000+");
//            需要设置背景才能铺满屏幕
            moment.setText("热点新闻");
        }
    }
}
