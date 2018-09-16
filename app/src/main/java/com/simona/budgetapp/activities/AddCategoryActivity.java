package com.simona.budgetapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.simona.budgetapp.R;
import com.simona.budgetapp.database.BudgetRepository;
import com.simona.budgetapp.entities.Category;

import java.util.concurrent.ExecutionException;

public class AddCategoryActivity extends AppCompatActivity {

    private BudgetRepository budgetRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        try {
            budgetRepository = new BudgetRepository(getApplicationContext());
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.save) {
            saveCategory();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveCategory() {
        TextView nameView = findViewById(R.id.name);
        Category category = new Category();
        category.setName(nameView.getText().toString());
        budgetRepository.insertCategory(category);

        Intent intent = new Intent(this, AddRecordActivity.class);
        startActivity(intent);
    }
}
