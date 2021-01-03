package top.timewl.bookmanage.fragment.viewmodel;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import top.timewl.bookmanage.entity.Borrow;

public class BorrowInfoViewModel extends BaseObservable {

    private Borrow mBorrow;


    @Bindable
    public Borrow getBorrow() {
        return mBorrow;
    }

    public void setBorrow(Borrow mBorrow) {
        this.mBorrow = mBorrow;
    }
}
