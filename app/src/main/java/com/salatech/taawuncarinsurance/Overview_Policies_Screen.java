package com.salatech.taawuncarinsurance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.salatech.taawuncarinsurance.databinding.ActivityOverviewPoliciesScreenBinding;
import com.salatech.taawuncarinsurance.databinding.ActivitySignupScreenBinding;


public class Overview_Policies_Screen extends AppCompatActivity {
    ActivityOverviewPoliciesScreenBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOverviewPoliciesScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.carCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("HtmlPage","file:///android_asset/car.html");
                startActivity(intent);
            }
        });
        binding.lifeCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("HtmlPage","file:///android_asset/life.html");
                startActivity(intent);
            }
        });
        binding.disableCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("HtmlPage","file:///android_asset/disability.html");
                startActivity(intent);
            }
        });
        binding.healthCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("HtmlPage","file:///android_asset/health.html");
                startActivity(intent);
            }
        });
    }
}