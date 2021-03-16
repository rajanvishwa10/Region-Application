package com.android.regionapplication.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface RegionDao {

    @Query("SELECT * FROM regionmodel")
    List<RegionModel> getRegion();

    @Insert
    void insertRegion(RegionModel... regionModel);

    @Query("DELETE FROM regionmodel")
    void deleteRegion();
}
