package com.salatech.taawuncarinsurance;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.salatech.taawuncarinsurance.databinding.ActivityRenewalProcessScreenBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Renewal_process_screen extends AppCompatActivity {
    ActivityRenewalProcessScreenBinding binding;
    DataBaseInsurance dataBaseInsurance;
    Button btnStartDateRenewal, btnEndDateRenewal, btnCalculateRenewal;
    EditText editTextRenewalPolicyInfo, editTextPriceCalculationRenewal;
    String selectedStartDate, selectedEndDate;
    int policyNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRenewalProcessScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        btnStartDateRenewal = findViewById(R.id.btn_start_date_renewal);
        btnEndDateRenewal = findViewById(R.id.btn_end_date_renewal);
        btnCalculateRenewal = findViewById(R.id.btn_calculate_renewal_process);
        editTextRenewalPolicyInfo = findViewById(R.id.editText_renewal_policy_info);
        editTextPriceCalculationRenewal = findViewById(R.id.editText_price_calculation_renewal);

        btnStartDateRenewal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog(btnStartDateRenewal);
            }
        });

        btnEndDateRenewal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog(btnEndDateRenewal);
            }
        });

        btnCalculateRenewal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculateRenewalPrice();
            }
        });

        binding.btnNextRenewal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Retrieve policy number from the EditText
                String policyNumberText = editTextRenewalPolicyInfo.getText().toString().trim();

                if (selectedStartDate == null || selectedEndDate == null) {
                    Toast.makeText(getApplicationContext(), "Please select start and end dates", Toast.LENGTH_SHORT).show();
                } else if (policyNumberText.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please enter policy number", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        // Parse policy number as an integer
                        policyNumber = Integer.parseInt(policyNumberText);

                        // Check if the renewal period is at least 1 year
                        if (isRenewalPeriodValid(selectedStartDate, selectedEndDate)) {
                            // Pass selected dates and calculated price to the next activity
                            Intent intent = new Intent(getApplicationContext(), Policy_details_screen.class);
                            intent.putExtra("selectedStartDate", selectedStartDate);
                            intent.putExtra("selectedEndDate", selectedEndDate);
                            double price = calculatePriceForPolicy(policyNumber);
                            // Display the price in the EditText
                            editTextPriceCalculationRenewal.setText(String.valueOf(price));
                            startActivity(intent);
                        } else {
                            Toast.makeText(getApplicationContext(), "Renewal must be at least 1 year long", Toast.LENGTH_SHORT).show();
                        }
                    } catch (NumberFormatException e) {
                        // Handle invalid policy number input
                        Toast.makeText(getApplicationContext(), "Invalid policy number", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        binding.btnCancelRenewalProcess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Initiate_renewal_screen.class);
                startActivity(intent);
            }
        });

        binding.btnHelpRenewal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), App_settings_screen.class);
                startActivity(intent);
            }
        });
    }

    private void showDatePickerDialog(final Button dateButton) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        String selectedDate = String.format(Locale.US, "%02d-%02d-%04d", dayOfMonth, month + 1, year);
                        dateButton.setText(selectedDate);
                        if (dateButton.getId() == R.id.btn_start_date_renewal) {
                            selectedStartDate = selectedDate;
                        } else if (dateButton.getId() == R.id.btn_end_date_renewal) {
                            selectedEndDate = selectedDate;
                        }
                    }
                },

                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );

        datePickerDialog.setCanceledOnTouchOutside(true);

        datePickerDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {

            }
        });

        datePickerDialog.show();
    }

    private double calculatePriceForPolicy(int policyNumber) {
        double price;
        // Adjust price calculation based on policy number
        if (policyNumber == 732) {
            price = calculateYears(selectedStartDate, selectedEndDate) * 100000;
        } else if (policyNumber == 829) {
            price = calculateYears(selectedStartDate, selectedEndDate) * 120000;
        } else if (policyNumber == 367) {
            price = calculateYears(selectedStartDate, selectedEndDate) * 140000;
        } else {
            // Default price if policy number doesn't match
            price = 0.0;
        }
        return price;
    }

    private boolean isRenewalPeriodValid(String startDate, String endDate) {
        // Implement your validation logic here
        // Parse the start and end dates in dd-MM-yyyy format
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        try {
            Date start = sdf.parse(startDate);
            Date end = sdf.parse(endDate);

            // Calculate the difference in days
            long differenceInMillis = end.getTime() - start.getTime();
            long differenceInDays = differenceInMillis / (24 * 60 * 60 * 1000);

            // Check if the difference is at least 365 days (1 year)
            if (differenceInDays >= 365) {
                return true;
            } else {
                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    private int calculateYears(String startDate, String endDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        try {
            Date start = sdf.parse(startDate);
            Date end = sdf.parse(endDate);

            Calendar startCalendar = Calendar.getInstance();
            Calendar endCalendar = Calendar.getInstance();

            startCalendar.setTime(start);
            endCalendar.setTime(end);

            int years = endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR);

            if (endCalendar.get(Calendar.DAY_OF_YEAR) < startCalendar.get(Calendar.DAY_OF_YEAR)) {
                years--;
            }

            return years;
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }

    private void calculateRenewalPrice() {
        String policyNumberText = editTextRenewalPolicyInfo.getText().toString().trim();
        if (policyNumberText.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please enter policy number", Toast.LENGTH_SHORT).show();
        } else {
            try {
                policyNumber = Integer.parseInt(policyNumberText);

                if (policyNumber == 732 || policyNumber == 829 || policyNumber == 367) {
                    double price = calculatePriceForPolicy(policyNumber);
                    editTextPriceCalculationRenewal.setText(String.valueOf(price));
                } else {
                    Toast.makeText(getApplicationContext(), "Invalid policy number", Toast.LENGTH_SHORT).show();
                }
            } catch (NumberFormatException e) {
                Toast.makeText(getApplicationContext(), "Invalid policy number", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
