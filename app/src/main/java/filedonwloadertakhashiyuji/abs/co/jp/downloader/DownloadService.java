package filedonwloadertakhashiyuji.abs.co.jp.downloader;

import android.os.AsyncTask;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


import android.app.IntentService;
import android.content.*;

import android.os.Bundle;
import android.util.Log;

public class DownloadService extends AsyncTask<URL, Void, Boolean> {

    @Override
    protected Boolean doInBackground(URL[] urls) {
        HttpURLConnection con = null;
        try {
            // アクセス先URL
            String[] values = new String[0];
            final URL url = new URL(values[0]);
            // 出力ファイルフルパス
            final String filePath = "内部ストレージ/Takahashi_Folder";

            // ローカル処理
            // コネクション取得
            con = (HttpURLConnection) url.openConnection();
            con.connect();

            // HTTPレスポンスコード
            final int status = con.getResponseCode();
            if (status == HttpURLConnection.HTTP_OK) {
                // 通信に成功した
                // ファイルのダウンロード処理を実行
                // 読み込み用ストリーム
                final InputStream input = con.getInputStream();
                final DataInputStream dataInput = new DataInputStream(input);
                // 書き込み用ストリーム
                final FileOutputStream fileOutput = new FileOutputStream(filePath);
                final DataOutputStream dataOut = new DataOutputStream(fileOutput);
                // 読み込みデータ単位
                final byte[] buffer = new byte[4096];
                // 読み込んだデータを一時的に格納しておく変数
                int readByte = 0;

                // ファイルを読み込む
                while((readByte = dataInput.read(buffer)) != -1) {
                    dataOut.write(buffer, 0, readByte);
                }
                // 各ストリームを閉じる
                dataInput.close();
                fileOutput.close();
                dataInput.close();
                input.close();
                // 処理成功
                return true;
            }

        } catch (IOException e1) {
            e1.printStackTrace();
        } finally {
            if (con != null) {
                // コネクションを切断
                con.disconnect();
            }
        }
        return false;
    }
}