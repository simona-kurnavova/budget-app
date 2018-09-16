package com.simona.budgetapp.activities;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.simona.budgetapp.R;
import com.simona.budgetapp.adapters.RecordsAdapter;
import com.simona.budgetapp.database.BudgetRepository;
import com.simona.budgetapp.entities.Category;
import com.simona.budgetapp.entities.Record;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class OverviewActivity extends AppCompatActivity {

    private final static String TAG = "Overview";

    private BudgetRepository budgetRepository;

    private LiveData<List<Category>> allCategories;

    private LiveData<List<Record>> allRecords;

    private RecyclerView mRecyclerView;
    private RecordsAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setImageResource(R.drawable.ic_add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplication(), AddRecordActivity.class);
                startActivity(intent);
            }
        });

        Spinner spinner = (Spinner) findViewById(R.id.month_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.month_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        try {
            budgetRepository = new BudgetRepository(this);
            allCategories = budgetRepository.getAllCategories();
            allRecords = budgetRepository.getAllRecords();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        allCategories.observe(this, new Observer<List<Category>>() {
            @Override
            public void onChanged(@Nullable List<Category> categories) {
                mAdapter.updateCategories(categories);
                mAdapter.notifyDataSetChanged();
                Log.v(TAG, "Data state changed");
            }
        });

        allRecords.observe(this, new Observer<List<Record>>() {
            @Override
            public void onChanged(@Nullable List<Record> records) {
                mAdapter.updateRecords(records);
                mAdapter.notifyDataSetChanged();
                Log.v(TAG, "Data state changed");
            }
        });

        setRecordsView();

    }

    private void setRecordsView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.records_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new RecordsAdapter(allRecords.getValue(), allCategories.getValue());
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_overview, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
