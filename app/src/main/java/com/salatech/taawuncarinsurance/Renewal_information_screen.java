package com.salatech.taawuncarinsurance;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.salatech.taawuncarinsurance.databinding.ActivityRenewalInformationScreenBinding;

public class Renewal_information_screen extends AppCompatActivity {

    private ActivityRenewalInformationScreenBinding binding;
    private DataBaseInsurance dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRenewalInformationScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        dbHelper = new DataBaseInsurance(this);
        binding.btnRenewalNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Initiate_renewal_screen.class);
                startActivity(intent);
            }
        });
        binding.btnRenewalCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Renewal_information_screen.class);
                startActivity(intent);
            }
        });
        binding.btnHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),App_settings_screen.class);
                startActivity(intent);
            }
        });
        binding.btnRenewalVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String policyNumber = binding.editTextPolicyField.getText().toString();
                String expirationDate = binding.editTextExpirationDate.getText().toString();

                // Retrieve policy holder's information
                String policyHolderInfo = dbHelper.getPolicyHolderInfo(policyNumber, expirationDate);
                binding.editTextPolicyInformation.setText(policyHolderInfo);
            }
        });

        binding.btnRenewalCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String policyNumber = binding.editTextPolicyField.getText().toString();
                String expirationDate = binding.editTextExpirationDate.getText().toString();

                // Check if the policy holder already has insurance
                boolean hasInsurance = dbHelper.checkInsurance(policyNumber, expirationDate);

                if (hasInsurance) {
                    binding.editTextPolicyInformation.setText("Yes, you already have insurance.");
                } else {
                    binding.editTextPolicyInformation.setText("Please first take our insurance.");
                }
            }
        });

        binding.btnCheckPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String policyNumber = binding.editTextPolicyField.getText().toString();
                String expirationDate = binding.editTextExpirationDate.getText().toString();
                String renewalYearsStr = binding.editTextRenewalYears.getText().toString();

                // Parse renewal years to an integer (add input validation)
                int renewalYears = Integer.parseInt(renewalYearsStr);

                // Calculate the renewal price based on policy number and renewal years
                double renewalPrice = calculateRenewalPrice(policyNumber, renewalYears);

                // Display the renewal price
                binding.editTextPriceCalculation.setText(String.valueOf(renewalPrice));

                // Save renewal years to the database
                boolean isRenewalYearsInserted = dbHelper.insertRenewalYears(renewalYears);
                if (isRenewalYearsInserted) {
                    // Data inserted successfully
                } else {
                    // Data insertion failed
                }
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbHelper.close();
    }

    // Calculate renewal price based on policy number and renewal years
    private double calculateRenewalPrice(String policyNumber, int renewalYears) {
        double price = 0.0; // Default price

        // Apply your pricing logic based on policy number
        if (policyNumber.equals("732")) {
            price = renewalYears * 100000;
        } else if (policyNumber.equals("829")) {
            price = renewalYears * 120000;
        } else if (policyNumber.equals("367")) {
            price = renewalYears * 1400000;
        }

        return price;
    }

}
