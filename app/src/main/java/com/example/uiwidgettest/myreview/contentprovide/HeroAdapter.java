package com.example.uiwidgettest.myreview.contentprovide;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.ArraySet;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.uiwidgettest.MyLog;
import com.example.uiwidgettest.R;
import com.example.uiwidgettest.myreview.json.Hero;

import java.util.List;
import java.util.Set;

/**
 * Created by 40774 on 2017/10/3.
 */

//class HeroAdapter<Hero> extends RecyclerView.Adapter<Hero> {
//    private Context context;
//    private int resource;
//    private List<Hero> heros;
//    private EditText name;
//    private EditText cognoment;
//    private RadioButton genderman;
//    private RadioButton genderfamale;
//    private EditText energy;
//    private EditText force;
//    private EditText wit;
//    private EditText loyal;
//    private EditText virtue;
//    private TextView displayid;
//    private int count=0;
//    public HeroAdapter(@NonNull Context context, @LayoutRes int resource, List<Hero> heros) {
//        this.context = context;
//        this.resource = resource;
//        this.heros = heros;
//    }
//
//
//    @NonNull
//    @Override
//    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//        View view;
//        //view在这里我没发现保存视图中组件的方法
//       //viewholder可以
//        ViewHolder viewholder=null;
//
//        MyLog.d("HeroAdapter:",""+position);
//        //提升效率
//        if (convertView==null) {
//            //执行多次
//            viewholder = new ViewHolder();
//            MyLog.d("HeroAdapter:", "视图为空——————————position="+position);
//            view = LayoutInflater.from(context).inflate(resource, parent, false);
//            //初始化各组件
//            Button edit = (Button) view.findViewById(R.id.reviewsqledit);
//            Button delete = (Button) view.findViewById(R.id.reviewsqldelete);
//            Button update = (Button) view.findViewById(R.id.reviewsqlupdate);
//            Button confirm = (Button) view.findViewById(R.id.reviewsqlconfirm);
//            name=(EditText)view.findViewById(R.id.name);
//            cognoment=(EditText)view.findViewById(R.id.cognoment);
//            energy=(EditText)view.findViewById(R.id.energy);
//            force=(EditText)view.findViewById(R.id.force);
//            wit=(EditText)view.findViewById(R.id.wit);
//            loyal=(EditText)view.findViewById(R.id.loyal);
//            virtue=(EditText)view.findViewById(R.id.virtue);
//            genderfamale=(RadioButton)view.findViewById(R.id.genderfamale);
//            genderman=(RadioButton)view.findViewById(R.id.genderman);
//            displayid=(TextView)view.findViewById(R.id.displayId);
//            //设置不可编辑状态
//            //保存下来
//            //viewholder要么是if 或者从前已经ok的
//            //viewholder保存视图
//            //设置显示
//            //加快响应速度
//            viewholder.edit = edit;
//            viewholder.delete = delete;
//            viewholder.update = update;
//            viewholder.confirm = confirm;
//            viewholder.cognoment=cognoment;
//            viewholder.name=name;
//            viewholder.energy=energy;
//            viewholder.force=force;
//            viewholder.wit=wit;
//            viewholder.loyal=loyal;
//            viewholder.virtue=virtue;
//            viewholder.genderfamale=genderfamale;
//            viewholder.genderman=genderman;
//            viewholder.displayId=displayid;
//            view.setTag(viewholder);
//        } else {
//            view = convertView;
//            viewholder=(ViewHolder) view.getTag();
//        }
//            Hero hero = heros.get(position);
//            viewholder.virtue.setText(hero.getVirtue() + "");
//            viewholder.name.setText(hero.getName());
//            viewholder.cognoment.setText(hero.getCognomen());
//            viewholder.energy.setText(hero.getEnergy() + "");
//            viewholder.force.setText(hero.getForce() + "");
//            viewholder.wit.setText(hero.getWit() + "");
//            viewholder.loyal.setText(hero.getLoyal() + "");
//            if (hero.getGender())
//                viewholder.genderman.setChecked(true);
//            else
//                viewholder.genderfamale.setChecked(true);
//            setOnclickListener(viewholder);
//            viewholder.displayId.setText("第" + (position + 1) + "条数据");
//            setEnable(viewholder, false);
//            MyLog.d("HeroAdapter:","数据不存在");
//            MyLog.d("HeroAdapter:","数据已存在");
//        return view;
//    }
//
//    @Override
//    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        return null;
//    }
//
//    @Override
//    public void onBindViewHolder(Hero holder, int position) {
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return 0;
//    }
//
//    class ViewHolder extends RecyclerView.ViewHolder
//    {
//        int position=-1;
//       Button edit;
//       Button delete;
//       Button confirm;
//       Button update;
//       EditText name;
//       EditText cognoment;
//       RadioButton genderman;
//       RadioButton genderfamale;
//       EditText energy;
//       EditText force;
//       EditText wit;
//       EditText loyal;
//       EditText virtue;
//       TextView displayId;
//        public ViewHolder(View itemView) {
//            super(itemView);
//        }
//    }
//    private void setOnclickListener(final ViewHolder viewholder)
//    {
//        viewholder.edit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                setVisibility(viewholder,View.GONE,View.VISIBLE,View.VISIBLE,View.GONE);
//            }
//        });
//        viewholder.delete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                setVisibility(viewholder,View.GONE,View.GONE,View.GONE,View.VISIBLE);
//            }
//        });
//        viewholder.update.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                setEnable(viewholder,true);
//                setVisibility(viewholder,View.GONE,View.GONE,View.GONE,View.VISIBLE);
//            }
//        });
//        viewholder.confirm.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                setEnable(viewholder,false);
//                setVisibility(viewholder,View.VISIBLE,View.GONE,View.GONE,View.GONE);
//            }
//        });
//    }
//    private void setVisibility(ViewHolder viewHolder,int edit,int update,int delete,int confirm)
//    {
//        viewHolder.edit.setVisibility(edit);
//        viewHolder.delete.setVisibility(update);
//        viewHolder.update.setVisibility(delete);
//        viewHolder.confirm.setVisibility(confirm);
//    }
//    private void myCode()
//    {
//        StringBuilder stringBuilder=new StringBuilder("");
//        Hero hero=new Hero();
//        stringBuilder.append("姓名："+hero.getName()+"\n字："+hero.getCognomen()+
//                "\n性别："+hero.getGender()+"\n体力："+hero.getEnergy()
//                +"\n武力："+hero.getForce()+ "\n智慧："+hero.getWit()+
//                "\n忠诚："+hero.getLoyal()+"\n德行:"+hero.getVirtue()+"\n");
//    }
//    private void setEnable(ViewHolder viewHolder,boolean can)
//    {
//        viewHolder.name.setEnabled(can);
//        viewHolder.cognoment.setEnabled(can);
//        viewHolder.energy.setEnabled(can);
//        viewHolder.wit.setEnabled(can);
//        viewHolder.force.setEnabled(can);
//        viewHolder.loyal.setEnabled(can);
//        viewHolder.virtue.setEnabled(can);
//        viewHolder.genderfamale.setEnabled(can);
//        viewHolder.genderman.setEnabled(can);
//    }
//}

