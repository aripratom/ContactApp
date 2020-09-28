package com.aripratom.aplikasikontak.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.aripratom.aplikasikontak.model.Contact;

import java.util.List;

@Dao
public interface ContactDao {
    @Query("SELECT * FROM Contact")
    List<Contact> getAll();

    @Query("SELECT * FROM Contact WHERE id=:id")
    Contact getById(Long id);

    @Insert
    void insert(Contact Contact);

    @Update
    void update(Contact Contact);

    @Delete
    void delete(Contact Contact);

    @Query("SELECT COUNT(*) from Contact")
    Integer count();
}
