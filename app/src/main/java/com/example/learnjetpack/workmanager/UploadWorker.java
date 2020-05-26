package com.example.learnjetpack.workmanager;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.learnjetpack.utils.Constants;
import com.example.learnjetpack.utils.LogUtil;

/**
 * 创建后台工作机器
 */
public class UploadWorker extends Worker {

    public UploadWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    /**
     * 后台需要执行的任务
     * @return
     */
    @NonNull
    @Override
    public Result doWork() {
        LogUtil.getInstance().e("后台任务开始执行");
        //模拟后台任务
        new Thread(new Runnable() {
            @Override
            public void run() {
                String uri = getInputData().getString(Constants.WORK_DATA_IMG_URI);
                LogUtil.getInstance().e("data uri = "+uri);
                //模拟网络请求
                try {
                    Thread.sleep(2000);
                    LogUtil.getInstance().e("后台任务执行完成");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        Data data = new Data.Builder().putString(Constants.WORK_DATA_IMG_URI,"1111").build();
        //success 执行完成
        return Result.success(data);
        //重试
//        return Result.retry();
    }
}
