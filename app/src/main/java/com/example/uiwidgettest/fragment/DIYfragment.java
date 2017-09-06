package com.example.uiwidgettest.fragment;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.uiwidgettest.R;

public class DIYfragment extends AppCompatActivity {
    int i=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diyfragment);
        Button b1=(Button)findViewById(R.id.Buttonfragment1);
        final RightFragment rightFragment=new RightFragment();
        final AnnotherFragment annotherFragment=new AnnotherFragment();

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if(i%2==0)
//                replaceFragment(rightFragment);
//                else
//               replaceFragment(annotherFragment);
//                i++;
            }
        });
    }
//
//    public void replaceFragment(Fragment fragment)
//    {
//        FragmentManager fragmentManager=getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
//        fragmentTransaction.replace(R.id.Right_fragment,fragment);
//        fragmentTransaction.commit();
//    }
}
