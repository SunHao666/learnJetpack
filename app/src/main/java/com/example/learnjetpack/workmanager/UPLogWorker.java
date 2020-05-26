package com.example.learnjetpack.workmanager;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.learnjetpack.utils.LogUtil;

public class UPLogWorker extends Worker {
    private int nums;
    public UPLogWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
        nums++;
        LogUtil.getInstance().e("上传日志次数=="+nums);
        return Result.success();
    }
}
