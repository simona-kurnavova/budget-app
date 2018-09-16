package com.simona.budgetapp.activities;

import android.app.DatePickerDialog;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import com.simona.budgetapp.R;
import com.simona.budgetapp.database.BudgetRepository;
import com.simona.budgetapp.entities.Category;
import com.simona.budgetapp.entities.Record;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class AddRecordActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private TextView dateTextView;
    private BudgetRepository budgetRepository;
    private List<Category> allCategories;
    private DatePickerDialog datePickerDialog;
    private Date selectedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_record);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Calendar now = Calendar. getInstance();
        datePickerDialog = new DatePickerDialog(this,
                AddRecordActivity.this, now.get(Calendar.YEAR), now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH));
        selectedDate = new Date();

        dateTextView = findViewById(R.id.date_text);
        String today = Integer.valueOf(now.get(Calendar.DAY_OF_MONTH)).toString() + '.'
                + Integer.valueOf(now.get(Calendar.MONTH)).toString() + '.'
                + Integer.valueOf(now.get(Calendar.YEAR)).toString();
        dateTextView.setText(today);

        Button button = findViewById(R.id.datepicker_button);
        button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        datePickerDialog.show();
                    }
                }
        );

        try {
            budgetRepository = new BudgetRepository(this);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        TextView addCategoryView = findViewById(R.id.add_category_link);
        addCategoryView.setClickable(true);
        addCategoryView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), AddCategoryActivity.class);
                startActivity(intent);
            }
        });
        addCategoryView.setMovementMethod(LinkMovementMethod.getInstance());

        setCategorySpinner();
        setTypeSpinner();
    }

    private void setTypeSpinner() {
        Spinner spinner = (Spinner) findViewById(R.id.type_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.type_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private void setCategorySpinner() {
        LiveData<List<Category>> categories = budgetRepository.getAllCategories();
        categories.observe(this, new Observer<List<Category>>() {
            @Override
            public void onChanged(@Nullable List<Category> categories) {
                allCategories = categories;
                ArrayList<String> categoriesArray = new ArrayList<>();
                if(allCategories != null) {
                    for (Category category : allCategories) {
                        categoriesArray.add(category.getName());
                    }
                }
                Spinner spinner = findViewById(R.id.category_spinner);
                ArrayAdapter<String> adapter =  new ArrayAdapter<String>(getApplication(), android.R.layout.simple_spinner_item, categoriesArray);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);
            }
        });
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
            saveRecord();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveRecord() {
        Record record = new Record();
        TextView noteView = findViewById(R.id.note_text);
        TextView priceView = findViewById(R.id.price_text);
        Spinner categorySpinner = findViewById(R.id.category_spinner);
        Category category = allCategories.get(categorySpinner.getSelectedItemPosition());
        Spinner typeSpinner = findViewById(R.id.type_spinner);

        record.setPrice(Float.parseFloat(priceView.getText().toString()));
        record.setNote(noteView.getText().toString());
        record.setCategory(category.getId());
        record.setDate(selectedDate);
        record.setType(typeSpinner.getSelectedItem().toString());

        budgetRepository.insertRecord(record);

        Intent intent = new Intent(this, OverviewActivity.class);
        startActivity(intent);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String date = Integer.valueOf(dayOfMonth).toString() + '.'
                + Integer.valueOf(month).toString() + '.'
                + Integer.valueOf(year).toString();
        dateTextView.setText(date);
        selectedDate = new Date(year, month, dayOfMonth);
    }
}

