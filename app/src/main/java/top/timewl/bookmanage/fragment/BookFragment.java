package top.timewl.bookmanage.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import top.timewl.bookmanage.MyApplication;
import top.timewl.bookmanage.R;
import top.timewl.bookmanage.activity.BookActivity;
import top.timewl.bookmanage.activity.SearchActivity;
import top.timewl.bookmanage.databinding.RecycleBookBinding;
import top.timewl.bookmanage.entity.BookInfo;

public class BookFragment extends BaseFragment{
    private String localHost = MyApplication.getInstance().getLocalHost();
    private String TAG = "BookFragment";
    private View view;
    private Button search;
    private RecyclerView recyclerView;
    private List<BookInfo> mBookInfos;
    private Handler mHandler = new Handler(Looper.getMainLooper());

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_book,container,false);
        initView();
        initData();

        initListener();
        return view;

    }

    private void initList() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false));
        BookAdapter bookAdapter = new BookAdapter(getContext(), mBookInfos);
        bookAdapter.setListener(new BookAdapter.BookOnClickListener() {
            @Override
            public void onItemClick(View view, int pos, BookInfo bookInfo) {
                Intent intent = new Intent(getContext(), BookActivity.class);
                intent.putExtra("book",bookInfo);
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(bookAdapter);
    }

    private void initData() {
        mBookInfos = new ArrayList<>();
        OkHttpClient client = new OkHttpClient();
        Request request  = new Request.Builder()
                .url(localHost+"book/searchBook/")
                .get()
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d(TAG,"服务器错误");
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getContext(), "服务器错误", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String responseBody = response.body().string();
                try {
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
                    }
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            initList();
                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
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

    public static class BookAdapter extends RecyclerView.Adapter<BookViewHolder>{
        private Context mContext;
        private List<BookInfo> mData;
        private BookOnClickListener mListener;

        public interface BookOnClickListener{
            void onItemClick(View view,int pos, BookInfo bookInfo);
        }

        public void setListener(BookOnClickListener listener){
            this.mListener = listener;
        }

        public BookAdapter(Context mContext, List<BookInfo> mData) {
            this.mContext = mContext;
            this.mData = mData;
        }

        @NonNull
        @Override
        public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            RecycleBookBinding bookBinding = DataBindingUtil.inflate(inflater,
                    R.layout.recycle_book,parent,false);
            BookViewHolder viewHolder = new BookViewHolder(bookBinding);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
            RecycleBookBinding binding = holder.getBinding();
            BookInfo bookInfo = mData.get(position);
            binding.bookImage.setImageUrl(bookInfo.getCover_url());
            binding.nameTxt.setText(bookInfo.getName());
            binding.authorTxt.setText(bookInfo.getAuthor());
            binding.pressTxt.setText(bookInfo.getPress());
            binding.dateTxt.setText(bookInfo.getPublish_date());
            if (mListener != null){
                binding.getRoot().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mListener.onItemClick(v,position,bookInfo);
                    }
                });
            }
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }
    }
    static class BookViewHolder extends RecyclerView.ViewHolder{
        private RecycleBookBinding mBinding;
        public BookViewHolder(@NonNull RecycleBookBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }
        public RecycleBookBinding getBinding(){
            return mBinding;
        }

    }
}
