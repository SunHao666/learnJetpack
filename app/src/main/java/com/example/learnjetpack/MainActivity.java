package com.example.learnjetpack;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.WorkManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.learnjetpack.room.RoomActivity;
import com.example.learnjetpack.workmanager.WorkManagerActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    //跳转room
    public void skipRoom(View view) {
        startActivity(new Intent(this,RoomActivity.class));
    }

    public void skipWorkManager(View view) {
        startActivity(new Intent(this, WorkManagerActivity.class));
    }
}
