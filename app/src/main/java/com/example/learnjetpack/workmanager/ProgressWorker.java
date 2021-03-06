package com.example.learnjetpack.workmanager;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class ProgressWorker extends Worker {

    public static final String PROGRESS = "PROGRESS";
    private static final long DELAY = 5000L;

    public ProgressWorker(
            @NonNull Context context,
            @NonNull WorkerParameters parameters) {
        super(context, parameters);
        // Set initial progress to 0
        setProgressAsync(new Data.Builder().putInt(PROGRESS, 0).build());
    }

    @NonNull
    @Override
    public Result doWork() {
        try {
            // Doing work.
            Thread.sleep(DELAY);
        } catch (InterruptedException exception) {
            // ... handle exception
        }
        // Set progress to 100 after you are done doing your work.
        setProgressAsync(new Data.Builder().putInt(PROGRESS, 100).build());
        return Result.success();
    }
}