package filedonwloadertakhashiyuji.abs.co.jp.downloader;

import android.os.AsyncTask;

import java.util.HashMap;
import java.util.Map;

public class DownLoadContainer  extends AsyncTask<Void, Void, Void> {
    //ダウンロードデータ
    private DownLoadData data;
    //リクエストコード
    private String requestCode;
    //コールバックの引数に渡す結果データ
    private Map<String, Object> resultMap;

    //コンストラクタ
    public DownLoadContainer(final DownLoadData downLoadData, final String requestCode){
        this.data = downLoadData;
        this.requestCode = requestCode;
        resultMap = new HashMap<String,Object>();
    }

    /**
     * executeが実行された後に実行される。
     */
    @Override
    protected Void doInBackground(Void... params) {
        // DB登録等のUIに関与しない処理
        // resuletMapにActivityに渡した値を詰めたり
        return null;
    }

    /**
     * コールバックメソッドを実行する。
     */
    protected void onPostExecute(Void... params) {
        data.callBack(DownLoadData.OK, 0, resultMap);
    }
}