class HeroAdapter extends RecyclerView.Adapter<HeroAdapter.ViewHolder> {
//    对编辑碎片中的子项进行操作
        private Context context;
    private List<Hero> heros;
    private Onclicksomething onclicksomething;
    private int count=0;
    public HeroAdapter(@NonNull Context context, List<Hero> heros,Onclicksomething onclicksomething) {
        this.context = context;
        this.heros = heros;
        this.onclicksomething=onclicksomething;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewtype) {
        MyLog.d("HeroAdapter:","创建缓存器"+count);
        //创建次数较少
        View view=LayoutInflater.from(context).inflate(R.layout.fragmentitem,parent,false);
        ViewHolder viewholder=new ViewHolder(view);
        setEnable(viewholder, false);
        return viewholder;
    }
    @Override
    public void onBindViewHolder(ViewHolder  viewholder, int position) {
        //缓存器数量较小
        MyLog.d("HeroAdapter:","位置："+position+"viewholder的位置："+viewholder.position);
        Hero hero = heros.get(position);
        viewholder.virtue.setText(hero.getVirtue()+"");
        viewholder.name.setText(hero.getName());
        viewholder.cognoment.setText(hero.getCognomen());
        viewholder.energy.setText(hero.getEnergy()+"");
        viewholder.force.setText(hero.getForce()+"");
        viewholder.wit.setText(hero.getWit() +"");
        viewholder.loyal.setText(hero.getLoyal()+"");
        viewholder.displayid.setText("第" + hero.getId()+ "条数据");
        setOnclickListener(viewholder,position);
        if (hero.getGender())
            viewholder.genderman.setChecked(true);
        else
            viewholder.genderfamale.setChecked(true);
        }
    @Override
    public int getItemCount() {
        return heros.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        int position=0;
        boolean isExists=false;
       Button edit;
       Button delete;
       Button confirm;
       Button update;
       EditText name;
       EditText cognoment;
       RadioButton genderman;
       RadioButton genderfamale;
       EditText energy;
       EditText force;
       EditText wit;
       EditText loyal;
       EditText virtue;
       TextView displayid;
        public ViewHolder(View view) {
            super(view);
            edit = (Button) view.findViewById(R.id.reviewsqledit);
            delete = (Button) view.findViewById(R.id.reviewsqldelete);
            update = (Button) view.findViewById(R.id.reviewsqlupdate);
            confirm = (Button) view.findViewById(R.id.reviewsqlconfirm);
            name=(EditText)view.findViewById(R.id.name);
            cognoment=(EditText)view.findViewById(R.id.cognoment);
            energy=(EditText)view.findViewById(R.id.energy);
            force=(EditText)view.findViewById(R.id.force);
            wit=(EditText)view.findViewById(R.id.wit);
            loyal=(EditText)view.findViewById(R.id.loyal);
            virtue=(EditText)view.findViewById(R.id.virtue);
            genderfamale=(RadioButton)view.findViewById(R.id.genderfamale);
            genderman=(RadioButton)view.findViewById(R.id.genderman);
            displayid=(TextView)view.findViewById(R.id.displayId);
        }
    }
    private void setEnable(ViewHolder viewHolder,boolean can)
    {
        viewHolder.name.setEnabled(can);
        viewHolder.cognoment.setEnabled(can);
        viewHolder.energy.setEnabled(can);
        viewHolder.wit.setEnabled(can);
        viewHolder.force.setEnabled(can);
        viewHolder.loyal.setEnabled(can);
        viewHolder.virtue.setEnabled(can);
        viewHolder.genderfamale.setEnabled(can);
        viewHolder.genderman.setEnabled(can);
    }
        private void setOnclickListener(final ViewHolder viewholder, final int position)
    {
        viewholder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setVisibility(viewholder,View.GONE,View.VISIBLE,View.VISIBLE,View.GONE);
            }
        });
        viewholder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onclicksomething.delete(position))
                setVisibility(viewholder,View.VISIBLE,View.GONE,View.GONE,View.GONE);
            }
        });
        viewholder.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setEnable(viewholder,true);
                setVisibility(viewholder,View.GONE,View.GONE,View.GONE,View.VISIBLE);
            }
        });
        viewholder.confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Hero hero=new Hero();
                hero.setName(viewholder.name.getText().toString());
                hero.setCognomen(viewholder.cognoment.getText().toString());
                if(viewholder.genderman.isChecked())
                    hero.setGender(true);
                else
                    hero.setGender(false);
                hero.setEnergy(Integer.valueOf(viewholder.energy.getText().toString()));
                hero.setForce(Integer.valueOf(viewholder.force.getText().toString()));
                hero.setLoyal(Integer.valueOf(viewholder.loyal.getText().toString()));
                hero.setWit(Integer.valueOf(viewholder.wit.getText().toString()));
                hero.setVirtue(Integer.valueOf(viewholder.virtue.getText().toString()));
                onclicksomething.confirm(hero,position);
                setEnable(viewholder,false);
                setVisibility(viewholder,View.VISIBLE,View.GONE,View.GONE,View.GONE);
            }
        });
    }
        private void setVisibility(ViewHolder viewHolder,int edit,int update,int delete,int confirm)
    {
        viewHolder.edit.setVisibility(edit);
        viewHolder.delete.setVisibility(update);
        viewHolder.update.setVisibility(delete);
        viewHolder.confirm.setVisibility(confirm);
    }
}