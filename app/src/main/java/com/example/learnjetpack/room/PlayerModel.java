package com.example.learnjetpack.room;

import android.nfc.tech.NfcA;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**
 * 球员表
 */
//Entity 对应数据库中的表
@Entity(tableName = "player")
public class PlayerModel {
    //ColumnInfo 表列名
    @ColumnInfo(name = "player_code")
    public int code;
    @ColumnInfo(name = "player_country")
    public String country;
    @ColumnInfo(name = "player_country_en")
    public String country_en;
    @ColumnInfo(name = "player_name")
    public String name;
    @ColumnInfo(name = "player_name_en")
    public String name_en;
    //Ignore 忽略字段 不存表
    @Ignore
    public String coundry_code;
    //Embedded 嵌套字段
    @Embedded
    public StatAverageModel statAverage;
    //PrimaryKey 主键 autoGenerate id值自动增长
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public int id;

    @Override
    public String toString() {
        return "PlayerModel{" +
                "code=" + code +
                ", country='" + country + '\'' +
                ", country_en='" + country_en + '\'' +
                ", name='" + name + '\'' +
                ", name_en='" + name_en + '\'' +
                ", coundry_code='" + coundry_code + '\'' +
                ", statAverage=" + statAverage +
                ", id=" + id +
                '}';
    }
}
