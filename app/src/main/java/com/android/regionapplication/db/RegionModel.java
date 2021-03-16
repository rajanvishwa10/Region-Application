package com.android.regionapplication.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity
public class RegionModel {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "capital")
    public String capital;

    @ColumnInfo(name = "region")
    public String region;

    @ColumnInfo(name = "subregion")
    public String subregion;

    @ColumnInfo(name = "flag")
    public String flag;

    @ColumnInfo(name = "population")
    public String population;

    @ColumnInfo(name = "border")
    public String border;

    @ColumnInfo(name = "languages")
    public String languages;

}
