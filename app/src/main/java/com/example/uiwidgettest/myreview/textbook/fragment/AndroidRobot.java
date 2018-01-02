package com.example.uiwidgettest.myreview.textbook.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.uiwidgettest.R;
import com.example.uiwidgettest.myreview.textbook.view.AndroidRobotView;

/**
 * Created by 40774 on 2017/12/29.
 */

public class AndroidRobot extends Fragment {
    private Button enlarge;
    private TextView display;
    private int size=1;
    private AndroidRobotView robotView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.textbook_9_25,container,false);
        enlarge=(Button) view.findViewById(R.id.enlarge);
        display=(TextView)view.findViewById(R.id.display);
        robotView=(AndroidRobotView)view.findViewById(R.id.robot_view);
        display.setText("正常大小");
        enlarge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                size++;
                switch (size)
                {
                    case 1:
                        display.setText("正常大小");
                        break;
                    default:
                        display.setText(size+"倍大小");
                }
                if (size==3)
                    size=0;
                robotView.setSize(size);
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
