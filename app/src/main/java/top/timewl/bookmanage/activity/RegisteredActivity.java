package top.timewl.bookmanage.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import top.timewl.bookmanage.R;


/**
 * Created by Time on 2018/5/25.
 */

public class RegisteredActivity extends Activity {
    private EditText phone;
    private Button next;
    private Button back;
    private EditText pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered);

        next = findViewById(R.id.next);
        phone =  findViewById(R.id.phone);
        pass = findViewById(R.id.et_pass);
        back =  findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisteredActivity.this,LoginActivity.class);
                startActivity(intent);

            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phonename = phone.getText().toString();
                if (phonename.length() <11){
                    Toast.makeText(RegisteredActivity.this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
                }
                else {
                    // 将账号密码储存在SharedPreferences中
                    SharedPreferences sp = getSharedPreferences("config",0);
                    SharedPreferences.Editor editor = sp.edit();
                    //将值存入编辑器
                    editor.putString("user",phone.getText().toString().trim());
                    editor.putString("pass",pass.getText().toString().trim());
                    editor.commit();
                    Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                    intent.putExtra("phone",phonename);
                    startActivity(intent);
                }

            }
        });
    }
}
