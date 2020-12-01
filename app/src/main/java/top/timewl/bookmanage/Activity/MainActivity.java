package top.timewl.bookmanage.Activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import top.timewl.bookmanage.Fragment.BookFragment;
import top.timewl.bookmanage.Fragment.BorrowFragment;
import top.timewl.bookmanage.Fragment.ReaderFragment;
import top.timewl.bookmanage.Fragment.SystemFragment;
import top.timewl.bookmanage.R;
import top.timewl.bookmanage.View.CustomTitleBar;
import top.timewl.bookmanage.utils.PopupMenuUtil;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private String TAG = getClass().getName();
    private Fragment currentFragment = new Fragment();
    private List<Fragment> fragmentsContainer = new ArrayList<Fragment>();
    private static final String CURRENT_FRAGMENT = "STATE_FRAGMENT_SHOW";
    private int position = 0;
    private TextView tv_book;
    private TextView tv_borrow;
    private TextView tv_reader;
    private TextView tv_system;
    private ImageView bt_book;
    private ImageView bt_borrow;
    private ImageView bt_reader;
    private ImageView bt_system;
    private RelativeLayout contain;
    private LinearLayout book_layout;
    private LinearLayout borrow_layout;
    private LinearLayout reader_layout;
    private LinearLayout system_layout;
    private CustomTitleBar customTitleBar;
    private LinearLayout buttonGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState != null){
            position = savedInstanceState.getInt(CURRENT_FRAGMENT,0);
            contain.removeAllViews();
        }
        initView();
        initListener();
        removeFragment();
        initFragment();
        showFragment(position);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void showPopupWindow() {
        View popupView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_addbook,null);
        LinearLayout scanner_add = popupView.findViewById(R.id.scanner_add);
        LinearLayout add = popupView.findViewById(R.id.add);
        PopupWindow popupWindow = new PopupWindow(popupView, 360, 205);
        popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_more_select));
        popupWindow.setOutsideTouchable(true);
        popupWindow.showAsDropDown(customTitleBar,-40, 10, Gravity.RIGHT);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        scanner_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                Intent intent = new Intent(getApplicationContext(), ScanActivity.class);
                startActivity(intent);

            }
        });

    }

    private void showBorrowPopup(){

    }

    private void initListener() {
        book_layout.setOnClickListener(this);
        borrow_layout.setOnClickListener(this);
        reader_layout.setOnClickListener(this);
        system_layout.setOnClickListener(this);
    }

    private void showFragment(int position) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        switch (position){
            case 0:
                removeFragment();
                bt_book.setEnabled(true);
                tv_book.setTextColor(getResources().getColor(R.color.blue));
                customTitleBar.setTitle("图书管理");
                customTitleBar.setRightOnclickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onClick(View v) {
                        showPopupWindow();
                    }
                });

                break;
            case 1:
                removeFragment();
                bt_borrow.setEnabled(true);
                tv_borrow.setTextColor(getResources().getColor(R.color.blue));
                customTitleBar.setTitle("借阅管理");
                customTitleBar.setRightOnclickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onClick(View v) {
                        PopupMenuUtil.getInstance()._show(getApplicationContext(), buttonGroup);
                    }
                });
                break;
            case 2:
                removeFragment();
                bt_reader.setEnabled(true);
                tv_reader.setTextColor(getResources().getColor(R.color.blue));
                customTitleBar.setTitle("读者管理");

                break;
            case 3:
                removeFragment();
                bt_system.setEnabled(true);
                tv_system.setTextColor(getResources().getColor(R.color.blue));
                customTitleBar.setTitle("系统");

                break;
            default:
                Log.e(TAG, "Position error");
                break;
        }
        // 如果展示出来的Fragment是添加过的 隐藏当前Fragment 显示这个Frgament
        if (fragmentsContainer.get(position).isAdded()){
            transaction.hide(currentFragment).show(fragmentsContainer.get(position));
        }
        else {
            transaction.hide(currentFragment).add(R.id.fragment_container,fragmentsContainer.get(position),
                    "currentFragmet" + position);
        }
        currentFragment = fragmentsContainer.get(position);
        transaction.commit();
    }

    private void removeFragment() {
        tv_book.setTextColor(getResources().getColor(R.color.black));
        tv_borrow.setTextColor(getResources().getColor(R.color.black));
        tv_reader.setTextColor(getResources().getColor(R.color.black));
        tv_system.setTextColor(getResources().getColor(R.color.black));
        bt_book.setEnabled(false);
        bt_borrow.setEnabled(false);
        bt_reader.setEnabled(false);
        bt_system.setEnabled(false);

    }

    private void initFragment() {
        Fragment bookFragment = new BookFragment();
        Fragment borrowFragment = new BorrowFragment();
        Fragment readerFragment = new ReaderFragment();
        Fragment systemFragment = new SystemFragment();
        fragmentsContainer.add(bookFragment);
        fragmentsContainer.add(borrowFragment);
        fragmentsContainer.add(readerFragment);
        fragmentsContainer.add(systemFragment);
    }

    private void initView() {
        contain = findViewById(R.id.fragment_container);
        buttonGroup = findViewById(R.id.buttongroup);
        book_layout = findViewById(R.id.book_layout);
        borrow_layout = findViewById(R.id.borrow_layout);
        reader_layout = findViewById(R.id.reader_layout);
        system_layout = findViewById(R.id.system_layout);
        tv_book = findViewById(R.id.book_tv);
        tv_borrow = findViewById(R.id.borrow_tv);
        tv_reader = findViewById(R.id.reader_tv);
        tv_system = findViewById(R.id.system_tv);
        bt_book = findViewById(R.id.book);
        bt_borrow = findViewById(R.id.borrow);
        bt_reader = findViewById(R.id.reader);
        bt_system = findViewById(R.id.system);
        customTitleBar = findViewById(R.id.title_bar);
        customTitleBar.setLeftVisible(false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.book_layout:
                position = 0;
                break;
            case R.id.borrow_layout:
                position = 1;
                break;
            case R.id.reader_layout:
                position = 2;
                break;
            case R.id.system_layout:
                position = 3;
                break;
            default:
                Log.e(TAG, "getID error");
                break;
        }
        showFragment(position);
    }
}