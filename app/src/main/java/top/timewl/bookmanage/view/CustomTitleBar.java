package top.timewl.bookmanage.view;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import top.timewl.bookmanage.R;

public class CustomTitleBar extends LinearLayout {

    private ImageView iv_back;
    private TextView tv_title;
    private ImageView iv_more;

    public CustomTitleBar(Context context, AttributeSet attrs) {
        super(context,attrs);
        initView(context,attrs);

    }

    private void initView(Context context, AttributeSet attrs) {
        View inflate = LayoutInflater.from(getContext()).inflate(R.layout.titlebar_layout,this);
        iv_back = inflate.findViewById(R.id.back_iv);
        tv_title =  inflate.findViewById(R.id.title_tv);
        iv_more = inflate.findViewById(R.id.more_iv);
        init(context,attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomTitleBar);
        String title = typedArray.getString(R.styleable.CustomTitleBar_title);
        int rightIcon = typedArray.getInt(R.styleable.CustomTitleBar_right_icon, R.drawable.ic_more);
        int leftIcon = typedArray.getInt(R.styleable.CustomTitleBar_left_icon, R.drawable.ic_back);
        boolean rightVisible = typedArray.getBoolean(R.styleable.CustomTitleBar_right_visible,true);
        boolean leftVisible = typedArray.getBoolean(R.styleable.CustomTitleBar_left_visible,true);
        tv_title.setText(title);
        iv_more.setImageResource(rightIcon);
        iv_back.setImageResource(leftIcon);
        iv_more.setVisibility(rightVisible?VISIBLE:INVISIBLE);
        iv_back.setVisibility(leftVisible?VISIBLE:INVISIBLE);

    }
    public void setRightOnclickListener(OnClickListener listener){
        iv_more.setOnClickListener(listener);
    }
    public void setLeftOnclickListener(OnClickListener listener){
        iv_back.setOnClickListener(listener);
    }
    public void setRightVisible(boolean visible){
        if (visible){
            iv_more.setVisibility(View.VISIBLE);
        }
        else{
            iv_more.setVisibility(View.INVISIBLE);
        }
    }
    public void setLeftVisible(boolean visible){
        if (visible){
            iv_back.setVisibility(View.VISIBLE);
        }
        else{
            iv_back.setVisibility(View.INVISIBLE);
        }
    }
    public void setTitle(String s){
        tv_title.setText(s);
    }

}
