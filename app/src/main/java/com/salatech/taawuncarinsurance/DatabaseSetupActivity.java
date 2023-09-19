package com.salatech.taawuncarinsurance;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class DatabaseSetupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database_setup);

        // Initialize the database helper
        DataBaseInsurance dbHelper = new DataBaseInsurance(this);

        // Insert initial data into the database
        insertInitialData(dbHelper);

        // Close the database connection
        dbHelper.close();

        // Navigate to your main activity or another appropriate activity
        Intent intent = new Intent(this, login_screen.class);
        startActivity(intent);
        finish(); // Finish this setup activity so it's not shown again
    }

    private void insertInitialData(DataBaseInsurance dbHelper) {
        // Manually insert multiple rows of initial data into the database
        insertRenewalData(dbHelper, "732", "31-12-2023", "saleh@gmail.com", 100000.0);
        insertRenewalData(dbHelper, "829", "31-12-2023", "safwan@gmail.com ", 120000.0);
        insertRenewalData(dbHelper, "367", "31-12-2023", "rimsha@gmail.com", 140000.0);
        // Add more rows as needed
    }

    private void insertRenewalData(DataBaseInsurance dbHelper, String policyNumber, String expirationDate, String userEmail, double price) {
        boolean isInserted = dbHelper.insertRenewal(policyNumber, expirationDate, userEmail, price);

        if (isInserted) {
            // Data inserted successfully
        } else {
            // Data insertion failed
        }

    }
}
