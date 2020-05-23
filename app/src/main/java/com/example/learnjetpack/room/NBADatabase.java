package com.example.learnjetpack.room;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

/**
 * 数据库类需要使用@Database注解
 * @Database包括几个参数：entities（数据库包含的数据表，@entities注解修饰的实体），
 * default（数据库包含的视图），
 * version（数据库的版本号），
 * exportSchema（可以理解为开关，如果开关为 true，Room 框架会通过注解处理器将一些数据库相关的schema输出到指定的目录中，默认 true）
 *
 * 创建的数据库类需要继承 RoomDatabase，数据库类声明为抽象类；
 */
@Database(entities = PlayerModel.class,version = 3,exportSchema = false)
public abstract class NBADatabase extends RoomDatabase {

    private static NBADatabase instance;

    /**
     * 设计成单例模式，避免创建多个数据库对象消耗资源；
     * @param context
     * @return
     */
    public static NBADatabase getInstance(Context context) {
        if(instance == null){
            synchronized (NBADatabase.class){
                if(instance == null){
                    instance = Room.databaseBuilder(context.getApplicationContext(), NBADatabase.class, "nba_db")
                            .addMigrations(MIGRATION_1_2,MIGRATION_2_3)
                            .addCallback(new Callback() {
                                @Override
                                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                                    super.onCreate(db);
                                }

                                @Override
                                public void onOpen(@NonNull SupportSQLiteDatabase db) {
                                    super.onOpen(db);
                                }
                            }).build();
                }
            }
        }
        return instance;
    }
    //需要提供方法来获取数据访问对象层(Dao)对象，方法声明为抽象方法；
    public abstract PlayerDao getPlayerDao();

    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE player ADD COLUMN player_name TEXT");
        }
    };
    static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE player ADD COLUMN player_name_en TEXT");
        }
    };

}
