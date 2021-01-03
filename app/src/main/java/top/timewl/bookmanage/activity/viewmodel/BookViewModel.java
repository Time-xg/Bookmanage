package top.timewl.bookmanage.activity.viewmodel;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.BindingAdapter;

import top.timewl.bookmanage.entity.BookInfo;
import top.timewl.bookmanage.view.SmartImageView;

public class BookViewModel extends BaseObservable {

    private BookInfo mBookInfo;

    public BookViewModel() {
    }

    @Bindable
    public BookInfo getBookInfo() {
        return mBookInfo;
    }

    public void setBookInfo(BookInfo mBookInfo) {
        this.mBookInfo = mBookInfo;
    }

    @BindingAdapter("app:cover_url")
    public static void setImage(SmartImageView smartImageView,String url){
        smartImageView.setImageUrl(url);
    }
}
