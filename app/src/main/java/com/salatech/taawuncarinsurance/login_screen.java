package com.salatech.taawuncarinsurance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.salatech.taawuncarinsurance.databinding.ActivityLoginScreenBinding;

public class login_screen extends AppCompatActivity {

    ActivityLoginScreenBinding binding;
    DataBaseInsurance dataBaseInsurance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        dataBaseInsurance = new DataBaseInsurance(this);
        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = binding.editTextSigninEmail.getText().toString();
                String password =  binding.editTextSigninPassword.getText().toString();

                if (email.equals("")||password.equals(""))
                    Toast.makeText(login_screen.this,"All fields are mandatory",Toast.LENGTH_LONG).show();
                else {
                    Boolean checkCredentials = dataBaseInsurance.checkEmailPassword(email,password);

                    if (checkCredentials == true){
                        Toast.makeText(login_screen.this,"Welcome back",Toast.LENGTH_SHORT).show();
                        Intent intent =new Intent(getApplicationContext(), Overview_Policies_Screen.class);
                        startActivity(intent);
                    }else {
                        Toast.makeText(login_screen.this,"Invalid Credentials",Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        binding.signupRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(login_screen.this, continue_with_page.class);
                startActivity(intent);
            }
        });
    }
}