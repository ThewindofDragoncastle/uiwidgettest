package com.example.uiwidgettest.TheLight;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.uiwidgettest.R;

public class cardview extends AppCompatActivity {
 private CardView cardView;
    private SeekBar seekBar1;
    private SeekBar seekBar2;
    private SeekBar seekBar3;
    private TextView protv1;
    private TextView protv2;
    private TextView protv3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cardview);
        assignViews();
    }
    private  void assignViews()
    {
        cardView=(CardView)findViewById(R.id.tv_item);
        seekBar1=(SeekBar)findViewById(R.id.sb1);
        seekBar2=(SeekBar)findViewById(R.id.sb2);
        seekBar3=(SeekBar)findViewById(R.id.sb3);
        protv1=(TextView)findViewById(R.id.protv1);
        protv2=(TextView)findViewById(R.id.protv2);
        protv3=(TextView)findViewById(R.id.protv3);

        seekBar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                cardView.setRadius(progress);
                protv1.setText("进度:"+progress+"%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                protv1.setText("拖动开始！");
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        seekBar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                cardView.setElevation(progress);
                protv2.setText("进度:"+progress+"%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                protv2.setText("拖动开始！");
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        seekBar3.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                cardView.setContentPadding(progress,progress,progress,progress);
                protv3.setText("进度:"+progress+"%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                protv3.setText("拖动开始！");
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }
}
