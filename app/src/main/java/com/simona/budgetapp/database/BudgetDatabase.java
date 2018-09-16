package com.simona.budgetapp.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import com.simona.budgetapp.dao.CategoryDao;
import com.simona.budgetapp.dao.RecordDao;


import com.simona.budgetapp.entities.Category;
import com.simona.budgetapp.entities.Record;


@Database(entities = {Category.class, Record.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class BudgetDatabase extends RoomDatabase {
    private static BudgetDatabase INSTANCE;

    public abstract CategoryDao categoryDao();

    public abstract RecordDao recordDao();

    static BudgetDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (BudgetDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            BudgetDatabase.class, "budget_database")
                            .build();

                }
            }
        }
        return INSTANCE;
    }
}
