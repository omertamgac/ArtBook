package com.example.artbook.RoomDB;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

@Dao
public interface ArtDAO {
    @Query("SELECT * FROM ArtEntity")
    Flowable<List<ArtEntity>> getAll();
    @Insert
    Completable insert(ArtEntity artEntity);
    @Delete
    Completable delete(ArtEntity artEntity);
}
