package com.example.learnjetpack.room;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.example.learnjetpack.R;

import java.util.ArrayList;
import java.util.List;

public class RoomActivity extends AppCompatActivity {

    private PlayerRepository repository;
    private List<PlayerModel> data = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);
        PlayerDao playerDao = NBADatabase.getInstance(this).getPlayerDao();
        repository = new PlayerRepository(playerDao);
    }

    public void getAllPlayers(View view) {
        LiveData<List<PlayerModel>> allPlayers = repository.findAllPlayers();
        allPlayers.observe(this, new Observer<List<PlayerModel>>() {
            @Override
            public void onChanged(List<PlayerModel> playerModels) {
                for (int i = 0; i < playerModels.size(); i++) {
                    Log.e("tag",playerModels.get(i).toString()+"\n");
                }
                data.addAll(playerModels);
            }
        });

    }

    public void addPlayer(View view) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                PlayerModel playerModel = new PlayerModel();
                playerModel.code = 1;
                playerModel.country = "china";
                repository.insert(playerModel);
            }
        }).start();
    }

    public void delAllPlayers(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                repository.delAllPlayers(data);
            }
        }).start();
    }
}
