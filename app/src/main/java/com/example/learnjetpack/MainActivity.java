package com.example.learnjetpack;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.learnjetpack.room.RoomActivity;

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
}
