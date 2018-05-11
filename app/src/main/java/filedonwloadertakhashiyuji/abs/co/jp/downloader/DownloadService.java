package filedonwloadertakhashiyuji.abs.co.jp.downloader;

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;


import android.app.IntentService;
import android.content.*;

import android.os.Bundle;
import android.util.Log;

public class DownloadService extends IntentService {

    static final String TAG = "Download1";

    public DownloadService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        try {

            Bundle bundle = intent.getExtras();
            if(bundle == null){
                Log.d(TAG, "bundle == null");
                return;
            }
            String urlString = bundle.getString("url");

            // HTTP Connection

            URL url = new URL(urlString);
            String fileName = getFilenameFromURL(url);
            Log.d(TAG, fileName);
            URLConnection conn = url.openConnection();

            HttpURLConnection httpConn = (HttpURLConnection)conn;
            httpConn.setAllowUserInteraction(false);
            httpConn.setInstanceFollowRedirects(true);
            httpConn.setRequestMethod("GET");
            httpConn.connect();
            int response = httpConn.getResponseCode();

            // Check Response
            if(response != HttpURLConnection.HTTP_OK){
                throw new HttpException();
            }
            int contentLength = httpConn.getContentLength();

            InputStream in = httpConn.getInputStream();

            FileOutputStream outStream
                    = openFileOutput(fileName, MODE_PRIVATE);

            DataInputStream dataInStream = new DataInputStream(in);
            DataOutputStream dataOutStream
                    = new DataOutputStream(
                    new BufferedOutputStream(outStream));

            // Read Data
            byte[] b= new byte[4096];
            int readByte = 0, totalByte = 0;

            while(-1 != (readByte = dataInStream.read(b))){
                dataOutStream.write(b, 0, readByte);
                totalByte += readByte;
                sendProgressBroadcast(
                        contentLength,
                        totalByte,
                        fileName);
            }

            dataInStream.close();
            dataOutStream.close();

            if(contentLength < 0){
                sendProgressBroadcast(
                        totalByte,
                        totalByte,
                        fileName);
            }

        } catch (IOException e) {
            Log.d(TAG, "IOException");
        } catch (HttpException e) {
            Log.d(TAG, "HttpException");
        }
    }

    protected void sendProgressBroadcast(
            int contentLength,
            int totalByte,
            String filename){
        Intent broadcastIntent = new Intent();
        int completePercent = contentLength < 0 ?
                -1 : ((totalByte*1000)/(contentLength*10));
        Log.d(TAG, "completePercent = " + completePercent);
        Log.d(TAG, "totalByte = " + totalByte);
        Log.d(TAG, "fileName = " + filename);

        broadcastIntent.putExtra("completePercent", completePercent);
        broadcastIntent.putExtra("totalByte", totalByte);
        broadcastIntent.putExtra("filename", filename);
        broadcastIntent.setAction("DOWNLOAD_PROGRESS_ACTION");
        getBaseContext().sendBroadcast(broadcastIntent);
    }

    protected String getFilenameFromURL(URL url){
        String[] p = url.getFile().split("/");
        String s = p[p.length-1];
        if(s.indexOf("?") > -1){
            return s.substring(0, s.indexOf("?"));
        }
        return s;
    }

    private class HttpException extends Throwable {
    }
}