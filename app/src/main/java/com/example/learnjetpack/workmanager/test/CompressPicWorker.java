package com.example.learnjetpack.workmanager.test;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.learnjetpack.utils.LogUtil;

/**
 * 图片过滤器
 */
public class CompressPicWorker extends Worker {
    public CompressPicWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        String[] uriFilters = getInputData().getStringArray("pic_uri_filter");
        LogUtil.getInstance().e("需要压缩的uri的数量=="+uriFilters.length);
        return null;
    }
}
