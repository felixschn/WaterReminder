package com.example.waterreminder2.persistence;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.waterreminder2.models.Water;
import com.example.waterreminder2.ui.main.WaterFragment;

@Database(entities = {Water.class}, version = 1)
public abstract class WaterDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "waters_db";

    private static WaterDatabase instance;

    static WaterDatabase getInstance(final Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                    //TODO schauen ob geht, da im Video auf eine Activity und kein Fragment verwiesen wird. vorher war es ein Context context -> context.getApplicationContext()
                    context.getApplicationContext(),
                    WaterDatabase.class,
                    DATABASE_NAME
            ).build();
        }
        return instance;
    }

    public abstract WaterDAO getWaterDao();

   /* static final Migration MIGRATION1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE waters ");

        }
    };*/



}
