package com.simona.budgetapp.activities;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.simona.budgetapp.R;

import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.Date;

public class AddRecordActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private TextView dateTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_record);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Sets return button on action bar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Calendar now = Calendar. getInstance();
        final DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                AddRecordActivity.this, now.get(Calendar.YEAR), now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH));

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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_record, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.save_record) {
            saveRecord();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveRecord() {
        // TODO: save new record
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String date = Integer.valueOf(dayOfMonth).toString() + '.'
                + Integer.valueOf(month).toString() + '.'
                + Integer.valueOf(year).toString();
        dateTextView.setText(date);
    }
}

