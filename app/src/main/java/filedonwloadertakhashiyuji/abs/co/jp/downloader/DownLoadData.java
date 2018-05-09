package filedonwloadertakhashiyuji.abs.co.jp.downloader;

import java.util.Map;

public interface DownLoadData {
    //成功した時のレスポン
    public static final int SUCCESS  =0;
    //失敗した時のレスポン
    public static final int FAILED   =-1;
    int OK = 0;

    //コールバックメッソド
    public void callBack(final int responseCode, final int requestCode,final Map<String, Object> resultMap);  }
