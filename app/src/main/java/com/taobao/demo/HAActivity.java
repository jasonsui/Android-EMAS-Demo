package com.taobao.demo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.ha.adapter.AliHaAdapter;
import com.alibaba.motu.tbrest.SendService;
import com.emas.demo.R;

import com.taobao.tao.log.upload.LogFileUploadManager;

import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

/**
 * Created by qiulibin on 2017/12/26.
 *
 * ha demo activity
 *
 */

public class HAActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hademo);


        Button javaCrashBtn = (Button) findViewById(R.id.javaCrash);
        javaCrashBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                throw new NullPointerException();
            }
        });

        Button nativeCrashBtn = (Button) findViewById(R.id.nativeCrash);
        nativeCrashBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(HAActivity.this, "无，请手动发送signall 11", Toast.LENGTH_SHORT).show();
            }
        });

        Button remoteDebugBtn = (Button) findViewById(R.id.debug);
        remoteDebugBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String TAG = "HaDemo";
                String MODEL = "HAActivity";

                for (int i = 0; i < 500; ++i){
                    String uuid = getRandomNum();
                    AliHaAdapter.getInstance().tLogService.loge(MODEL,TAG,i+"" + uuid);
                }

                for (int i = 0; i < 100; ++i){
                    String uuid = getRandomNum();
                    AliHaAdapter.getInstance().tLogService.loge(MODEL,TAG,"fenchao" + i+"hahh" + uuid);
                }

                //上传
                startLog(getApplication().getApplicationContext());
            }
        });


        Button telescopeBtn = (Button) findViewById(R.id.telescope);
        telescopeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String contain = "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";

                for (int i = 0; i < 10; ++i){
                    SendService.getInstance().sendRequest(null,
                            System.currentTimeMillis(),
                            "ALIHA",
                            61004,
                            "telescope",
                            contain,
                            null,
                            null);
                }
            }
        });
    }

    //上传
    public void startLog(final Context context){
        TimerTask task= new TimerTask() {
            @Override
            public void run() {
                LogFileUploadManager logFileUploadManager = new LogFileUploadManager(context);
                logFileUploadManager.uploadWithFilePath(null,"feedback","tlog",null);
            }
        };

        Timer timer = new Timer();
        //time为Date类型：在指定时间执行一次。
        timer.schedule(task, 3 * 1000);
    }

    //随机数
    public String getRandomNum(){
        try {
            UUID uuid = UUID.randomUUID();
            return uuid.toString().replace("-", "");
        }catch (Exception e){
            Log.w(AliHaAdapter.TAG,"get random num failure",e);
        }
        return null;
    }
}
