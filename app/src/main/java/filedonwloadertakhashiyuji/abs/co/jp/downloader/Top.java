package filedonwloadertakhashiyuji.abs.co.jp.downloader;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Map;

import android.widget.Toast;

public class Top  extends AppCompatActivity {

    private ImageView imageView;
    private EditText etitText;
    private DownloadTask task;
    boolean Succes = false;
    Bitmap bitmap_;
    String param0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        task = new DownloadTask();
        setContentView(R.layout.activity_top);

        etitText = findViewById(R.id.DownloadText);
        imageView = findViewById(R.id.DownloadImage);
        bitmap_ = task.bmp;
        Button downloadButton = findViewById(R.id.DownLoadButton);
    }
    public void onClick(View v) {
        param0 = etitText.getText().toString();
        if(param0.length() != 0){
            // ボタンをタップして非同期処理を開始
            task = new DownloadTask();
             // Listenerを設定
            task.setListener(createListener());
            task.execute(param0);
        }

        if(task.bmp==null&&param0.length()>=30) {
            Toast.makeText(this, "成功しました", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this,"失敗しました",Toast.LENGTH_LONG).show();
        imageView.setImageResource(R.drawable.fake);
    }
    }


    @Override
    protected void onDestroy() {
        task.setListener(null);
        super.onDestroy();
    }

    private DownloadTask.Listener createListener() {
        return new DownloadTask.Listener() {
            @Override
            public void onSuccess(Bitmap bmp) {
                imageView.setImageBitmap(bmp);
            }
        };
    }
}