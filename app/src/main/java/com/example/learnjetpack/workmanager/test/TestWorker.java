package com.example.learnjetpack.workmanager.test;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class TestWorker extends Worker {
    public TestWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @SuppressLint("RestrictedApi")
    @NonNull
    @Override
    public Result doWork() {
        Data input = getInputData();
        if (0 == input.size()) {
            return Result.failure();
        } else {
            return Result.success(input);
        }
    }
}
