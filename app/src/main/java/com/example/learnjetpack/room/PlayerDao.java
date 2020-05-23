package com.example.learnjetpack.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

/**
 * 开始创建数据访问对象层 Dao，在这里我们需要定义一些对数据库增删改查的方法。
 * 创建一个使用@Dao 注释的接口
 */
// @Dao
@Dao
public interface PlayerDao {
    /**
     * Insert 添加
     * onConflict 处理数据冲突时采取的策略
     * OnConflictStrategy.REPLACE用新的数据行替换旧的数据行；
     * OnConflictStrategy.ABORT直接回滚冲突的事务；
     * OnConflictStrategy.IGNORE保持现有数据行。
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPlayer(PlayerModel playerModel);

    /**
     * Delete 删除
     * @param playerModel
     */
    @Delete
    void deletePlayer(PlayerModel playerModel);
    @Delete
    void deletePlayer(List<PlayerModel> datas);

    /**
     * 修改
     * @param playerModel
     */
    @Update
    void updatePlayer(PlayerModel playerModel);
    @Update
    void updatePlayer(List<PlayerModel> data);

    /**
     * 查询
     * @Query 中声明我们要查询的 SQL 语句
     * 这些查询都是同步的，不能再主线程中
     * @param id
     * @return
     */
    @Query("SELECT * FROM player WHERE id=:id")
    PlayerModel findPlayerById(Long id);
    @Query("SELECT * FROM player")
    LiveData<List<PlayerModel>> findPlayer();

    /**
     * 配合LiveData<PlayerModel>
     * 表格数据更新时 就会收到通知
     * @param id
     * @return
     */
    @Query("SELECT * FROM player WHERE id=:id")
    LiveData<PlayerModel> findRlayerByid(Long id);
}
