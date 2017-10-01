package com.example.uiwidgettest.broadcast;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.uiwidgettest.broadcast.seivice.DataListener;
import com.example.uiwidgettest.broadcast.seivice.ReceiveDatantentService;
import com.example.uiwidgettest.broadcast.seivice.StartService;
import com.example.uiwidgettest.MyLog;
import com.example.uiwidgettest.R;

import com.example.uiwidgettest.ui.sendorrecieve.intactActivity;

import java.util.regex.Pattern;

public class LoginActivity extends BaseActivity {
private final int LOGINACTIVITY=0;
    StringBuffer account=new StringBuffer("");
    StringBuffer password=new StringBuffer("");
    EditText editText;
    EditText editText1;
    CheckBox checkBox;
    SharedPreferences sharedPreferences;
    ProgressDialog dialog;
    Boolean isRemember;
//    StartService startService;
    private SharedPreferences.Editor editor;
    private Intent intent;
    private Intent serviceIntent;
    private DataListener logindata;
//    private ReceiveDatantentService.WeReceive weReceive;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        logindata=new DataListener() {
            @Override
            public void ReturnData(String data) {
                HandleData(data);
            }
            @Override
            public void Already() {
                //服务回调接口调用就开启向服务器发送请求的线程
//               MyLog.d("LoginActivity:","网络流："+s.getLocalAddress());
                MyLog.d("LoginActivity:","已经准备好了，可以向服务输出数据。");

//               new Thread(new LoginActivity()).start();
                //执行回调函数时实例化绑定器
            }
        };
        editText=(EditText)findViewById(R.id.inputaccount) ;
        editText1=(EditText)findViewById(R.id.inputpassword) ;
        checkBox=(CheckBox)findViewById(R.id.checkbox);
        Button forceoutline=(Button)findViewById(R.id.Forceoutline);
        //为了连接服务的实例
        serviceIntent=new Intent(LoginActivity.this,ReceiveDatantentService.class);
        //必须预先传入绑定器的回调接口设置
        StartService.setDataListener(logindata);
       if( bindService(serviceIntent,StartService.connection,BIND_AUTO_CREATE)) {
           MyLog.d("LoginActivity:","服务被绑定");
//           weReceive.SetDataListener(logindata);
       }
        //开启服务
        forceoutline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,ForceOffActivity1.class);
                startActivity(intent);
            }
        });
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkBox.isChecked())
                Toast.makeText(getBaseContext(),"下次登录将不需要输入密码。",Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getBaseContext(),"下次登录将需要输入密码。",Toast.LENGTH_SHORT).show();
            }
        });
        Button button=(Button)findViewById(R.id.login);
        sharedPreferences=getSharedPreferences(account.toString(), MODE_PRIVATE);
        editor =sharedPreferences.edit();
        isRemember=sharedPreferences.getBoolean("isRemember",false);
        if(isRemember)
        {
            checkBox.setChecked(true);
            editText.setText(sharedPreferences.getString("account", ""));
            editText1.setText("********");
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                account.replace(0,account.length(),editText.getText().toString());
                if(isRemember&&sharedPreferences.getString("password",null)!=null&&"********".equals(editText1.getText().toString()))
                {//确认用户是在重新输入密码
                    password.replace(0,password.length(),sharedPreferences.getString("password",null));
                }
                else
                password.replace(0,password.length(),editText1.getText().toString());

//                Toast.makeText(getBaseContext(),account.toString()+password.toString(),Toast.LENGTH_SHORT).show();
                if(account.toString().isEmpty()||password.toString().isEmpty())
                {
                    Toast.makeText(getBaseContext(),"账号或者密码未输入",Toast.LENGTH_SHORT).show();
                }
                else if(!validatePassword(password.toString()))
                {
                  editText1.setEnabled(true);
                    editText1.setError("密码格式不正确，至少包含四个数字且只能使用数字或字母");
                }
                else if(!validateAccount(account.toString()))
                {
                    editText.setEnabled(true);
                    editText.setError("账号格式不正确，只能使用数字");
                }
                else
                {
           intent = new Intent(LoginActivity.this,intactActivity.class );
if(dialog==null) {
    dialog = new ProgressDialog(LoginActivity.this);
    dialog.setMessage("正在登录...");
    dialog.setCanceledOnTouchOutside(false);
}
dialog.show();
//weReceive=startService.getWeReceive();//实例化绑定
  StartService.weReceive.SetCurrentActivity(LOGINACTIVITY);
  StartService.weReceive.WriteToService("登录:"+account.toString()+":"+password.toString()+":");
                }
//                else
//                {
//                    Toast.makeText(getBaseContext(),"登录失败",
//                            Toast.LENGTH_SHORT).show();
//                    editText1.setText("");
//                }
            }
        });
    }
    public boolean validatePassword(String password)
    {
        //验证密码合法性
      String ValidatePassword="^[\\d{4,}]+[a-zA-Z0-9]$";
        Pattern pattern=Pattern.compile(ValidatePassword);
        MyLog.d("LoginActivity:","Password:"+pattern.matcher("12344a").matches());
        return pattern.matcher(password).matches();
    }
    public boolean validateAccount(String Account)
    {
        //验证密码合法性
        String ValidateAccount="^[0-9]*$";
        Pattern pattern=Pattern.compile(ValidateAccount);
        MyLog.d("LoginActivity:","Account:"+pattern.matcher("12312434").matches());
        return pattern.matcher(Account).matches();
    }

    private void HandleData(String content)
    {

        MyLog.d("LoginActivity:","当前线程："+Thread.currentThread().getName());
        //这是服务器线程
        MyLog.d("LoginActivity:","从服务器接收到的数据："+content.split(":")[0]);
            if(content.split(":")[0].equals("登录成功"))
            {
                if(checkBox.isChecked())
                {
                    editor.putBoolean("isRemember",true);
                    editor.putString("account",account.toString());
                    editor.putString("password",password.toString());
                    MyLog.d(TAG,"账号："+account.toString()+"密码"+password.toString());
                    editor.apply();
                }
                else
                {
                    editor.putBoolean("isRemember",false);
                    editor.apply();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            editText.setText("");
                            editText1.setText("");
                        }
                    });

                }
                dialog.dismiss();
                //新建个类放入socket 到聊天再取出
                //登录成功将账号传递给下一个活动
                intent.putExtra("account",account.toString());
                StartService.weReceive.SetCurrentAccount(account.toString());
                account.setLength(0);
                password.setLength(0);
                StartService.weReceive.SetPassword(password.toString());
                //登录成功应该解绑 再重新绑定 不关闭服务
