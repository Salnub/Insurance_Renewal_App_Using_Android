package com.salatech.taawuncarinsurance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.salatech.taawuncarinsurance.databinding.ActivityInitiateRenewalScreenBinding;

public class Initiate_renewal_screen extends AppCompatActivity {
    ActivityInitiateRenewalScreenBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInitiateRenewalScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnStartRenewal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Renewal_process_screen.class);
            startActivity(intent);
            }
        });

        binding.btnInitiateRenewalBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Renewal_information_screen.class);
                startActivity(intent);
            }
        });


    }
}