package top.timewl.bookmanage.activity.viewmodel;

import android.content.Context;
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


public class AddBookViewModel {
    private Context mContext = MyApplication.getInstance().getApplicationContext();
    private String TAG = getClass().getName();
    private String localHost = MyApplication.getInstance().getLocalHost();
    public ObservableField<String> name = new ObservableField<>();
    public ObservableField<String> author = new ObservableField<>();
    public ObservableField<String> press = new ObservableField<>();
    public ObservableField<String> publishDate = new ObservableField<>();
    public ObservableField<String> labels = new ObservableField<>();
    public ObservableField<String> isbn = new ObservableField<>();
    public ObservableField<String> price = new ObservableField<>();
    public ObservableField<String> coverUrl = new ObservableField<>();
    private Handler mHandler = new android.os.Handler(Looper.getMainLooper());



    public void onAddBook(){
        OkHttpClient client = new OkHttpClient();
        FormBody.Builder formbody = new FormBody.Builder();
        formbody.add("name",(name.get() != null)? name.get() : "");
        formbody.add("author",(author.get() != null)? author.get() : "");
        formbody.add("press",(press.get() != null) ? press.get() : "");
        formbody.add("publish_date",(publishDate.get() != null) ? publishDate.get() : "");
        formbody.add("labels",(labels.get() != null) ? labels.get() : "");
        formbody.add("isbn",(isbn.get() != null) ? isbn.get() : "");
        formbody.add("price",(price.get() != null) ? price.get() : "");
        formbody.add("cover_url",(coverUrl.get() != null) ? coverUrl.get() : "");
        Request request = new Request.Builder()
                .url(localHost + "book/manualAddBook/")
                .post(formbody.build())
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d(TAG,"服务器错误");
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(mContext, "服务器错误", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                Log.d(TAG,"添加成功");
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(mContext, "添加成功", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