//                MyLog.d("LoginActivity:","即将解绑");
//                getBaseContext().unbindService(startService.getConnection());
//                intent.putExtra("WeReceive",StartService.weReceive);//将weReceive传递给下一个活动以方便下一活动
//                重新构建回调接口
                startActivity(intent);
            }
            else if(content.split(":")[0].equals("登录失败"))
            {
                //从服务器收到了两条信息啊啊啊！！所以else应当用if
                MyLog.d("LoginActivity:","如若登录成功，此句不应执行");
                dialog.dismiss();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getBaseContext(),"登录失败",
                                Toast.LENGTH_SHORT).show();
                        account.setLength(0);
                        password.setLength(0);//很重要 否则或出现登录不成功
                        editText1.setText("");
                    }
                });

            }
            else if(content.split(":")[0].equals("强制下线"))
            {
                Intent intent=new Intent("com.example.uiwidgettest.GOFORITOUTLINE");
                sendBroadcast(intent);
            }
        }

    @Override
    protected void onRestart() {
        super.onRestart();
        MyLog.d("LoginActivity:","Restart重启活动");
        if( bindService(serviceIntent,StartService.connection,BIND_AUTO_CREATE)) {
            MyLog.d("LoginActivity:","服务已被绑定");
            StartService.weReceive.SetCurrentActivity(LOGINACTIVITY);
            StartService.weReceive.SetDataListener(logindata);
        }
    }

//    @Override
//    protected void onStart() {
//        MyLog.d("LoginActivity:","Onstart重启活动");
//        super.onStart();
//    }//会启动

//    @Override
//    protected void OnResume() {
//        MyLog.d("LoginActivity:","Resume重启活动");
//        super.OnResume();
//    }
}
