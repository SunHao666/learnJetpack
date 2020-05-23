package com.example.learnjetpack.room;

import androidx.annotation.WorkerThread;
import androidx.lifecycle.LiveData;

import java.util.List;

public class PlayerRepository {

    public PlayerDao playerDao;

    public PlayerRepository(PlayerDao playerDao) {
        this.playerDao = playerDao;
    }
    @WorkerThread
    public void insert(PlayerModel playerModel){
        playerDao.insertPlayer(playerModel);
    }

    public LiveData<List<PlayerModel>> findAllPlayers(){
        return playerDao.findPlayer();
    }

    public void delAllPlayers(List<PlayerModel> data){
        playerDao.deletePlayer(data);
    }
}
