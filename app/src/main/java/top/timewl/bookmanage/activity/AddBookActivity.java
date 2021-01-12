package top.timewl.bookmanage.activity;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import top.timewl.bookmanage.R;
import top.timewl.bookmanage.activity.viewmodel.AddBookViewModel;
import top.timewl.bookmanage.databinding.ActivityAddbookBinding;
import top.timewl.bookmanage.databinding.ActivityBookBinding;

public class AddBookActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityAddbookBinding binding = DataBindingUtil.setContentView(AddBookActivity.this, R.layout.activity_addbook);
        binding.setViweModel(new AddBookViewModel());

    }
}
