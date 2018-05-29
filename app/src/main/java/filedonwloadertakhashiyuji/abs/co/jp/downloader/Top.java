package filedonwloadertakhashiyuji.abs.co.jp.downloader;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.widget.Toolbar;

public class Top  extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    private final int REQUEST_PERMISSION = 1000;
    private TextView textView;


    private String filePath;
    // String 型の ArrayList を生成
    private List<String> filesList = new ArrayList<String>();
    private File[] files;
    private ImageView imageView;
    private EditText etitText;
    private EditText fileText;
    private DownloadTask task;
    boolean Succes = false;
    boolean ReadMode = false;
    int j = 0;
    String item;
    Bitmap bitmap_;
    String param0;
    String name_;
    String sdPath;
    ToggleButton toggle;
    Spinner spinner;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        task = new DownloadTask();
        setContentView(R.layout.main);

        etitText = findViewById(R.id.urlEditText);
        fileText = findViewById(R.id.urlEditText2);
        name_ = fileText.getText().toString();
        imageView = findViewById(R.id.ImageViewer);
        bitmap_ = task.bmp;
        spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setVisibility(View.INVISIBLE);
        Button downloadButton = findViewById(R.id.ViewImageButton);
        sdPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
        // ToggleButtonの取得
        toggle = (ToggleButton) findViewById(R.id.toggleButton);
        toggle.setOnCheckedChangeListener(this);
        toggle.setTextOff("外部読み込み");
        toggle.setTextOn("内部読み込み");
        //==== スピナー取得 ====//

        //==== アダプタ作成 ====//
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //==== アイテム追加 ====//

        //==== スピナーにアダプタ設定 ====//
        spinner.setAdapter(adapter);
        //==== リスナー設定 ====//
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            // アイテム選択時に呼び出される。
            @Override
            public void onItemSelected(AdapterView<?> v, View view,
                                       int position, long id) {
                String item = (String) spinner.getSelectedItem();
            }

            // 何も選択されなかったときに呼び出される。
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
        checkFiles();


    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            spinner.setVisibility(View.VISIBLE);
            etitText.setVisibility(View.INVISIBLE);
            ReadMode = true;
            adapter.clear();
            for (int i = 0; i < files.length; i++) {
                if (files[i].isFile() && files[i].getName().endsWith(".jpg")) {
                    adapter.add(files[i].getName());
                }
            }

        }else {
            spinner.setVisibility(View.INVISIBLE);
            etitText.setVisibility(View.VISIBLE);
            ReadMode = false;
        }
    }


    public void onClick(View v) {

        if (v.getId() == R.id.ViewImageButton) Succes = false;
        if (v.getId() == R.id.startButton) {
            Succes = true;
        }
        if (v.getId() == R.id.ViewImageButton && ReadMode) {
            File file = new File(sdPath + item);
            Bitmap bm = BitmapFactory.decodeFile(file.getPath());
            ((ImageView) findViewById(R.id.ImageViewer)).setImageBitmap(bm);
            Toast.makeText(this, "成功しました", Toast.LENGTH_LONG).show();
        }
        else if(ReadMode) {
            Toast.makeText(this, "失敗しました", Toast.LENGTH_LONG).show();
        return;
    }

        if (v.getId() == R.id.startButton && ReadMode) {
            File file = new File(sdPath + item);
            Bitmap bm = BitmapFactory.decodeFile(file.getPath());
            ((ImageView) findViewById(R.id.ImageViewer)).setImageBitmap(bm);
            saveToFile(bm);
            Toast.makeText(this, "成功しました", Toast.LENGTH_LONG).show();
        }
        else if(ReadMode) {
            Toast.makeText(this, "失敗しました", Toast.LENGTH_LONG).show();
            return;
        }
    param0 =etitText.getText().

    toString();
        if(param0.length()!=0)

    {
        // ボタンをタップして非同期処理を開始
        task = new DownloadTask();
        // Listenerを設定
        task.setListener(createListener());
        task.execute(param0);
    }

        if(task.bmp==null&&param0.length()>=30)

    {
        Toast.makeText(this, "成功しました", Toast.LENGTH_LONG).show();
    }else

    {
        Toast.makeText(this, "失敗しました", Toast.LENGTH_LONG).show();
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
                if(Succes&&!ReadMode)
                saveToFile(bmp);
            }
        };
    }
    private void setUpWriteExternalStorage() {

            File file = new File(sdPath + item);
                Bitmap bm = BitmapFactory.decodeFile(file.getPath());
                ((ImageView) findViewById(R.id.ImageViewer)).setImageBitmap(bm);

        }


            public void saveToFile(Bitmap bit) {
                try {
                    File filePath = null;
                    if(bit==null)return;
                    name_ = fileText.getText().toString();

                    if (filesList.contains(name_ + ".jpg")) {
                        j += 1;
                        filePath = new File(
                                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), name_ + j + ".jpg");
                    } else filePath = new File(
                            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), name_ + ".jpg");

                    checkFiles();
                    String path = filePath.getAbsolutePath();
                    FileOutputStream output = null;
                    output = new FileOutputStream(path);
                    bit.compress(Bitmap.CompressFormat.PNG, 100, output);
                    output.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

//    // アンドロイドのデータベースへ登録する
//    private void registerDatabase(String file) {
//        ContentValues contentValues = new ContentValues();
//        ContentResolver contentResolver = Top.this.getContentResolver();
//        contentValues.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
//        contentValues.put("_data", file);
//        contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
//                contentValues);
//    }

            private void checkFiles() {
                files = new File(sdPath).listFiles();
                if (files != null) {
                    for (int i = 0; i < files.length; i++) {
                        if (files[i].isFile() && files[i].getName().endsWith(".jpg")) {
                            filesList.add(files[i].getName());
                            }
                        }
                    }
                }
            }

