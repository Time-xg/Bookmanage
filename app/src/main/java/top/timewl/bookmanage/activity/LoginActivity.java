package top.timewl.bookmanage.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import top.timewl.bookmanage.R;

public class LoginActivity extends Activity {
    private Button login;
    private EditText et_phone;
    private EditText et_pass;
    private TextView registered;
    private CheckBox remain;
    private SharedPreferences sp;
    private String phonename;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //查找关心控件
        login =  findViewById(R.id.login);
        et_phone =  findViewById(R.id.phone);
        et_pass =  findViewById(R.id.pass);
        registered =  findViewById(R.id.registered);
        remain =  findViewById(R.id.remain);

        //sp读取数据
        sp = getSharedPreferences("config",0);
        phonename = sp.getString("user",null);
        password = sp.getString("pass",null);
        Boolean flag = sp.getBoolean("remain",false);
        if(flag){
            et_phone.setText(phonename);
            et_pass.setText(password);
            remain.setChecked(true);
        }



        login.setOnClickListener(new Loginbutton());
        registered.setOnClickListener(new Registered());
    }
    //注册点击事件
    class Registered implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(LoginActivity.this,RegisteredActivity.class);
            startActivity(intent);
        }
    }
    //登陆点击事件
    class Loginbutton implements View.OnClickListener{
        @Override
        public void onClick(View v) {

            String phone = et_phone.getText().toString();
            String pass= et_pass.getText().toString();
            SharedPreferences.Editor editor = sp.edit();

            if(remain.isChecked()){
                editor.putBoolean("remain",true);
                editor.commit();
            }

            // 账号密码为空
            if (pass == null || phone ==null){
                Toast.makeText(LoginActivity.this, "手机号或者密码不许为空", Toast.LENGTH_SHORT).show();
            }
            // 手机号不符合规范
            else if (phone.length() < 11){
                Toast.makeText(LoginActivity.this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
            }

            else if (pass.equals(password) && phone.equals(phonename)){
                Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                startActivity(intent);
            }
        }
    }
}
