package top.timewl.bookmanage.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import top.timewl.bookmanage.MyApplication;
import top.timewl.bookmanage.R;


/**
 * Created by Time on 2018/5/25.
 */

public class RegisteredActivity extends Activity {
    private EditText phone;
    private Button next;
    private Button back;
    private EditText pass;
    private RadioGroup radioGroup;
    private Handler mHandler = new android.os.Handler(Looper.getMainLooper());
    private String TAG = getClass().getName();
    private String localHost = MyApplication.getInstance().getLocalHost();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered);

        next = findViewById(R.id.next);
        phone =  findViewById(R.id.phone);
        pass = findViewById(R.id.et_pass);
        back =  findViewById(R.id.back);
        radioGroup = findViewById(R.id.gp_gender);




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
//                    // 将账号密码储存在SharedPreferences中
//                    SharedPreferences sp = getSharedPreferences("config",0);
//                    SharedPreferences.Editor editor = sp.edit();
//                    //将值存入编辑器
//                    editor.putString("user",phone.getText().toString().trim());
//                    editor.putString("pass",pass.getText().toString().trim());
//                    editor.commit();
//                    Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
//                    intent.putExtra("phone",phonename);
                    int checkedRadioButtonId = radioGroup.getCheckedRadioButtonId();
                    String gender = "male";
                    if (checkedRadioButtonId == R.id.female){
                        gender = "female";
                    }
                    OkHttpClient client = new OkHttpClient();
                    FormBody.Builder formbody = new FormBody.Builder();
                    formbody.add("name",phone.getText().toString().trim());
                    formbody.add("password",pass.getText().toString().trim());
                    formbody.add("permission","0");
                    formbody.add("gender",gender);
                    Request request = new Request.Builder()
                            .url(localHost + "user/creatUser/")
                            .post(formbody.build())
                            .build();
                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(@NotNull Call call, @NotNull IOException e) {
                            Log.d(TAG,"服务器错误");
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(RegisteredActivity.this, "服务器错误", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                        @Override
                        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                            Log.d(TAG,"添加成功");
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(RegisteredActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(RegisteredActivity.this,LoginActivity.class));
                                }
                            });
                        }
                    });
                }

            }
        });
    }
}
