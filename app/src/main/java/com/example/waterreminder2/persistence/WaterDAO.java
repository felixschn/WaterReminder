package com.example.waterreminder2.persistence;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.waterreminder2.models.Water;

import java.util.List;

@Dao
public interface WaterDAO {
    @Insert
    long[] insertWaters(Water... waters);

    @Query("SELECT * FROM waters")
    LiveData<List<Water>> getWaters();

    @Query("SELECT COALESCE(sum(COALESCE(amount,0)),0) from waters")
    LiveData<Integer> getAmountOfWater();

    @Delete
    int delete (Water... waters);

    @Update
    int update(Water... waters);
}
