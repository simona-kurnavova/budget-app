package com.simona.budgetapp.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.simona.budgetapp.database.Converters;

import java.util.Date;
import static android.arch.persistence.room.ForeignKey.CASCADE;


@Entity(tableName = "record",
        foreignKeys = @ForeignKey(entity = Category.class, parentColumns = "id", childColumns = "category", onDelete = CASCADE))
public class Record {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "category")
    private int category;

    @ColumnInfo(name = "note")
    private String note;

    @TypeConverters(Converters.class)
    @ColumnInfo(name = "date")
    private Date date;

    @ColumnInfo(name = "price")
    private double price;

    @ColumnInfo(name = "type")
    private String type;

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

    @Override
    public String toString() {
        return "category=" + category + ", type='" + type + '\'' + ", price=" + price + '}';
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
