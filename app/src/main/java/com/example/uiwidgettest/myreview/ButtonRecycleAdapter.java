package com.example.uiwidgettest.myreview;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.transition.Transition;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uiwidgettest.R;

import org.greenrobot.eventbus.util.ErrorDialogManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 40774 on 2017/9/30.
 */

public class ButtonRecycleAdapter extends RecyclerView.Adapter<ButtonRecycleAdapter.ViewHolder> {
    private OnClickListener onClickListener;
    private List<ButtonAndText> buttonAndTexts;
    private List<Fragment> add_fragments;
    private Context context;
//    碎片管理
    private FragmentManager fragmentManager;
    public ButtonRecycleAdapter(Context context,OnClickListener onClickListener,
                                List<ButtonAndText> buttonAndTexts,List<Fragment> add_fragments,FragmentManager fragmentManager)
    {
        this.context=context;
        this.buttonAndTexts=buttonAndTexts;
        this.onClickListener=onClickListener;
        this.add_fragments=add_fragments;
        this.fragmentManager=fragmentManager;
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
//        如果可以下拉就下拉设置可见
//        判断：当前位置有布局文件
             if (add_fragments!=null){
//                 逻辑：碎片列表不为空并且碎片不为空 就显示折叠 否则不显示
//                 有两种情况碎片列表为空 碎片为空 都不应该显示
                     final Fragment fragment=add_fragments.get(position);
                      if(fragment!=null) {
                          showFolding(holder);
                          holder.folding_iron.setOnClickListener(new View.OnClickListener() {
                              @Override
                              public void onClick(View v) {
                                  Log.i(getClass().getName(), "点击位置：position=" + position);
                                  FragmentTransaction transition = fragmentManager.beginTransaction();

                                  if (fragment.getView() == null) {
//                       如果碎片未添加则添加
                                      transition.replace(R.id.item_folding, fragment);
                                      transition.commit();
                                  } else {
//                      有则删除
                                      transition.remove(fragment);
                                      transition.commit();
                                  }
                              }
                          });
                      }
                      else {
                          HideFolding(holder, position);
                      }
                 //                          将标题字设为紫色
                 holder.text.setTextSize(20);
                 holder.text.setTextColor(Color.parseColor("#FF8000FF"));
            }else  {
               HideFolding(holder,position);
        }


        holder.text.setText(buttonAndTexts.get(position).getText());
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
    private void showFolding(ViewHolder holder)
    {
        holder.frame_place.setVisibility(View.VISIBLE);
        holder.buttonname.setVisibility(View.GONE);
        holder.folding_iron.setVisibility(View.VISIBLE);
    }
    private void HideFolding(ViewHolder holder,final int position)
    {
        holder.frame_place.setVisibility(View.GONE);
        holder.buttonname.setVisibility(View.VISIBLE);
        holder.folding_iron.setVisibility(View.GONE);
        holder.buttonname.setText(buttonAndTexts.get(position).getButtonName());
        holder.buttonname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.OnClick(v, position);
            }
        });
    }
    class ViewHolder extends RecyclerView.ViewHolder
   {
       public TextView text;
       public Button buttonname;
       public ImageView folding_iron;
       private FrameLayout frame_place;
       public ViewHolder(View view)
       {
           super(view);
           text=(TextView)view.findViewById(R.id.introduce);
           buttonname=(Button)view.findViewById(R.id.fulfill);
           frame_place=(FrameLayout) view.findViewById(R.id.item_folding);
           folding_iron =(ImageView)view.findViewById(R.id.folding);
       }
   }
}
