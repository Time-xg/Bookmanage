package top.timewl.bookmanage.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import top.timewl.bookmanage.databinding.RecycleBorrowBinding;
import top.timewl.bookmanage.entity.Borrow;
import top.timewl.bookmanage.fragment.viewmodel.BorrowInfoViewModel;

public class BorrowFragment extends BaseFragment{
    private String localHost = MyApplication.getInstance().getLocalHost();
    private String TAG = getClass().getName();
    private View view;
    private RecyclerView recyclerView;
    private List<Borrow> mBorrowList;
    private Handler mHandler = new Handler(Looper.getMainLooper());

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_borrow,container,false);
        initView();
        initData();
        return view;
    }

    private void initData() {
        mBorrowList = new ArrayList<>();
        OkHttpClient client = new OkHttpClient();
        Request request  = new Request.Builder()
                .url(localHost+"borrow/searchBorrow/")
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
                    JSONArray books = (JSONArray) jsonObject.get("borrows");
                    for (int i = 0; i < books.length(); i++) {
                        JSONObject bookObject = (JSONObject) books.get(i);
                        JSONObject fields = (JSONObject) bookObject.get("fields");
                        int id = (int) bookObject.get("pk");
                        Gson gson = new Gson();
                        Borrow borrow = gson.fromJson(fields.toString(),Borrow.class);
                        borrow.setId(id);
                        mBorrowList.add(borrow);
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
    private void initList() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false));
        BorrowAdapter borrowAdapter = new BorrowAdapter();
        recyclerView.setAdapter(borrowAdapter);
    }

    private void initView() {
        recyclerView = view.findViewById(R.id.borrow_list);
    }


    class BorrowAdapter extends RecyclerView.Adapter<BorrowViewHolder>{

        @NonNull
        @Override
        public BorrowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            RecycleBorrowBinding binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()),
                    R.layout.recycle_borrow,parent,false);
            BorrowViewHolder viewHolder = new BorrowViewHolder(binding);

            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull BorrowViewHolder holder, int position) {
            holder.updateList(mBorrowList.get(position));
        }

        @Override
        public int getItemCount() {
            return mBorrowList.size();
        }
    }
    class BorrowViewHolder extends RecyclerView.ViewHolder{
        private RecycleBorrowBinding mBinding;

        public BorrowViewHolder(@NonNull RecycleBorrowBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
            mBinding.setViewModel(new BorrowInfoViewModel());
        }
        public void updateList(Borrow borrow){
            BorrowInfoViewModel viewModel = mBinding.getViewModel();
            viewModel.setBorrow(borrow);
            mBinding.executePendingBindings();
        }
    }
}
