package com.example.waterreminder2.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;
@Entity(tableName = "waters")
public class Water {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @NonNull
    @ColumnInfo(name = "amount")
    private int amount;

    @ColumnInfo(name = "content")
    private String currentTimeStamp;

    public Water(int amount, String currentTimeStamp) {
        this.amount = amount;
        this.currentTimeStamp = currentTimeStamp;
    }
    @Ignore
    public Water() {
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getCurrentTimeStamp() {
        return currentTimeStamp;
    }

    public void setCurrentTimeStamp(String currentTimeStamp) {
        this.currentTimeStamp = currentTimeStamp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Water{" +
                "amount=" + amount +
                ", currentTimeStamp=" + currentTimeStamp +
                '}';
    }
}
