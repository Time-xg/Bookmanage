package top.timewl.bookmanage.Fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.lang.annotation.Target;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import top.timewl.bookmanage.Activity.ScanActivity;
import top.timewl.bookmanage.Activity.SearchActivity;
import top.timewl.bookmanage.MyApplication;
import top.timewl.bookmanage.R;
import top.timewl.bookmanage.View.CustomTitleBar;

public class BookFragment extends BaseFragment{
    private String localHost = MyApplication.getInstance().getLocalHost();
    private String TAG = "BookFragment";
    private View view;
    private Button search;
    private RecyclerView recyclerView;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_book,container,false);
        initView();
        initData();
        initList();
        initListener();
        return view;

    }

    private void initData() {
        OkHttpClient client = new OkHttpClient();
        Request request  = new Request.Builder()
                .url(localHost+"book/searchBook")
                .get()
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                Log.d(TAG,response.body().string());
            }
        });
    }
    private void initList() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false));
//        recyclerView.setAdapter();
    }


    private void initListener() {


        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SearchActivity.class);
                startActivity(intent);
            }
        });

    }




    private void initView() {

        search = view.findViewById(R.id.search_bt);
        recyclerView = view.findViewById(R.id.book_list);
    }
}
