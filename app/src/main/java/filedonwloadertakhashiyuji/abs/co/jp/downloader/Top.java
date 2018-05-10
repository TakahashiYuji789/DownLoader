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
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Map;

import android.widget.Toast;

public class Top extends AppCompatActivity {

    private static final String REQUEST_CODE = "http://xn--eckyfna8731bop8c.jp/wp-content/uploads/2014/12/wpid-607566c4.png";

    // asset の画像ファイル名
    private String fileName = "main.png";

    private String filePath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top);
        // 画像を置く外部ストレージのパスを設定
        filePath = Environment.getExternalStorageDirectory().getPath()
                + "/DCIM/Camera/"+fileName;


    }
    public void callback(final int responseCode, final int requestCode,
                         final Map<String, Object> resultMap) {

        if (REQUEST_CODE.equals(requestCode)) {
           // dialog.dismess();
        }
    }
    public void onClick(View v) throws FileNotFoundException {
        EditText text =findViewById(R.id.editText3);
        ImageView image=findViewById(R.id.DownloadImage);
        Editable textWord = text.getText();
        if(textWord.length()==75){
            Toast.makeText(this, "成功しました", Toast.LENGTH_LONG).show();
            image.setImageResource(R.drawable.main);
            // 外部ストレージのパスに画像を保存
            ContentValues contentValues = new ContentValues();
            ContentResolver contentResolver = Top.this.getContentResolver();
            contentValues.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
            contentValues.put("_data", fileName);
            contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    contentValues);}
        else
            Toast.makeText(this,"失敗しました", Toast.LENGTH_LONG).show();
    }
    }
