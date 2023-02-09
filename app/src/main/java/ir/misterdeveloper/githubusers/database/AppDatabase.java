package ir.misterdeveloper.githubusers.database;

import android.content.Context;

import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Favorite.class}, version = 1, exportSchema = true)
public abstract class AppDatabase extends RoomDatabase {

    private static final String DB_NAME = "task.db";

    public abstract DaoDatabase daoDatabase();

    private static AppDatabase instance = null;


    public static synchronized AppDatabase getInstance(Context context) {


        if (instance == null) {

            instance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DB_NAME)
                    .allowMainThreadQueries().build();
        }


        return instance;

    }


}
