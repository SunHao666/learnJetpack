package com.example.learnjetpack.workmanager.test;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.learnjetpack.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 图片过滤
 */
public class FilterPicWorker extends Worker {

    public FilterPicWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        LogUtil.getInstance().e("开始过滤图片==");
        List<String> list = new ArrayList<>();
        String[] pic_uris = getInputData().getStringArray("pic_uri");
        for (int i = 0; i < pic_uris.length; i++) {
            if(pic_uris[i].startsWith("sh")){
                LogUtil.getInstance().e("过滤到图片=="+pic_uris[i]);
                list.add(pic_uris[i]);
            }
        }
        String[] filters = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            filters[i] = list.get(i);
        }
        Data data = new Data.Builder().putStringArray("pic_uri_filter",filters).build();
        LogUtil.getInstance().e("开始过滤结束==过滤图片的数量是"+filters.length);
        return Result.success(data);
    }
}
