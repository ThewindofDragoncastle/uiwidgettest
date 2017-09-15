package com.example.uiwidgettest.Broadcast;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.uiwidgettest.MyLog;
import com.example.uiwidgettest.R;
import com.example.uiwidgettest.ui.Sendorrecieve.Intact;
import com.example.uiwidgettest.ui.Sendorrecieve.intactActivity;
import com.example.uiwidgettest.ui.Warehousesocket;
import com.example.uiwidgettest.ui.chatgui;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.regex.Pattern;

public class LoginActivity extends BaseActivity {

    StringBuffer account=new StringBuffer("");
    StringBuffer password=new StringBuffer("");
    EditText editText;
    EditText editText1;
    CheckBox checkBox;
    DataInputStream inputStream;
    SharedPreferences sharedPreferences;
    DataOutputStream dos;
    Socket socket;
    ProgressDialog dialog;
    Boolean isRemember;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        editText=(EditText)findViewById(R.id.inputaccount) ;
        editText1=(EditText)findViewById(R.id.inputpassword) ;
        checkBox=(CheckBox)findViewById(R.id.checkbox);
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
        final SharedPreferences.Editor editor =sharedPreferences.edit();
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
                  final  Intent intent = new Intent(LoginActivity.this,intactActivity.class );
if(dialog==null) {
    dialog = new ProgressDialog(LoginActivity.this);
    dialog.setMessage("正在登录...");
    dialog.setCanceledOnTouchOutside(false);
}dialog.show();

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                 socket = new Socket("39.108.123.220",30000);
//            socket = new Socket("172.18.242.163",30000);
                                Log.d("ChatfromServer","成功连接服务器");
                                dos=new DataOutputStream(socket.getOutputStream());
                                dos.writeUTF("登录:"+account.toString()+":"+password.toString()+":");
                                String content;
                                    inputStream=new DataInputStream(socket.getInputStream());
                                    while((content=inputStream.readUTF())!=null)
                                    {
                                        MyLog.d("LoginActivity:","从服务器接收到的数据："+content.split(":")[0]);
                                         if(content.split(":")[0].equals("登录成功"))
                                         {
                                             if(checkBox.isChecked())
                                             {
                                                 editor.putBoolean("isRemember",true);
                                                 editor.putString("account",account.toString());
                                                 editor.putString("password",password.toString());
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
                                             closeAll();
                                             //新建个类放入socket 到聊天再取出
                                             //登录成功将账号传递给下一个活动
                                             intent.putExtra("account",account.toString());
                                             account.setLength(0);
                                             password.setLength(0);
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
                                    }


                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                finally {
                                    try {
                                            if(socket!=null)
                                            socket.close();
                                        if(dos!=null)
                                            dos.close();
                                        if(inputStream!=null)
                                            inputStream.close();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                }

                            }
                        }).start();

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
    private void closeAll() {
        try {
            if(socket!=null)
                                            socket.close();
            if (dos != null)
                dos.close();
            if (inputStream != null)
                inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
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

}
