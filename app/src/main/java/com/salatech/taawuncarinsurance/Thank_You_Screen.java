package com.salatech.taawuncarinsurance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.salatech.taawuncarinsurance.databinding.ActivityThankYouScreenBinding;

public class Thank_You_Screen extends AppCompatActivity {
    ActivityThankYouScreenBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityThankYouScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Overview_Policies_Screen.class);
                startActivity(intent);
            }
        });
    }
}