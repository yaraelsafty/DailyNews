package com.example.yara.dailynews.DataBase;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Created by Yara on 05-Feb-19.
 */
@Dao
public interface NewsDao {
    @Query(" SELECT * FROM News")
    LiveData<List<NewsEntry>> loadAllNews();

    @Query(" SELECT * FROM News")
    List<NewsEntry> widget();

    @Insert
    void  insertNews (NewsEntry newsEntry);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void  updateNews (NewsEntry newsEntry);

    @Query("delete from News where title=:title")
    void  deleteNews (String title);

}
