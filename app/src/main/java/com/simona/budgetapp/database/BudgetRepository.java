package com.simona.budgetapp.database;


import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.simona.budgetapp.dao.CategoryDao;
import com.simona.budgetapp.dao.RecordDao;
import com.simona.budgetapp.entities.Category;
import com.simona.budgetapp.entities.Record;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class BudgetRepository {
    private CategoryDao categoryDao;
    private RecordDao recordDao;
    private final LiveData<List<Category>> allCategories;
    private final LiveData<List<Record>> allRecords;
    private final static String TAG = "Repository";

    public BudgetRepository(Context context) throws ExecutionException, InterruptedException {
        BudgetDatabase db = BudgetDatabase.getDatabase(context);
        categoryDao = db.categoryDao();
        recordDao = db.recordDao();

        retrieveAllCategoriesAsyncTask retrieveAllCategoriesAsyncTask = new retrieveAllCategoriesAsyncTask(categoryDao);
        allCategories = retrieveAllCategoriesAsyncTask.execute().get();

        retrieveAllRecordsAsyncTask retrieveAllRecordsAsyncTask = new retrieveAllRecordsAsyncTask(recordDao);
        allRecords = retrieveAllRecordsAsyncTask.execute().get();
    }

    public LiveData<List<Category>> getAllCategories() {
        return allCategories;
    }

    public LiveData<List<Record>> getAllRecords() {
        return allRecords;
    }

    public void insertCategory(Category category) {
        insertCategoryAsyncTask insertCategoryAsyncTask = new insertCategoryAsyncTask(categoryDao);
        insertCategoryAsyncTask.execute(category);
    }

    public void insertRecord(Record record) {
        insertRecordAsyncTask insertRecordAsyncTask = new insertRecordAsyncTask(recordDao);
        insertRecordAsyncTask.execute(record);
    }

    private static class insertCategoryAsyncTask extends AsyncTask<Category, Void, Void> {
        private CategoryDao mAsyncTaskDao;

        insertCategoryAsyncTask(CategoryDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Category... categories) {
            mAsyncTaskDao.insert(categories[0]);
            Log.v(TAG, "Category " + categories[0].toString() + " inserted.");
            return null;
        }
    }

    private static class insertRecordAsyncTask extends AsyncTask<Record, Void, Void> {
        private RecordDao mAsyncTaskDao;

        insertRecordAsyncTask(RecordDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Record... records) {
            mAsyncTaskDao.insert(records[0]);
            Log.v(TAG, "Record " + records[0].toString() + " inserted.");
            return null;
        }
    }

    private static class retrieveAllCategoriesAsyncTask extends AsyncTask<Void, Void, LiveData<List<Category>>> {
        private CategoryDao mAsyncTaskDao;

        retrieveAllCategoriesAsyncTask(CategoryDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected LiveData<List<Category>> doInBackground(Void... voids) {
            LiveData<List<Category>> allCategories = mAsyncTaskDao.getAll();
            Log.v(TAG, "Categories retrieved");
            return allCategories;
        }
    }

    private static class retrieveAllRecordsAsyncTask extends AsyncTask<Void, Void, LiveData<List<Record>>> {

        private RecordDao mAsyncTaskDao;

        retrieveAllRecordsAsyncTask(RecordDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected LiveData<List<Record>> doInBackground(Void... voids) {
            LiveData<List<Record>> allRecords = mAsyncTaskDao.getAll();
            Log.v(TAG, "Records retrieved");
            return allRecords;
        }
    }
}
