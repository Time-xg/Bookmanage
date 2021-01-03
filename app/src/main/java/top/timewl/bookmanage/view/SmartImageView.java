package top.timewl.bookmanage.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;


import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class SmartImageView extends androidx.appcompat.widget.AppCompatImageView {
    private Handler handler= new Handler(){
        @Override
        public void handleMessage(Message msg) {
            Bitmap bitmap = (Bitmap) msg.obj;
            SmartImageView.this.setImageBitmap(bitmap);
        }
    };
    public SmartImageView(Context context) {
        super(context);
    }

    public SmartImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SmartImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    public void setImageUrl(final String path){
        new Thread(){
            @Override
            public void run() {

                try {
                    URL url = new URL(path);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setConnectTimeout(5000);
                    int responseCode = connection.getResponseCode();
                    connection.setRequestMethod("GET");
                    if (responseCode == 200){
                        InputStream inputStream = connection.getInputStream();
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        Message message = new Message();
                        message.obj = bitmap;
                        handler.sendMessage(message);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }
}
