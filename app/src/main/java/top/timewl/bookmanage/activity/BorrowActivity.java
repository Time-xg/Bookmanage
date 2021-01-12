package top.timewl.bookmanage.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import top.timewl.bookmanage.R;
import top.timewl.bookmanage.activity.viewmodel.BorrowViewModel;
import top.timewl.bookmanage.databinding.ActivityBorrowBinding;

public class BorrowActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String isbn = intent.getStringExtra("isbn");

        ActivityBorrowBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_borrow);
        BorrowViewModel viewModel = new BorrowViewModel();
        viewModel.setIsbn(isbn);
        binding.setViewModel(viewModel);
    }
}
