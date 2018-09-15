package com.simona.budgetapp.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;


@Entity(tableName = "record",
        foreignKeys = @ForeignKey(entity = Category.class, parentColumns = "id", childColumns = "category"))
public class Record {
    @PrimaryKey
    private int id;

    @ColumnInfo(name = "category")
    private int category;

    @ColumnInfo(name = "note")
    private String note;

    @ColumnInfo(name = "date")
    private Date date;

    @ColumnInfo(name = "price")
    private double price;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
