package com.example.artbook.RoomDB;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.artbook.RoomDB.uri.UriConverter;

@Database(entities = {ArtEntity.class}, version = 1)
@TypeConverters(UriConverter.class)
public abstract class ArtDatabase extends RoomDatabase {
    public abstract ArtDAO artDAO();
}
