package com.example.learnjetpack.workmanager;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.work.BackoffPolicy;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.Operation;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;
import androidx.work.Worker;

import com.example.learnjetpack.R;
import com.example.learnjetpack.utils.Constants;
import com.example.learnjetpack.utils.LogUtil;
import com.example.learnjetpack.workmanager.test.CompressPicWorker;
import com.example.learnjetpack.workmanager.test.FilterPicWorker;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.List;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

/**
 * workManager
 */
public class WorkManagerActivity extends AppCompatActivity {

    private OneTimeWorkRequest requestTag;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workmanager);
//        initWorkManager();
//        initConstraints();
//        initDelay();
//        initRetry();
//        initData();

    }
    //标记工作
    private void initTag() {
        Constraints constraints = new Constraints.Builder()
                .setRequiresCharging(true)//仅充电状态下
//                .setRequiresDeviceIdle(true)//手机空闲状态下
                .build();

        //配置任务工作方式和运行时间
        requestTag = new OneTimeWorkRequest.Builder(UploadWorker.class).
                addTag(Constants.CLEAN_UPLOAD).setConstraints(constraints).build();
        //将任务提交给系统
        WorkManager.getInstance(this).enqueue(requestTag);
        status();
    }

    //数据传输
    private void initData() {
        Data data = new Data.Builder()
                .putString(Constants.WORK_DATA_IMG_URI,"hahahaha")
                .build();
        OneTimeWorkRequest oneTimeWorkRequest = new OneTimeWorkRequest.Builder(UploadWorker.class)
                .setInputData(data)
                .build();
        Operation enqueue = WorkManager.getInstance(this).enqueue(oneTimeWorkRequest);
    }

    //重试
    private void initRetry() {
        //配置任务工作方式和运行时间
        OneTimeWorkRequest request = new OneTimeWorkRequest.Builder(UploadWorker.class)
                //重试执行的时间规则
                .setBackoffCriteria(BackoffPolicy.LINEAR,2,TimeUnit.SECONDS).build();
        //将任务提交给系统
        WorkManager.getInstance(this).enqueue(request);
    }

    //初始延迟
    private void initDelay() {

        //配置任务工作方式和运行时间
        OneTimeWorkRequest request = new OneTimeWorkRequest.Builder(UploadWorker.class)
                .setInitialDelay(5, TimeUnit.SECONDS).build();
        //将任务提交给系统
        WorkManager.getInstance(this).enqueue(request);
    }

    //添加工作约束
    private void initConstraints() {

        Constraints constraints = new Constraints.Builder()
                .setRequiresCharging(true)//仅充电状态下
                .setRequiresDeviceIdle(true)//手机空闲状态下
                .build();

        OneTimeWorkRequest request = new OneTimeWorkRequest.Builder(UploadWorker.class)
                                        .setConstraints(constraints)
                                        .build();

        WorkManager.getInstance(this).enqueue(request);

    }
    //普通
    private void initWorkManager() {
        //配置任务工作方式和运行时间
        OneTimeWorkRequest request = new OneTimeWorkRequest.Builder(UploadWorker.class).build();
        //将任务提交给系统
        WorkManager.getInstance(this).enqueue(request);
    }
    //绑定Tag
    public void tagBind(View view) {
        initTag();
    }

    public void unBind(View view) {
        WorkManager.getInstance(this).cancelAllWorkByTag(Constants.CLEAN_UPLOAD);
    }
    //获取任务状态
    public void status(View view) {

        if(requestTag == null)
            return;
        WorkManager.getInstance(this).getWorkInfoByIdLiveData(requestTag.getId())
                .observe(this, new Observer<WorkInfo>() {
                    @Override
                    public void onChanged(@Nullable WorkInfo workInfo) {
                        if(workInfo != null){
                            WorkInfo.State state = workInfo.getState();
                            LogUtil.getInstance().e("任务状态 = "+state);
                        }
                    }
                });
    }
    public void status() {

        if(requestTag == null)
            return;
        WorkManager.getInstance(this).getWorkInfoByIdLiveData(requestTag.getId())
                .observe(this, new Observer<WorkInfo>() {
                    @Override
                    public void onChanged(@Nullable WorkInfo workInfo) {
                        if(workInfo != null){
                            WorkInfo.State state = workInfo.getState();
                            LogUtil.getInstance().e("任务状态 = "+state);
                        }
                    }
                });
    }
    //观察进度
    public void progress(View view) {
        //配置任务工作方式和运行时间
        OneTimeWorkRequest request = new OneTimeWorkRequest.Builder(ProgressWorker.class).build();
        //将任务提交给系统
        WorkManager.getInstance(this).enqueue(request);

        WorkManager.getInstance(getApplicationContext())
                // requestId is the WorkRequest id
                .getWorkInfoByIdLiveData(request.getId())
                .observe(this, new Observer<WorkInfo>() {
                    @Override
                    public void onChanged(@Nullable WorkInfo workInfo) {
                        if (workInfo != null) {
                            Data progress = workInfo.getProgress();
                            int value = progress.getInt(ProgressWorker.PROGRESS, 0);
                            // Do something with progress
                            LogUtil.getInstance().e("status="+workInfo.getState()+"  progress=="+value);
                        }
                    }
                });


    }

    /**
     * 多个request 执行顺序
     * @param view
     */
    public void moreLink(View view) {
        //配置任务工作方式和运行时间
        OneTimeWorkRequest request = new OneTimeWorkRequest.Builder(ProgressWorker.class).build();

        //配置任务工作方式和运行时间
        OneTimeWorkRequest uploadWorkRequest = new OneTimeWorkRequest.Builder(UploadWorker.class).build();
        WorkManager.getInstance(this).beginWith(uploadWorkRequest)
                .then(request)
                .enqueue();

        WorkManager.getInstance(getApplicationContext())
                // requestId is the WorkRequest id
                .getWorkInfoByIdLiveData(request.getId())
                .observe(this, new Observer<WorkInfo>() {
                    @Override
                    public void onChanged(@Nullable WorkInfo workInfo) {
                        if (workInfo != null) {
                            Data progress = workInfo.getProgress();
                            int value = progress.getInt(ProgressWorker.PROGRESS, 0);
                            // Do something with progress
                            LogUtil.getInstance().e("status="+workInfo.getState()+"  progress=="+value);
                        }
                    }
                });

        WorkManager.getInstance(this).getWorkInfoByIdLiveData(uploadWorkRequest.getId())
                .observe(this, new Observer<WorkInfo>() {
                    @Override
                    public void onChanged(@Nullable WorkInfo workInfo) {
                        if(workInfo != null){
                            WorkInfo.State state = workInfo.getState();
                            LogUtil.getInstance().e("任务状态 = "+state);
                        }
                    }
                });
    }
    //重复执行某个工作器
    public void again(View view) {
        PeriodicWorkRequest periodicWorkRequest = new PeriodicWorkRequest.Builder(UPLogWorker.class,5,TimeUnit.SECONDS)
                .build();
        WorkManager.getInstance(this).enqueue(periodicWorkRequest);
        WorkManager.getInstance(this).getWorkInfoByIdLiveData(periodicWorkRequest.getId()).observe(this, new Observer<WorkInfo>() {
            @Override
            public void onChanged(WorkInfo workInfo) {
                LogUtil.getInstance().e("status=="+workInfo.getState());
            }
        });
    }

    public void testPic(View view) {
        String[] args = {"sh://www.pic.com/pic1" ,"sh://www.pic.com/pic2" ,"1sh://www.pic.com/pic1" };
        Data data = new Data.Builder().putStringArray("pic_uri",args).build();

        OneTimeWorkRequest request = new OneTimeWorkRequest.Builder(FilterPicWorker.class).
                setInputData(data).build();

        OneTimeWorkRequest comPressRequest = new OneTimeWorkRequest.Builder(CompressPicWorker.class)
                .build();

        WorkManager.getInstance(this).beginWith(request)
                .then(comPressRequest)
                .enqueue();

        WorkManager.getInstance(this).getWorkInfoByIdLiveData(request.getId())
                .observe(this, new Observer<WorkInfo>() {
                    @Override
                    public void onChanged(WorkInfo workInfo) {
                        if(workInfo != null){
                            LogUtil.getInstance().e("status=="+workInfo.getState());
                        }
                    }
                });

    }
}
