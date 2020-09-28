package com.aripratom.aplikasikontak.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Contact implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    public Long id;
    public String nama;
    public String tgllahir;
    public String pekerjaan;
    public String jeniskelamin;
    public String email;
    public String telpon;
}
