package com.example.uiwidgettest.myreview.contentprovide;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.uiwidgettest.R;

public class DispalyCP extends AppCompatActivity implements View.OnClickListener {
private TextView display;
    private Button getData;
    private EditText inputuri;
    private final String INTRODUCE="内容提供器提供的接口：hero/4\n" +
            "查询范围：hero表中武力<80\n"+
            "内容提供器提供的接口：hero/5\n" +
                    "查询范围：hero表中id<5\n"+
            "内容提供器提供的接口：*\n" +
            "查询范围：hero全表\n";
    private final String HINTURI="content://com.example.uiwidgettest.heroprovider/*";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispaly_cp);
        display=(TextView)findViewById(R.id.display);
        getData=(Button) findViewById(R.id.getdatafromCP);
        getData.setOnClickListener(this);
        inputuri=(EditText)findViewById(R.id.inputuri);
        inputuri.setText(HINTURI);
        display.setText(INTRODUCE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.getdatafromCP:
                Uri uri=Uri.parse(inputuri.getText().toString());
                display.setText(INTRODUCE+new GetDataFromCP().GetData(this,uri));
                break;
        }
    }
}
