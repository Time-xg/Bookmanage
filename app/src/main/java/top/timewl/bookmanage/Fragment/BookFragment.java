package top.timewl.bookmanage.Fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
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

import top.timewl.bookmanage.Activity.ScanActivity;
import top.timewl.bookmanage.Activity.SearchActivity;
import top.timewl.bookmanage.R;
import top.timewl.bookmanage.View.CustomTitleBar;

public class BookFragment extends BaseFragment{

    private String TAG = "BookFragment";
    private View view;
    private Button search;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_book,container,false);
        initView();
        initListener();
        return view;

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
    }
}
