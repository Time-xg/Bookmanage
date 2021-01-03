package top.timewl.bookmanage.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import top.timewl.bookmanage.fragment.BookFragment;
import top.timewl.bookmanage.MyApplication;
import top.timewl.bookmanage.R;
import top.timewl.bookmanage.entity.BookInfo;

public class SearchActivity extends Activity {

    private String localHost = MyApplication.getInstance().getLocalHost();
    private SearchView searchView;
    private TextView cancel;
    private RecyclerView listView;
    private List<BookInfo> mBookInfos;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initView();
        initListener();

    }

    private void updateItem(){
        listView.setLayoutManager(new LinearLayoutManager(SearchActivity.this,
                RecyclerView.VERTICAL,
                false));
        BookFragment.BookAdapter bookAdapter = new BookFragment.BookAdapter(SearchActivity.this, mBookInfos);
        bookAdapter.setListener(new BookFragment.BookAdapter.BookOnClickListener() {
            @Override
            public void onItemClick(View view, int pos, BookInfo bookInfo) {
                Intent intent = new Intent(SearchActivity.this, BookActivity.class);
                intent.putExtra("book",bookInfo);
                startActivity(intent);
            }
        });
        listView.setAdapter(bookAdapter);
    }

    private void initListener() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mBookInfos = new ArrayList<>();
                OkHttpClient client = new OkHttpClient();
                FormBody.Builder body = new FormBody.Builder();
                body.add("content",query);
                Request request = new Request.Builder()
                        .url(localHost + "book/searchBook/")
                        .post(body.build())
                        .build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(SearchActivity.this, "服务器错误", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        try {
                            String responseBody = response.body().string();
                            JSONObject jsonObject = new JSONObject(responseBody);
                            JSONArray books = (JSONArray) jsonObject.get("books");
                            for (int i = 0; i < books.length(); i++) {
                                JSONObject bookObject = (JSONObject) books.get(i);
                                JSONObject fields = (JSONObject) bookObject.get("fields");
                                int id = (int) bookObject.get("pk");
                                Gson gson = new Gson();
                                BookInfo bookInfo = gson.fromJson(fields.toString(),BookInfo.class);
                                bookInfo.setId(id);
                                mBookInfos.add(bookInfo);
                                mHandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        updateItem();
                                    }
                                });
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initView() {
        searchView = findViewById(R.id.search);
        cancel = findViewById(R.id.cancel_btn);
        listView = findViewById(R.id.list_item);
    }
}
