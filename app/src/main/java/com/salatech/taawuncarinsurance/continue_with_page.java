package com.salatech.taawuncarinsurance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.salatech.taawuncarinsurance.databinding.ActivityContinueWithPageBinding;

public class continue_with_page extends AppCompatActivity {
    ActivityContinueWithPageBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityContinueWithPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnContinueMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), signup_screen.class);
                startActivity(intent);
            }
        });
        binding.btnContinueGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Overview_Policies_Screen.class);
                startActivity(intent);
            }
        });
    }
}