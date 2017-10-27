package com.example.uiwidgettest.mvpupdatechat.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.uiwidgettest.R;
import com.example.uiwidgettest.mvpupdatechat.presenter.PresenterContractIntract;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 40774 on 2017/10/16.
 */

public class IntactRecyclerviewAdapter extends RecyclerView.Adapter<IntactRecyclerviewAdapter.ViewHolder> {
private List<com.example.uiwidgettest.ui.sendorrecieve.Intact> intacts;
private Context context;
    private PresenterContractIntract.FragmentWithAdapter adapter;
    public IntactRecyclerviewAdapter(Context context,
                                     List<com.example.uiwidgettest.ui.sendorrecieve.Intact> intacts
    , PresenterContractIntract.FragmentWithAdapter adapter)
    {
        this.intacts=intacts;
        this.context=context;
        this.adapter=adapter;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.intactitem,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final com.example.uiwidgettest.ui.sendorrecieve.Intact intact=intacts.get(position);
        holder.name.setText(intact.getName());
        if(intact.getOnline()==1)
            holder.online.setImageResource(R.drawable.online);
        else if(intact.getOnline()==0)
            holder.online.setImageResource(R.drawable.outline);
        if(intact.getUnread())
        holder.unread.setText("未读消息");
        holder.IntactImage.setImageResource(R.drawable.sjlr);
        holder.time.setText(intact.getTime());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.IntoChat(intact);
            }
        });
    }

    @Override
    public int getItemCount() {
        return intacts.size();
    }

   class ViewHolder extends RecyclerView.ViewHolder
{
    @BindView(R.id.intactname)
    TextView name;
    @BindView(R.id.IntactImage)
    ImageView IntactImage;
    @BindView(R.id.online)
    ImageView online;
    @BindView(R.id.intacttime)
    TextView time;
    @BindView(R.id.intactunread)
    TextView unread;
    public ViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }
}
}
