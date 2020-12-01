package top.timewl.bookmanage.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import top.timewl.bookmanage.R;
import top.timewl.bookmanage.View.CustomTitleBar;

public class BorrowFragment extends BaseFragment{

    private View view;
    private CustomTitleBar titleBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_borrow,container,false);
        initView();
        return view;
    }

    private void initView() {

    }
}
