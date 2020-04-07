package com.example.waterreminder2.persistence;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.waterreminder2.async.InsertAsyncTask;
import com.example.waterreminder2.models.Water;

import java.util.List;

public class WaterRepository {

    private WaterDatabase mWaterDatabase;

    public WaterRepository(Context context) {
        mWaterDatabase = WaterDatabase.getInstance(context);
    }

    public void inserWaterTask (Water water){
        new InsertAsyncTask(mWaterDatabase.getWaterDao()).execute(water);
    }

    public void updateWater(Water water){}

    public LiveData<List<Water>> retrieveWaterTask(){
        return mWaterDatabase.getWaterDao().getWaters();
    }

    public List<Water> retrieveAmountOfWaterTask(){
        return mWaterDatabase.getWaterDao().getAmountOfWater();
    }

    public void deleteWater (Water water){}
}
