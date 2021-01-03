package top.timewl.bookmanage.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import top.timewl.bookmanage.R;
import top.timewl.bookmanage.activity.viewmodel.BookViewModel;
import top.timewl.bookmanage.databinding.ActivityBookBinding;
import top.timewl.bookmanage.entity.BookInfo;

public class BookActivity extends Activity {

    private ActivityBookBinding mBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(BookActivity.this, R.layout.activity_book);
        initData();

    }

    private void initData() {
        Intent intent = getIntent();
        BookInfo bookInfo = (BookInfo) intent.getSerializableExtra("book");
        BookViewModel viewModel = new BookViewModel();
        viewModel.setBookInfo(bookInfo);
        mBinding.setViewModel(viewModel);
    }
}
