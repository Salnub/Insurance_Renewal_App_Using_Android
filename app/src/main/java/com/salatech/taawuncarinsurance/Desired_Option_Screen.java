package com.salatech.taawuncarinsurance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.salatech.taawuncarinsurance.databinding.ActivityDesiredOptionScreenBinding;

public class Desired_Option_Screen extends AppCompatActivity {
    ActivityDesiredOptionScreenBinding binding;
    DataBaseInsurance dataBaseInsurance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDesiredOptionScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnRenewalInfoDesired.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Renewal_information_screen.class);
                startActivity(intent);
            }
        });
        binding.btnInitiateRenewalProcessDesired.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Initiate_renewal_screen.class);
                startActivity(intent);
            }
        });
        binding.btnPolicyDetailsDesired.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Policy_details_screen.class);
                startActivity(intent);
            }
        });
        binding.btnSettingSupport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), App_settings_screen.class);
                startActivity(intent);
            }
        });
        binding.btnPaymentPdfDesired.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Payment_process.class);
          startActivity(intent);
            }
        });
    }
}