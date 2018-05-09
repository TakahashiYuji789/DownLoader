package filedonwloadertakhashiyuji.abs.co.jp.downloader;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.Map;
import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

public class Top extends AppCompatActivity {

    private static final String REQUEST_CODE = "http://xn--eckyfna8731bop8c.jp/wp-content/uploads/2014/12/wpid-607566c4.png";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top);


    }
    public void callback(final int responseCode, final int requestCode,
                         final Map<String, Object> resultMap) {

        if (REQUEST_CODE.equals(requestCode)) {
           // dialog.dismess();
        }
    }
    public void onClick(View v) {
        EditText text =findViewById(R.id.editText3);
        ImageView image=findViewById(R.id.imageView);
        if(text.toString()==REQUEST_CODE){
        }
        Toast.makeText(this, "Toast example", Toast.LENGTH_LONG).show();

    }
    }
