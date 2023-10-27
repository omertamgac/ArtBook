package com.example.artbook.RoomDB;

import android.net.Uri;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.net.URI;

@Entity
public class ArtEntity implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name = "name")
    public String name;
    @ColumnInfo(name = "artName")
    public String artName;
    @ColumnInfo(name = "year")
    public String year;
    @ColumnInfo(name = "image")
    public String image;

    public ArtEntity(String name, String artName, String year, String image) {
        this.name = name;
        this.artName = artName;
        this.year = year;
        this.image = image;
    }
}
