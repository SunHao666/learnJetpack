package com.example.learnjetpack;

import android.content.Context;
import android.util.Log;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.work.Configuration;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;
import androidx.work.testing.SynchronousExecutor;
import androidx.work.testing.WorkManagerTestInitHelper;

import com.example.learnjetpack.workmanager.test.TestWorker;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        assertEquals("com.example.learnjetpack", appContext.getPackageName());
    }

    @Before
    public void setup() {
        Context context = ApplicationProvider.getApplicationContext();
        Configuration config = new Configuration.Builder()
                // Set log level to Log.DEBUG to
                // make it easier to see why tests failed
                .setMinimumLoggingLevel(Log.DEBUG)
                // Use a SynchronousExecutor to make it easier to write tests
                .setExecutor(new SynchronousExecutor())
                .build();

        // Initialize WorkManager for instrumentation tests.
        WorkManagerTestInitHelper.initializeTestWorkManager(context, config);
    }

    @Test
    public void testWorker() throws Exception {
        Data input = new Data.Builder().put("a", 1).put("b", 2).build();

        OneTimeWorkRequest request = new OneTimeWorkRequest.Builder(TestWorker.class).setInputData(input).build();

        WorkManager mgr = WorkManager.getInstance(ApplicationProvider.getApplicationContext());
        mgr.enqueue(request).getResult().get();
        //该接口其实得到的是一个StatusRunnable，从数据库中查询到WorkInfo后会调用SettableFuture.set()，然后get()会返回对应的WorkInfo
        WorkInfo workInfo = mgr.getWorkInfoById(request.getId()).get();
        assertThat(workInfo.getState(), is(WorkInfo.State.ENQUEUED));
        workInfo = mgr.getWorkInfoById(request.getId()).get();
        assertThat(workInfo.getState(), is(WorkInfo.State.RUNNING));
        workInfo = mgr.getWorkInfoById(request.getId()).get();
        assertThat(workInfo.getState(), is(WorkInfo.State.SUCCEEDED));
        Data output = workInfo.getOutputData();
        assertThat(output.getInt("a", -1), is(1));
    }

}
