package filedonwloadertakhashiyuji.abs.co.jp.downloader;

import android.os.AsyncTask;

import java.util.HashMap;
import java.util.Map;

public class DownLoadContainer   {
    //ダウンロードデータ
    private DownLoadData data;
    //リクエストコード
    private String requestCode;
    //コールバックの引数に渡す結果データ
    private Map<String, Object> resultMap;

    public  boolean Status;
    //コンストラクタ
    public DownLoadContainer(final DownLoadData downLoadData, final String requestCode){
        this.data = downLoadData;
        this.requestCode = requestCode;
        resultMap = new HashMap<String,Object>();
    }

}
