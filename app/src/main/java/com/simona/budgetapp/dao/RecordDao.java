package com.simona.budgetapp.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import com.simona.budgetapp.entities.Record;

import java.util.List;


@Dao
public interface RecordDao {
    @Query("SELECT * FROM record")
    LiveData<List<Record>> getAll();

    @Query("SELECT * FROM record WHERE id LIKE :record_id LIMIT 1")
    Record findById(int record_id);

    @Query("SELECT * FROM record WHERE category LIKE :category_id")
    LiveData<List<Record>> getByCategory(int category_id);

    @Insert
    void insert(Record record);

    @Update
    void update(Record record);

    @Delete
    void delete(Record record);
}
