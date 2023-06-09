package com.pedrycz.phonedb.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.pedrycz.phonedb.entities.Phone;

import java.util.List;

@Dao
public interface PhoneDao {

    @Insert
    void insert(Phone phone);

    @Delete
    void deletePhone(Phone phone);

    @Query("UPDATE phones " +
            "SET manufacturer = :brand, model = :model, android = :android, url = :url " +
            "WHERE id = :id")
    void updatePhone(Long id, String brand, String model, String android, String url);

    @Query("DELETE FROM phones")
    void deleteAll();

    @Query("SELECT * FROM phones")
    LiveData<List<Phone>> getAllPhones();

    @Query("SELECT COUNT(model) FROM phones")
    int getDataCount();
}
