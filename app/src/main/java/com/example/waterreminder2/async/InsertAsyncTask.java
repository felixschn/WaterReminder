package com.example.waterreminder2.async;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.waterreminder2.models.Water;
import com.example.waterreminder2.persistence.WaterDAO;

public class InsertAsyncTask extends AsyncTask<Water, Void, Void> {
    private static final String TAG = "InsertAsyncTask";
    private WaterDAO mWaterDao;
    public InsertAsyncTask(WaterDAO dao) {
        mWaterDao = dao;
    }

    @Override
    protected Void doInBackground(Water... waters) {
        Log.d(TAG, "doInBackground: thread "+ Thread.currentThread().getName());
        mWaterDao.insertWaters(waters);
        return null;
    }

}
