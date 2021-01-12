package top.timewl.bookmanage.activity.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.databinding.ObservableField;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import top.timewl.bookmanage.MyApplication;
import top.timewl.bookmanage.activity.ScanActivity;

public class BorrowViewModel {
    private String localHost = MyApplication.getInstance().getLocalHost();
    private Context mContext = MyApplication.getInstance().getApplicationContext();
    public ObservableField<String> userId = new ObservableField<>();
    public ObservableField<String> borrowDay = new ObservableField<>();
    private String mIsbn;
    private Handler handler = new Handler(Looper.myLooper());


    public void setIsbn(String isbn){
        this.mIsbn = isbn;
    }

    public void borrowBookOnClick(){
        if (mIsbn == null || userId.get() == null || borrowDay.get() == null){
            Toast.makeText(mContext, "请先添加信息", Toast.LENGTH_SHORT).show();
            return;
        }
        OkHttpClient client = new OkHttpClient();
        FormBody.Builder formbody = new FormBody.Builder();
        formbody.add("isbn",mIsbn);
        formbody.add("userID", userId.get());
        formbody.add("days", borrowDay.get());
        Request request = new Request.Builder()
                .url(localHost + "book/addBook/")
                .post(formbody.build())
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(mContext, "服务器错误", Toast.LENGTH_SHORT).show();

                    }
                });

            }
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(mContext, "借阅成功", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    public void scanBookOnClick(){
        Intent intent = new Intent(mContext, ScanActivity.class);
        intent.putExtra("action","borrow");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);

    }

}
