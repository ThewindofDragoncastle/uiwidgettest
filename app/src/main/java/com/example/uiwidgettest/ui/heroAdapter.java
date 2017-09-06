package com.example.uiwidgettest.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.uiwidgettest.R;

import java.util.List;

/**
 * Created by 40774 on 2017/7/2.
 */

public class heroAdapter extends ArrayAdapter<Hero>{
    private int Id;
    public heroAdapter(Context ct, int tvid, List<Hero> obj)
    {
    super(ct,tvid,obj);
        Id=tvid;
    }
    public View getView(int position, View cv, ViewGroup parent)
    {
        Hero he=getItem(position);
        View vi;viewsave vs;
        if(cv==null) {
            vi = LayoutInflater.from(getContext()).inflate(Id, parent, false);
            vs=new viewsave();
            vs.image=(ImageView)vi.findViewById(R.id.hero_1);
            vs.name=(TextView)vi.findViewById(R.id.hero_2);
            vi.setTag(vs);
        }
        else {
            vi = cv;
           vs=(viewsave) vi.getTag();
        }
        vs.image.setImageResource(he.getImage());
        vs.name.setText(he.getName());
        return vi;
    }
class viewsave
{
    ImageView image;
    TextView  name;
}
}
