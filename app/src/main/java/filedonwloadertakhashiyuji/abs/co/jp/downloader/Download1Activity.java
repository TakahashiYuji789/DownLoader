package filedonwloadertakhashiyuji.abs.co.jp.downloader;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class Download1Activity extends Activity
        implements OnClickListener {

    static String TAG = "Download1";

    EditText urlEditText;
    Button startButton;
    TextView progressTextView;
    ImageView imageView;

    DownloadProgressBroadcastReceiver progressReceiver;
    IntentFilter intentFilter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        findViews();
        setListeners();
        registerDownloadBroadcastReceiver();
    }

    protected void findViews(){
        urlEditText = (EditText)findViewById(R.id.urlEditText);
        startButton = (Button)findViewById(R.id.startButton);
        //progressTextView = (TextView)findViewById(R.id.progressTextView);
        imageView = (ImageView)findViewById(R.id.ImageViewer);
    }

    protected void setListeners(){
        startButton.setOnClickListener(this);
    }

    protected void registerDownloadBroadcastReceiver(){
        progressReceiver = new DownloadProgressBroadcastReceiver();
        intentFilter = new IntentFilter();
        intentFilter.addAction("DOWNLOAD_PROGRESS_ACTION");
        registerReceiver(progressReceiver, intentFilter);
    }

    protected void startDownload(){
        Intent intent = new Intent(getBaseContext(), DownloadService.class);
        intent.putExtra("url", urlEditText.getText().toString());
        startService(intent);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.startButton:
                startDownload();
                break;
        }
    }

    class DownloadProgressBroadcastReceiver
            extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            // Show Progress
            Bundle bundle = intent.getExtras();
            int completePercent = bundle.getInt("completePercent");
            int totalByte = bundle.getInt("totalByte");
            String progressString = totalByte + " byte read.";
            if(0 < completePercent){
                progressString += "[" + completePercent + "%]";
            }
            progressTextView.setText(progressString);

            // If completed, show the picture.
            if(completePercent == 100){
                String fileName = bundle.getString("filename");
                Bitmap bitmap = BitmapFactory.decodeFile(
                        "/data/data/com.keicode.android.test/files/"
                                + fileName);
                if(bitmap != null){
                    imageView.setImageBitmap(bitmap);
                }
            }
        }
    }
}